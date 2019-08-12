package com.soyoung.component_base.mvpbase;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter帮助类
 * 必须实现两个方法
 * getItemView
 * getItemResource
 * @author tang
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    public List<T> datas = new ArrayList<T>();
    public Context context;

    public BaseListViewAdapter(Context context) {
        this.context = context;
    }

    public BaseListViewAdapter(Context context, List<T> datas) {
        this.context = context;
        if (datas == null) return;
        this.datas.clear();
        this.datas.addAll(datas);

    }

    public void removeAll() {
        this.datas.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return datas;
    }


    public void onBoundData(List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setList(List list) {
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (position < 0 || position >= datas.size()) return;
        this.datas.remove(position);
        notifyDataSetChanged();
    }

    public void remove(T object) {
        this.datas.remove(object);
        notifyDataSetChanged();
    }

    public void addAll(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void add(T object) {
        this.datas.add(object);
    }

    public void add(int location, T object) {
        this.datas.add(location, object);
        notifyDataSetChanged();
    }


    public void addAll(int location, List<T> object) {
        this.datas.addAll(location, object);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        datas.clear();
        datas.addAll(elem);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (position >= datas.size()) return null;
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 此方法必须实现  获取layout  id
     *
     * @return
     */
    public abstract int getItemResource();

    /**
     * 此方法必须实现 绑定数据
     *
     * @param position
     * @param convertView
     * @param holder
     * @return
     */
    public abstract void getItemView(int position, View convertView, ViewHolder holder);

    @SuppressWarnings({"unchecked"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, getItemResource(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        getItemView(position, convertView, holder);
        return convertView;
    }

    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        @SuppressWarnings({"unchecked", "hiding"})
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }

}
