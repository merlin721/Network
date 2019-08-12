package com.merlin.time.my.feedback.adapter;

import android.view.View;

import com.merlin.time.R;
import com.merlin.time.my.feedback.model.FeedbackListBean;
import com.merlin.time.view.TimeTextView;

/**
 * @author merlin720
 * @date 2019/2/16
 * @mail zy44638@gmail.com
 * @description
 */
public class FeedbackReceiverHolder extends FeedbackBaseHolder {
    TimeTextView textView;

    public FeedbackReceiverHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.feedback_receive_text);
    }
    public void setData(FeedbackListBean model){
        textView.setText(model.getContent());
    }
}
