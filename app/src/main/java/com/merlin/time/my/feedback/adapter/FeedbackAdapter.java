package com.merlin.time.my.feedback.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.merlin.time.R;
import com.merlin.time.my.feedback.model.FeedbackListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author merlin720
 * @date 2019/2/16
 * @mail zy44638@gmail.com
 * @description
 */
public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackBaseHolder> {

    private static int TYPE_SEND = 1;
    private static int TYPE_RECEIVER = 2;

    private Context context;

    private List<FeedbackListBean> mContentData;

    public FeedbackAdapter(Context context) {
        this.context = context;
        mContentData = new ArrayList<>();
    }

    public void setmContentData(List<FeedbackListBean> mContentData) {
        this.mContentData.clear();
        this.mContentData.addAll(mContentData);
    }


    @Override
    public int getItemViewType(int position) {
        if (mContentData.size() == 0){
            return 0;
        }
        return Integer.parseInt(mContentData.get(position).getType());
    }

    @NonNull
    @Override
    public FeedbackBaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEND) {
            return new FeedbackSendHolder(LayoutInflater.from(context).inflate(R.layout.feedback_send_item, null));
        } else if (viewType == TYPE_RECEIVER) {
            return new FeedbackReceiverHolder(LayoutInflater.from(context).inflate(R.layout.feedback_receiver_item, null));
        }
        return new FeedbackSendHolder(LayoutInflater.from(context).inflate(R.layout.feedback_send_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackBaseHolder holder, int position) {
        if (mContentData.size()>0) {
            FeedbackListBean model = mContentData.get(position);
            if (holder instanceof FeedbackSendHolder) {
                ((FeedbackSendHolder) holder).setData(model);
            }else if (holder instanceof  FeedbackReceiverHolder){
                ((FeedbackReceiverHolder) holder).setData(model);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mContentData.size();
    }
}
