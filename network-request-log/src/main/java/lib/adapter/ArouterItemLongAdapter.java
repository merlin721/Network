package lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import httploglib.lib.R;
import java.util.List;
import lib.data.ArouterValue;

public class ArouterItemLongAdapter
    extends RecyclerView.Adapter<ArouterItemLongAdapter.ViewHolder> {
    private List<ArouterValue> mList;
    private Context mContext;

    public ArouterItemLongAdapter(Context mContext, List<ArouterValue> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.arouter_item_pop, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        if (mList.size() > 0) {
            ArouterValue bean = mList.get(position);
            holder.tvKey.setText(bean.key);
            holder.tvValue.setText(bean.value);
        }
    }

    @Override public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        EditText tvKey;
        EditText tvValue;

        public ViewHolder(View itemView) {
            super(itemView);
            tvKey = itemView.findViewById(R.id.tvKey);
            tvValue = itemView.findViewById(R.id.tvValue);
        }
    }
}
