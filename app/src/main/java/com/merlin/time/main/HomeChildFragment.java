package com.merlin.time.main;

import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.main.adapter.HomeChildAdapter;
import com.merlin.time.main.model.CommonListModel;
import com.merlin.time.main.model.HomeChildListModel;
import com.merlin.time.main.model.HomeChildModel;
import com.merlin.time.main.model.HomeCommonModel;
import com.merlin.time.main.model.HomeLikeModel;
import com.merlin.time.main.presenter.HomeChildPresenter;
import com.merlin.time.sharegallery.ImageShowActivity;
import com.merlin.time.view.TimeTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvp.factory.CreatePresenter;
import com.soyoung.component_base.mvpbase.BaseFragment;
import com.soyoung.component_base.util.InputUtils;
import com.soyoung.component_base.util.LogUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description
 */
@CreatePresenter(HomeChildPresenter.class)
public class HomeChildFragment extends BaseFragment implements HomeChildView {

  private SmartRefreshLayout smartRefreshLayout;
  private RecyclerView recyclerView;
  private HomeChildPresenter presenter;
  private HomeChildAdapter adapter;
  private String currentTab = "new_upload";
  private int index = 1;
  private EditText feedback_input;
  private LinearLayout feedback_bottom_ll;
  private TimeTextView feedback_send_tv;

