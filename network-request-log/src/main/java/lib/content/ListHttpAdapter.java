package lib.content;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import httploglib.lib.R;
import lib.NetworkRequestUtil;
import lib.adapter.MBaseAdapter;
import lib.data.HttpTransaction;

/**
 * @author liuml
 * @explain
 * @time 2017/3/28 11:11
 */

public class ListHttpAdapter extends MBaseAdapter<HttpTransaction> {
    Context context;
    int colorDefault;
    int colorRequested;
    int colorError;
    int color500;
    int color400;
    int color300;

    public ListHttpAdapter(Context context) {
        super(context);
        this.context = context;
        Resources res = context.getResources();
        colorDefault = res.getColor(R.color.chuck_status_200);
        colorRequested = res.getColor(R.color.chuck_status_requested);
        colorError = res.getColor(R.color.chuck_status_error);
        color500 = res.getColor(R.color.chuck_status_500);
        color400 = res.getColor(R.color.chuck_status_400);
        color300 = res.getColor(R.color.chuck_status_300);
    }

    @Override
    public int getItemResource() {
        return R.layout.chuck_list_item_transaction;
    }

    @Override
    public void getItemView(final int position, View convertView, ViewHolder holder) {
        final HttpTransaction transaction = getItem(position);
        if (null==transaction)return;
        TextView code = holder.getView(R.id.code);
        TextView path = holder.getView(R.id.path);
        TextView host = holder.getView(R.id.host);
        TextView start = holder.getView(R.id.start);
        TextView duration = holder.getView(R.id.duration);
        TextView size = holder.getView(R.id.size);
        ImageView ssl = holder.getView(R.id.ssl);
        Button mBtCopyData=holder.getView(R.id.bt_copy_data);
        String pathString=transaction.getMethod() + " " + transaction.getPath();
        path.setText(pathString);
        host.setText(transaction.getHost());
        start.setText(transaction.getRequestStartTimeString());
        ssl.setVisibility(transaction.isSsl() ? View.VISIBLE : View.GONE);

        if (transaction.getStatus() == HttpTransaction.Status.Complete) {
            code.setText(String.valueOf(transaction.getResponseCode()));
            duration.setText(transaction.getDurationString());
            size.setText(transaction.getTotalSizeString());
        } else {
            code.setText(null);
            duration.setText(null);
            size.setText(null);
        }
        if (transaction.getStatus() == HttpTransaction.Status.Failed) {
            code.setText("!!!");
        }
        setStatusColor(holder, transaction);
        mBtCopyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkRequestUtil.copy(context, "请求方式: " + transaction.getMethod() + "\n" + "请求地址: " +
                        transaction.getUrl() + "\n" + "请求参数: " + transaction.getRequestBody()
                        + "\n请求结果:" + transaction.getFormattedResponseBody());
            }
        });
    }

    private void setStatusColor(ViewHolder holder, HttpTransaction transaction) {
        int color;
        if (transaction.getStatus() == HttpTransaction.Status.Failed) {
            color = colorError;
        } else if (transaction.getStatus() == HttpTransaction.Status.Requested) {
            color = colorRequested;
        } else if (transaction.getResponseCode() >= 500) {
            color = color500;
        } else if (transaction.getResponseCode() >= 400) {
            color = color400;
        } else if (transaction.getResponseCode() >= 300) {
            color = color300;
        } else {
            color = colorDefault;
        }
        ((TextView)holder.getView(R.id.code)).setTextColor(color);
        ((TextView)holder.getView(R.id.path)).setTextColor(color);
    }
}
