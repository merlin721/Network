package com.merlin.time.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.TimeApplication;
import com.merlin.time.common.CommonUtils;
import com.merlin.time.login.LoginActivity;
import com.merlin.time.my.about.AboutActivity;
import com.merlin.time.my.edit.EditMessageActivity;
import com.merlin.time.my.feedback.FeedbackActivity;
import com.merlin.time.my.invite.InviteActivity;
import com.merlin.time.my.model.MyCenterModel;
import com.merlin.time.my.model.MyCenterSignBean;
import com.merlin.time.my.presenter.MyCenterPresenter;
import com.merlin.time.my.space.SpaceDetailActivity;
import com.merlin.time.my.space.SpaceDetailAdapter;
import com.merlin.time.my.view.MyCenterView;
import com.merlin.time.utils.SystemUtils;
import com.merlin.time.utils.WXUtils;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.mvp.factory.CreatePresenter;
import com.soyoung.component_base.mvpbase.BaseFragment;
import com.soyoung.component_base.util.ToastUtils;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.umeng.analytics.MobclickAgent;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;

/**
 * @author zhouyang
 * @date 2018/11/11
 * @desc
 */
@CreatePresenter(MyCenterPresenter.class)
public class MyCenterFragment extends BaseFragment implements MyCenterView {
  private TimeTextView logoutTv;
  private ImageView headImg;
  private TimeTextView phoneTv;
  private TimeTextView nameTv;
  private TimeTextView spaceTv;
  private TimeTextView my_center_sign_top_tv;
  private ProgressBar progressBar;

  private RelativeLayout feedbackRl;
  private RelativeLayout aboutRl;
  private RelativeLayout shareRl;
  private RelativeLayout signRl;
  private RelativeLayout askRl;
  private RelativeLayout my_center_expand_desc;

  private MyCenterPresenter presenter;

  @Override
  protected int setLayoutId() {
    return R.layout.my_center_fragment;
  }

  @Override
  protected void initView() {
    headImg = findViewById(R.id.my_center_head_img);
    phoneTv = findViewById(R.id.my_center_phone);
    logoutTv = findViewById(R.id.my_center_logout_tv);
    nameTv = findViewById(R.id.my_center_name);
    spaceTv = findViewById(R.id.my_center_phone_size);
    progressBar = findViewById(R.id.my_center_progress);
    feedbackRl = findViewById(R.id.my_center_feedback_rl);
    aboutRl = findViewById(R.id.my_center_about_rl);
    shareRl = findViewById(R.id.my_center_share_rl);
    signRl = findViewById(R.id.my_center_sign_rl);
    askRl = findViewById(R.id.my_center_ask_rl);
    my_center_expand_desc = findViewById(R.id.my_center_expand_desc);

    my_center_sign_top_tv = findViewById(R.id.my_center_sign_top_tv);
  }

  @Override
  protected void initData(@Nullable Bundle savedInstanceState) {
    presenter = (MyCenterPresenter) getMvpPresenter();
    EventBus.getDefault().register(this);
    ImageWorker.loadImageCircle(getActivity(),
        AppPreferencesHelper.getString(Constant.U_IMAGE_URL), headImg, R.drawable.my_img_head);
    phoneTv.setText(AppPreferencesHelper.getString(Constant.USER_PHONE));
    nameTv.setText(AppPreferencesHelper.getString(Constant.USER_NAME, ""));

    spaceTv.setText("已使用" + AppPreferencesHelper.getString(Constant.USED_TOTAL_DESC));
    int totalSpace = AppPreferencesHelper.getInt(Constant.TOTAL_SPACE, 0);
    if (0 != totalSpace) {
      progressBar.setMax(totalSpace);
    } else {
      progressBar.setMax(100);
    }
    int userRate = AppPreferencesHelper.getInt(Constant.USER_SPACE, 0);
    if (0 != userRate) {
      progressBar.setProgress(userRate);
    } else {
      progressBar.setProgress(0);
    }
    String isSignIn = AppPreferencesHelper.getString(Constant.IS_SIGN_IN, "-1");

    setSignTv(isSignIn);
  }

  private void setSignTv(String isSignIn) {
    if (!"-1".equals(isSignIn)) {
      if ("0".equals(isSignIn)) {
        my_center_sign_top_tv.setText("每日签到");
        my_center_sign_top_tv.setTextColor(
            ContextCompat.getColor(getActivity(), R.color.color_text_color));
      } else if ("1".equals(isSignIn)) {
        my_center_sign_top_tv.setText("今日已签到");
        my_center_sign_top_tv.setTextColor(
            ContextCompat.getColor(getActivity(), R.color.color_4caf50));
      }
    }
  }