  /**
   * @param tab tab:new_upload 最新上传
   * tab:new_photograph 最新拍照
   * tab:video 最新视频
   * tab:like 最新点赞
   */
  public static HomeChildFragment newInstance(String tab) {
    HomeChildFragment fragment = new HomeChildFragment();
    Bundle bundle = new Bundle();
    bundle.putString("tab", tab);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  protected boolean isImmersionBarEnabled() {
    return true;
  }

  /**
   * 初始化沉浸式
   */
  protected void initImmersionBar() {
    mImmersionBar = ImmersionBar.with(this);
    mImmersionBar
        .statusBarDarkFont(true, 0.2f)
        .fitsSystemWindows(true)
        .statusBarColor(com.soyoung.component_base.R.color.ffffff)
        .navigationBarWithKitkatEnable(false)
        .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        .setOnKeyboardListener(new OnKeyboardListener() {
          @Override
          public void onKeyboardChange(boolean b, int i) {
            if (!b) {
              if (feedback_bottom_ll.getVisibility() == View.VISIBLE) {

                feedback_input.clearFocus();
                feedback_bottom_ll.setVisibility(View.GONE);
                InputUtils.hideInput(getActivity(), feedback_input);
                ((MainActivity) getActivity()).group.setVisibility(View.VISIBLE);
              }
            } else {
              ((MainActivity) getActivity()).group.setVisibility(View.GONE);
              feedback_bottom_ll.setVisibility(View.VISIBLE);
            }
          }
        })
        .init();
  }

  @Override
  protected int setLayoutId() {
    return R.layout.my_gallery_fragment;
  }

  @Override
  protected void initView() {
    smartRefreshLayout = findViewById(R.id.home_refreshLayout);
    recyclerView = findViewById(R.id.home_recycler);
    feedback_bottom_ll = findViewById(R.id.main_home_child_ll);
    feedback_input = findViewById(R.id.feedback_input);
    feedback_send_tv = findViewById(R.id.feedback_send_tv);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(linearLayoutManager);
    adapter = new HomeChildAdapter(getActivity(), null);
    recyclerView.setAdapter(adapter);
    initLoadRootView(smartRefreshLayout);
  }

  @Override
  public void setListener() {
    smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
      @Override
      public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        index++;
        onRequestData();
      }

      @Override
      public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        index = 1;
        onRequestData();
      }
    });

    adapter.setLikeListener(new HomeChildAdapter.LikeListener() {
      @Override
      public void onLikeClick(HomeChildListModel model, int position) {
        presenter.doLike(model.getId(), position);
      }
    });

    adapter.setImageClick(new HomeChildAdapter.ImageClick() {
      @Override
      public void imageClick(String url) {
        Intent intent = new Intent(getActivity(), ImageShowActivity.class);
        intent.putExtra("image_url", url);
        startActivity(intent);
      }
    });

    adapter.setDiscussListener(new HomeChildAdapter.DiscussListener() {
      @Override
      public void onDiscussClick(String id, int position) {
        feedback_bottom_ll.setVisibility(View.VISIBLE);
        feedback_input.setFocusableInTouchMode(true);
        feedback_input.setFocusable(true);
        feedback_input.requestFocus();
        ((MainActivity) getActivity()).group.setVisibility(View.GONE);
        commonPosition = position;
        commonId = id;
        InputUtils.showInput(getActivity(), feedback_input);
      }
    });
    Disposable disposable =
        RxView.clicks(feedback_send_tv).throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
              @Override
              public void accept(Object o) throws Exception {
                presenter.doDiscuss(commonId, feedback_input.getText().toString(), commonPosition);
              }
            });
    recyclerView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (feedback_bottom_ll.getVisibility() == View.VISIBLE) {
          InputUtils.hideInput(getActivity(), feedback_input);
          feedback_input.setFocusableInTouchMode(false);
          feedback_input.setFocusable(false);
          feedback_input.clearFocus();
          feedback_bottom_ll.setVisibility(View.GONE);

          ((MainActivity) getActivity()).group.setVisibility(View.VISIBLE);
        }
        return false;
      }
    });
  }

  private int commonPosition;
  private String commonId;

  @Override
  protected void initData(@Nullable Bundle savedInstanceState) {
    presenter = (HomeChildPresenter) getMvpPresenter();
    Bundle bundle = getArguments();
    currentTab = bundle.getString("tab");
    onRefreshData();
  }

  @Override
  public void onRequestData() {
    presenter.getTabData(index, currentTab);
  }

  HomeChildModel mHomeModel;

  @Override
  public void showData(HomeChildModel model) {
    mHomeModel = model;
    showSuccess();
    if (index == 1) {
      smartRefreshLayout.finishRefresh();
      adapter.setNewData(model.getRows());
    } else {
      smartRefreshLayout.finishLoadMore();

      adapter.addData(model.getRows());
    }
    if (model.getPage() >= model.getTotal_page()) {
      smartRefreshLayout.finishLoadMoreWithNoMoreData();
    }
  }

  @Override
  public void doLike(HomeLikeModel model, int position) {
    getActivity().runOnUiThread(() -> {
      if (mHomeModel != null) {
        mHomeModel.getRows().get(position).setIs_like("1");
        mHomeModel.getRows().get(position).getUser_like_name().add(AppPreferencesHelper.getString(Constant.USER_NAME));
      }
      adapter.notifyItemChanged(position);
    });
  }

  @Override
  public void doCommon(HomeCommonModel model, int position) {
    if (mHomeModel != null) {
      CommonListModel commonListModel = new CommonListModel();
      commonListModel.setUser_name(AppPreferencesHelper.getString(Constant.USER_NAME, ""));
      commonListModel.setComment(model.getComment());
      if (null != mHomeModel.getRows().get(position).getComment_list()) {
        mHomeModel.getRows().get(position).getComment_list().add(commonListModel);
      } else {
        List<CommonListModel> listModels = new ArrayList<>();
        listModels.add(commonListModel);
        mHomeModel.getRows().get(position).setComment_list(listModels);
      }

      adapter.notifyItemChanged(position);
      feedback_input.getText().clear();
      feedback_input.clearFocus();
      feedback_bottom_ll.setVisibility(View.GONE);
      InputUtils.hideInput(getActivity(), feedback_input);
    }
  }

  @Override
  public void showMessage(String message) {
    super.showMessage(message);
    smartRefreshLayout.finishLoadMore();
    smartRefreshLayout.finishRefresh();
  }

  @Override
  public void onResume() {
    super.onResume();
    //统计页面，"MainScreen"为页面名称，可自定义
    MobclickAgent.onPageStart(HomeChildFragment.class.getSimpleName());
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(HomeChildFragment.class.getSimpleName());
  }
}
