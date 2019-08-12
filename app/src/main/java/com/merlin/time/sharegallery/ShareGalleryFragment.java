package com.merlin.time.sharegallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.sharegallery.create.CreateGalleryActivity;
import com.merlin.time.sharegallery.gallerylist.GalleryListActivity;
import com.merlin.time.sharegallery.model.ShareGalleryBaseModel;
import com.merlin.time.sharegallery.model.ShareGalleryModel;
import com.merlin.time.sharegallery.presenter.ShareGalleryPresenter;
import com.merlin.time.sharegallery.view.ShareGalleryAdapter;
import com.merlin.time.sharegallery.view.ShareGalleryView;
import com.merlin.time.utils.RecyclerViewItemDec;
import com.merlin.time.view.TimeTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.soyoung.component_base.mvp.factory.CreatePresenter;
import com.soyoung.component_base.mvpbase.BaseFragment;
import com.soyoung.component_base.util.SizeUtils;
import com.umeng.analytics.MobclickAgent;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouyang
 * @date 2018/11/11
 * @desc 共享相册
 */

@CreatePresenter(ShareGalleryPresenter.class)
public class ShareGalleryFragment extends BaseFragment implements ShareGalleryView {

    private RecyclerView recyclerView;
    private ShareGalleryAdapter adapter;

    private ShareGalleryPresenter presenter;

    private SmartRefreshLayout refreshLayout;
    private int page = 1;

    //private TimeTextView createGalleryTv;

    private TimeTextView addImage;
    private RelativeLayout relativeLayout;
    /**
     * add图片是否为初始状态
     * true 为初始状态
     */
    private boolean flag = true;

    private LinearLayout dropdownLl;

    private View bgView;



    @Override protected int setLayoutId() {
        return R.layout.share_gallery_fragment;
    }

    @Override protected void initView() {
        recyclerView = findViewById(R.id.share_gal_recycler);
        adapter = new ShareGalleryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addItemDecoration(new RecyclerViewItemDec(SizeUtils.dp2px(getActivity(),10)));
        refreshLayout = findViewById(R.id.share_refreshLayout);
        //dropdownLl = findViewById(R.id.share_gal_drop_ll);
        bgView = findViewById(R.id.share_gal_view);
        //createGalleryTv = findViewById(R.id.share_gal_create_gal);
        //暂时去掉header
        View headerView = initHeader();
        adapter.addHeaderView(headerView);
        initFooter();
    }

    private View initHeader(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.share_fragment_header,null);
        addImage = view.findViewById(R.id.share_gal_add_img);
        relativeLayout = view.findViewById(R.id.share_gal_add_img_rl);
        return view;
    }
    ImageView footer;
    private void initFooter(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment_bottom,null);
        footer = view.findViewById(R.id.share_list_footer_img);

        adapter.addFooterView(view);
    }

    @Override protected void initData(@Nullable Bundle savedInstanceState) {
        presenter = (ShareGalleryPresenter) getMvpPresenter();
        presenter.getData(page);
    }

    @Override public void setListener() {
        Disposable disposable = RxView.clicks(relativeLayout)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    Intent intent = new Intent(getActivity(),CreateGalleryActivity.class);
                    startActivity(intent);
                    //picAnimation();
                }
            });

        //Disposable disposable1 = RxView.clicks(createGalleryTv)
        //    .throttleFirst(500, TimeUnit.MILLISECONDS)
        //    .subscribe(o -> {
        //        startActivity(new Intent(getActivity(),CreateGalleryActivity.class));
        //    });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (contentData != null){
                    String albumid = contentData.get(i).getId();
                    Intent intent = new Intent(getActivity(),GalleryListActivity.class);
                    intent.putExtra("album_id",albumid);
                    intent.putExtra("name",contentData.get(i).getName());
                    intent.putExtra("model", contentData.get(i));
                    startActivity(intent);
                }
            }
        });
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getData(page);
            }

            @Override public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.getData(page);
            }
        });

        RxView.clicks(footer)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    Intent intent = new Intent(getActivity(),CreateGalleryActivity.class);
                    startActivity(intent);
                    //picAnimation();
                }
            });
    }
    List<ShareGalleryModel> contentData = new ArrayList<>();

    @Override public void showData(ShareGalleryBaseModel.ShareGalleryInnerModel model) {
        if (page == 1){
            contentData.clear();
        }
        contentData.addAll(model.getRows());
        if (page == 1) {
            adapter.setNewData(model.getRows());
        } else {
            adapter.addData(model.getRows());
        }

        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (model.getPage() >= model.getTotal_page()) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    /**
     * 加号图片的动画。
     */
    //private void picAnimation() {
    //    addImage.setPivotX(addImage.getWidth() / 2);
    //    addImage.setPivotY(addImage.getHeight() / 2);
    //    if (flag) {
    //        bgView.setVisibility(View.VISIBLE);
    //        AnimatorSet animatorSet = new AnimatorSet();
    //        animatorSet.playTogether(ObjectAnimator.ofFloat(addImage, "rotation", 0, 45)
    //            .setDuration(500), ObjectAnimator.ofFloat(dropdownLl, "translationY", 0,
    //            SystemUtils.dip2px(getActivity(), 240)).setDuration(500));
    //        animatorSet.start();
    //        flag = !flag;
    //    } else {
    //        bgView.setVisibility(View.GONE);
    //        AnimatorSet set = new AnimatorSet();
    //        set.playTogether(ObjectAnimator.ofFloat(addImage, "rotation", 45, 0)
    //            .setDuration(500), ObjectAnimator.ofFloat(dropdownLl, "translationY",
    //            SystemUtils.dip2px(getActivity(), 240), 0).setDuration(500));
    //        set.start();
    //        flag = !flag;
    //    }
    //}


    @Override
    public void onResume() {
        super.onResume();
        //统计页面，"MainScreen"为页面名称，可自定义
        MobclickAgent.onPageStart(ShareGalleryFragment.class.getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(ShareGalleryFragment.class.getSimpleName());
    }
}
