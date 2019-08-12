package com.merlin.time.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.main.model.HomeChildListModel;
import com.merlin.time.utils.ExcutorPoolUtils;
import com.merlin.time.utils.SystemUtils;
import com.merlin.time.utils.Util;
import com.merlin.time.utils.WXUtils;
import com.merlin.time.view.TimeImageView;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.image.GlideApp;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.widget.FlowLayout;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description
 */
public class HomeChildAdapter extends BaseMultiItemQuickAdapter<HomeChildListModel, BaseViewHolder> {

    private static final int ITEM_TYPE = 1;
    private static final int CUS_TYPE = 0;
    private Context context;
    private int width;

    public HomeChildAdapter(Context context, List<HomeChildListModel> data) {
        super(data);
        this.context = context;

        addItemType(ITEM_TYPE, R.layout.home_child_item);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeChildListModel homeChildModel) {
        switch (homeChildModel.getItemType()) {
            case ITEM_TYPE:
                setItemData(baseViewHolder, homeChildModel);
                break;
            case CUS_TYPE:
                setItemData(baseViewHolder, homeChildModel);
                break;
        }
    }


    private void setItemData(BaseViewHolder holder, HomeChildListModel model) {
        try {
            JzvdStd playerStandard = holder.getView(R.id.videoplayer);

            TimeImageView pic = holder.getView(R.id.home_child_item_img);
            if ("1".equals(model.getType())){
                pic.setVisibility(View.VISIBLE);
                playerStandard.setVisibility(View.GONE);
                ImageWorker.loadImage(context, model.getImg_url_big(), pic);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) pic.getLayoutParams();
                if (!TextUtils.isEmpty(model.getWidth()) && !TextUtils.isEmpty(model.getHeight())) {
                    width = SystemUtils.getDisplayWidth(context);
                    layoutParams.width = width;
                    int localWidth = Integer.parseInt(model.getWidth());
                    int localHeight = Integer.parseInt(model.getHeight());
                    if (localHeight != 0 && localWidth != 0)
                        layoutParams.height = width *  localHeight / localWidth;
                    pic.setLayoutParams(layoutParams);
                }
            }else if ("2".equals(model.getType())){
                pic.setVisibility(View.GONE);
                playerStandard.setVisibility(View.VISIBLE);
                String url = model.getVideo_url();
                if (url.contains("https")){
                    url = url.replace("https","http");
                }
                LogUtils.e(url);
                playerStandard.setUp(url, "时间视频", Jzvd.SCREEN_WINDOW_LIST);
                ImageWorker.loadImage(mContext,model.getImg_url_big(),playerStandard.thumbImageView);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) playerStandard.getLayoutParams();
                if (!TextUtils.isEmpty(model.getWidth()) && !TextUtils.isEmpty(model.getHeight())) {
                    width = SystemUtils.getDisplayWidth(context);
                    layoutParams.width = width;
                    int localWidth = Integer.parseInt(model.getWidth());
                    int localHeight = Integer.parseInt(model.getHeight());
                    if (localHeight != 0 && localWidth != 0)
                        layoutParams.height = width *  localHeight / localWidth;
                    playerStandard.setLayoutParams(layoutParams);
            }}