  @Override
  public void setListener() {
    Disposable disposable5 = RxView.clicks(nameTv)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            editName();
          }
        });
    Disposable disposable6 = RxView.clicks(headImg)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            editName();
          }
        });
    Disposable disposable = RxView.clicks(logoutTv)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            logout();
          }
        });

    Disposable disposable1 = RxView.clicks(feedbackRl)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            feedback();
          }
        });
    Disposable disposable3 = RxView.clicks(aboutRl)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            goAbout();
          }
        });
    Disposable disposable4 = RxView.clicks(shareRl)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            //CommonUtils.showShare("QQ.NAME",getActivity());
            share();
          }
        });
    Disposable disposable7 = RxView.clicks(signRl)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            String isSignIn = AppPreferencesHelper.getString(Constant.IS_SIGN_IN, "-1");
            if (!"-1".equals(isSignIn)) {
              if ("0".equals(isSignIn)) {
                presenter.sign();
              } else if ("1".equals(isSignIn)) {
                createSignedDialog();
              }
            }
          }
        });
    Disposable disposable8 = RxView.clicks(askRl)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            startActivity(new Intent(getActivity(), InviteActivity.class));
          }
        });
    Disposable disposable9 = RxView.clicks(my_center_expand_desc)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            startActivity(new Intent(getActivity(), SpaceDetailActivity.class));
          }
        });
  }

  private void share() {
    TDialog.Builder builder = new TDialog.Builder(getFragmentManager());
    builder.setLayoutRes(R.layout.share_dialog)
        .setWidth(SystemUtils.getDisplayWidth(getActivity()))
        .setHeight(SystemUtils.dip2px(getActivity(), 181))
        .setGravity(Gravity.BOTTOM)
        .addOnClickListener(R.id.share_dialog_qq_tv, R.id.share_dialog_wechat_tv,
            R.id.logout_dialog_logout_cancel_tv)
        .setOnViewClickListener(new OnViewClickListener() {
          @Override
          public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
            if (view == viewHolder.getView(R.id.share_dialog_qq_tv)) {
              //                       CommonUtils.showShare("QQ",getActivity());
              shareqq();
            }
            if (view == viewHolder.getView(R.id.share_dialog_wechat_tv)) {
              if (WXUtils.api.isWXAppInstalled()) {
                WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
                miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
                miniProgramObj.miniprogramType =
                    WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
                miniProgramObj.userName = "gh_e669aa61f016";     // 小程序原始id
                miniProgramObj.path = "/pages/landing/index";            //小程序页面路径
                WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
                msg.title = "时间瓶";                    // 小程序消息title
                msg.description = "时间瓶";               // 小程序消息desc
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
                Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                msg.thumbData = Bitmap2Bytes(sendBitmap);                      // 小程序消息封面图片，小于128k

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "miniProgram";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
                WXUtils.api.sendReq(req);
              } else {
                ToastUtils.showToast(getActivity(), "请先安装微信");
              }
            } else {
              tDialog.dismiss();
            }
          }
        })
        .create()
        .show();
  }

  public void shareqq() {
    Bundle params = new Bundle();
    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
    params.putString(QQShare.SHARE_TO_QQ_TITLE, "时间瓶");// 标题
    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "与爱的人分享生活照片");// 摘要
    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");// 内容地址
    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
        "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
    params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "");
    // 分享操作要在主线程中完成
    TimeApplication.mTencent.shareToQQ(getActivity(), params, qqShareListener);
  }

  IUiListener qqShareListener = new IUiListener() {
    @Override
    public void onCancel() {
      //            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
      //                Util.toastMessage(QQShareActivity.this, "onCancel: ");
      //            }
    }

    @Override
    public void onComplete(Object response) {
      // TODO Auto-generated method stub
      ToastUtils.showToast(getActivity(), "onComplete: " + response.toString());
    }

    @Override
    public void onError(UiError e) {
      // TODO Auto-generated method stub
      ToastUtils.showToast(getActivity(), "onError: " + e.toString());
      //            Util.toastMessage(QQShareActivity.this, "onError: " + e.errorMessage, "e");
    }
  };

  //    特别注意：一定要添加以下代码，才可以从回调listener中获取到消息
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (null != TimeApplication.mTencent) {
      TimeApplication.mTencent.onActivityResult(requestCode, resultCode, data);
    }
  }

  /**
   * 微信分享bitmap转化成byte
   */
  public byte[] Bitmap2Bytes(Bitmap bm) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
    return baos.toByteArray();
  }

  /**
   * 用户反馈
   */
  private void feedback() {
    startActivity(new Intent(getActivity(), FeedbackActivity.class));
  }

  /**
   * 关于我们
   */
  private void goAbout() {
    startActivity(new Intent(getActivity(), AboutActivity.class));
  }

  private void logout() {
    TDialog.Builder builder = new TDialog.Builder(getFragmentManager());
    builder.setLayoutRes(R.layout.logout_dialog)
        .setWidth(SystemUtils.getDisplayWidth(getActivity()))
        .setHeight(SystemUtils.dip2px(getActivity(), 181))
        .setGravity(Gravity.BOTTOM)
        .addOnClickListener(R.id.logout_dialog_logout_tv, R.id.logout_dialog_logout_cancel_tv)
        .setOnViewClickListener(new OnViewClickListener() {
          @Override
          public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
            if (view == viewHolder.getView(R.id.logout_dialog_logout_tv)) {
              AppPreferencesHelper.clear();
              tDialog.dismiss();
              startActivity(new Intent(getActivity(), LoginActivity.class));
              getActivity().finish();
            } else {
              tDialog.dismiss();
            }
          }
        })
        .create()
        .show();
  }

  AlertDialog alertDialog;

  private void createSignDialog(String msize) {
    View mLmCountView =
        LayoutInflater.from(getActivity()).inflate(R.layout.my_center_sign_dialog, null);

    TimeTextView size = mLmCountView.findViewById(R.id.my_center_dialog_size);
    ImageView close = mLmCountView.findViewById(R.id.my_center_close_img);
    size.setText(Integer.parseInt(msize) / 1024 +"");
    RxView.clicks(close)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            alertDialog.dismiss();
            alertDialog = null;
          }
        });

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog_Fullscreen);
    builder.setCancelable(true);
    builder.setView(mLmCountView);
    alertDialog = builder.create();

    if (null != alertDialog && !alertDialog.isShowing()) {
      alertDialog.show();
      Window window = alertDialog.getWindow();
      window.setDimAmount(0.4f);
      window.setGravity(Gravity.CENTER);
      WindowManager.LayoutParams lp = window.getAttributes();
      lp.width = SystemUtils.getDisplayWidth(getActivity()) - SystemUtils.dip2px(getActivity(), 20);
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);
    }
  }
  private void createSignedDialog() {
    View mLmCountView =
        LayoutInflater.from(getActivity()).inflate(R.layout.my_center_signed_dialog, null);

    ImageView close = mLmCountView.findViewById(R.id.my_center_close_img);
    RxView.clicks(close)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            alertDialog.dismiss();
            alertDialog = null;
          }
        });

    RelativeLayout invite = mLmCountView.findViewById(R.id.signed_go_ask);

    RxView.clicks(invite)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            startActivity(new Intent(getActivity(), InviteActivity.class));
            alertDialog.dismiss();
            alertDialog = null;
          }
        });
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog_Fullscreen);
    builder.setCancelable(true);
    builder.setView(mLmCountView);
    alertDialog = builder.create();

    if (null != alertDialog && !alertDialog.isShowing()) {
      alertDialog.show();
      Window window = alertDialog.getWindow();
      window.setDimAmount(0.4f);
      window.setGravity(Gravity.CENTER);
      WindowManager.LayoutParams lp = window.getAttributes();
      lp.width = SystemUtils.getDisplayWidth(getActivity()) - SystemUtils.dip2px(getActivity(), 20);
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);
    }
  }

  private void editName() {
    startActivity(new Intent(getActivity(), EditMessageActivity.class));
  }

  @Override
  public void onResume() {
    super.onResume();
    //统计页面，"MainScreen"为页面名称，可自定义
    MobclickAgent.onPageStart(MyCenterFragment.class.getSimpleName());
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(getClass().getSimpleName());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void eventBus(Message message) {
    if (message.what == CommonUtils.EVENT_CHANGE_NAME_CODE) {
      nameTv.setText((String) message.obj);
    } else if (message.what == CommonUtils.EVENT_CHANGE_PIC_CODE) {
      ImageWorker.loadImageCircle(getActivity(), (String) message.obj, headImg);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
  }

  @Override public void showData(MyCenterModel model) {

  }

  String signSize;

  @Override public void signSuccess(MyCenterSignBean model) {
    signSize = model.getAdd_space_size();
    createSignDialog(model.getAdd_space_size());
    setSignTv("1");
  }

  @Override public void signedSuccess() {
    createSignedDialog();
  }
}