            holder.setText(R.id.home_child_item_name, model.getImg_user_name());
            TimeImageView head = holder.getView(R.id.home_child_item_head);
            ImageWorker.loadImageCircle(context, model.getHead_img(), head);
            //什么时候上传的
            holder.setText(R.id.home_child_item_top_time, model.getTime_desc());
            // "img_area": 照片地区
            //            "img_city": 照片城市
            //            "lng": "116.39524722222",
            //            "lat": "39.971133333333",
            //            "exif_time": 拍摄时间戳
            String str = "";
            if (!TextUtils.isEmpty(model.getImg_city()) && !TextUtils.isEmpty(model.getExif_time())) {
                str = "拍摄于 " + model.getImg_city() + "·" + model.getImg_area() + "|" + model.getExif_time();
            } else if (TextUtils.isEmpty(model.getImg_city()) && !TextUtils.isEmpty(model.getExif_time())) {
                str = "拍摄于 " + model.getExif_time();
            } else {
                str = "无拍摄时间地点";
            }
            holder.setText(R.id.home_child_item_time, str);
            RecyclerView recyclerView = holder.getView(R.id.home_child_item_discuss_recycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            likeTv = holder.getView(R.id.home_child_item_like_tv);
            changeLikeStatus(model.getIs_like(), holder.getLayoutPosition());

            CommonAdapter commonAdapter = new CommonAdapter(model.getComment_list());
            recyclerView.setAdapter(commonAdapter);
            LinearLayout linearLayout = holder.getView(R.id.home_child_item_discuss_ll);
            Disposable disposable = RxView.clicks(linearLayout)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (null != discussListener) {
                                discussListener.onDiscussClick(model.getId(), holder.getLayoutPosition());
                            }
                        }
                    });

            LinearLayout like = holder.getView(R.id.home_child_item_like_ll);
            Disposable disposable2 = RxView.clicks(pic)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (null != imageClick) {

                                imageClick.imageClick(model.getImg_url_big());
                            }
                        }
                    });
            Disposable disposable1 = RxView.clicks(like)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (null != likeListener) {
                                if (!"1".equals(model.getIs_like())) {
                                    likeListener.onLikeClick(model, holder.getLayoutPosition());
                                }
                            }
                        }
                    });
            LinearLayout shareLl = holder.getView(R.id.home_share_ll);
            final Bitmap[] bitmap = {null};
            ExcutorPoolUtils.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        bitmap[0] = Glide.with(context)
                                .asBitmap()
                                .load(model.getImg_url_big())
                                .submit(width, 500)
                                .get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            TimeTextView likefl = holder.getView(R.id.item_like_fl);
            if (model.getUser_like_name().size()>0){
                likefl.setVisibility(View.VISIBLE);
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : model.getUser_like_name()) {
                    stringBuilder.append(s);
                    stringBuilder.append("、");
                }
                likefl.setText(stringBuilder.toString());
            }else {
                likefl.setVisibility(View.GONE);
            }

            RxView.clicks(shareLl)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
//                            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.send_img);

                            try {


                                //初始化 WXImageObject 和 WXMediaMessage 对象
                                WXImageObject imgObj = new WXImageObject(bitmap[0]);
                                WXMediaMessage msg = new WXMediaMessage();
                                msg.mediaObject = imgObj;

                                //设置缩略图
                                Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap[0], 150, 150, true);
//                                bitmap[0].recycle();
                                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

                                //构造一个Req
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = buildTransaction("img");
                                req.message = msg;
                                req.scene = mTargetScene;
//                            req.userOpenId = getOpenId();
                                //调用api接口，发送数据到微信
                                WXUtils.api.sendReq(req);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    TimeTextView likeTv;

    public void changeLikeStatus(String islike, int position) {
        LogUtils.d(islike);
        if (null != likeTv) {
            LogUtils.d("tv部位空");
            if ("1".equals(islike)) {
                likeTv.setText("已赞");
                //GlideApp.with(context).asGif().load(R.drawable.c_home_zan2).into(likeTv);

                likeTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.c_home_zan2, 0, 0, 0);
            } else {
                likeTv.setText("点赞");
                likeTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_unlike, 0, 0, 0);
            }
//            notifyItemChanged(position);
        }
    }

    private DiscussListener discussListener;

    public void setDiscussListener(DiscussListener discussListener) {
        this.discussListener = discussListener;
    }

    public interface DiscussListener {
        void onDiscussClick(String id, int position);
    }

    private LikeListener likeListener;

    public void setLikeListener(LikeListener likeListener) {
        this.likeListener = likeListener;
    }

    public interface LikeListener {
        void onLikeClick(HomeChildListModel model, int position);
    }

    private ImageClick imageClick;

    public void setImageClick(ImageClick imageClick) {
        this.imageClick = imageClick;
    }

    public interface ImageClick {
        void imageClick(String url);
    }

    /**
     * 把网络资源图片转化成bitmap
     *
     * @param url 网络资源图片
     * @return Bitmap
     */
    public Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }


}
