package lib.content;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import httploglib.lib.R;
import io.mattcarroll.hover.Content;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import lib.NetworkRequestUtil;
import lib.adapter.ArouterItemLongAdapter;
import lib.adapter.MBaseAdapter;
import lib.data.ArouterBean;
import lib.data.ArouterValue;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/7/24
 * description   : 路由
 */

public class ArouterNavigatorContent extends FrameLayout implements Content {
    private static final String TAG = ArouterNavigatorContent.class.getSimpleName();
    private EditText edit_arouter;
    private TextView arouter_go;
    private ListView arouterListView;
    private ArrayList<ArouterBean> arouterBeans;
    private ArouterAdapter arouterAdapter;
    //popData
    private ArouterItemLongAdapter popAdapter;
    private PopupWindow popupWindow;
    private RecyclerView recycleView;
    private TextView addParam;
    private TextView delParam;
    private EditText tvKey;
    private EditText tvValue;
    private List<ArouterValue> popList = new ArrayList<>();

    public ArouterNavigatorContent(@NonNull Context context) {
        super(context);
        initView(context);
    }

    private void initView(final Context context) {
        arouterBeans = new ArrayList<>();
        LayoutInflater.from(context).inflate(R.layout.arouter_root_view, this, true);
        edit_arouter = findViewById(R.id.edit_arouter);
        arouter_go = findViewById(R.id.arouter_go);
        arouterListView = findViewById(R.id.arouterListView);
        arouterAdapter = new ArouterAdapter(getContext());
        arouterListView.setAdapter(arouterAdapter);
        arouterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArouterBean item = arouterAdapter.getItem(position);
                //TODO: add params
                String routerPath = item.arouterPath;
                Map map = new HashMap();
                if (item.arouterParams.size() > 0) {
                    for (ArouterValue bean : item.arouterParams) {
                        map.put(bean.key, bean.value);
                    }
                }
                Log.e(TAG, "onItemClick(ArouterNavigatorContent.java:86)" + routerPath);
                NetworkRequestUtil.getInstance().changePage(routerPath, map);
            }
        });
        arouterAdapter.setIaddParams(new IaddParams() {
            @Override public void addParams(int position) {
                showKVpop(context, position);
            }
        });
        arouter_go.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = edit_arouter.getText().toString();
                if (!TextUtils.isEmpty(path)) {
                    NetworkRequestUtil.getInstance().changePage(path, null);
                }
            }
        });
    }

    private void showKVpop(Context context, final int position) {
        popList.clear();
        if (popupWindow == null) {
            popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
            View inflate = LayoutInflater.from(context).inflate(R.layout.arouter_pop, null);
            popupWindow.setContentView(inflate);
            popupWindow.setBackgroundDrawable(new ColorDrawable(
                ContextCompat.getColor(context, R.color.colorWhite)));//设置背景色，需要设置，不设置可能会造成返回键不起作用
            popupWindow.setFocusable(
                true);//物理键是否响应，为true时，点返回键  //popupWindow消失，为false时，点返回键activity消失。popupWindow.setOutsideTouchable(true);//点击popupWindow外面消失
            recycleView = inflate.findViewById(R.id.recycleView);
            recycleView.setAdapter(popAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recycleView.setLayoutManager(manager);
            popAdapter = new ArouterItemLongAdapter(context, popList);
            recycleView.setAdapter(popAdapter);
            addParam = inflate.findViewById(R.id.add);
            delParam = inflate.findViewById(R.id.del);
            tvKey = inflate.findViewById(R.id.tvKey);
            tvValue = inflate.findViewById(R.id.tvValue);
            addParam.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    if (!TextUtils.isEmpty(tvKey.getText()) && !TextUtils.isEmpty(
                        tvValue.getText())) {
                        ArouterValue arouterValue =
                            new ArouterValue(tvKey.getText().toString(),
                                tvValue.getText().toString());
                        popList.add(arouterValue);
                        popAdapter.notifyDataSetChanged();
                        arouterAdapter.getItem(position).arouterParams.add(arouterValue);
                    }
                }
            });
        }
        popupWindow.showAtLocation(this, Gravity.CENTER, 0, 0);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean isFullscreen() {
        return false;
    }

    @Override
    public void onShown() {
        try {
            FutureTask<List<ArouterBean>> futureTask =
                new FutureTask<List<ArouterBean>>(new Callable<List<ArouterBean>>() {
                    @Override
                    public List<ArouterBean> call() throws Exception {
                        HashMap<String, String> arouterData =
                            NetworkRequestUtil.getInstance().getArouterData(getContext());
                        List<ArouterBean> arouterBeans = new ArrayList<>();
                        Set<Map.Entry<String, String>> entries = arouterData.entrySet();
                        for (Map.Entry<String, String> entry : entries) {
                            ArouterBean arouterBean = new ArouterBean();
                            arouterBean.arouterPageName = entry.getKey();
                            arouterBean.arouterPath = entry.getValue();
                            arouterBeans.add(arouterBean);
                        }
                        return arouterBeans;
                    }
                }) {
                    @Override
                    protected void done() {
                        edit_arouter.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    arouterAdapter.setList(get());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                };
            new Thread(futureTask).start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onShown(ArouterNavigatorContent.java:95)" + e.getMessage());
        }
    }

    @Override
    public void onHidden() {

    }

    interface IaddParams {
        void addParams(int position);
    }

    public static class ArouterAdapter extends MBaseAdapter<ArouterBean> {

        private IaddParams iaddParams;

        public ArouterAdapter(Context context) {
            super(context);
        }

        public void setIaddParams(IaddParams iaddParams) {
            this.iaddParams = iaddParams;
        }

        @Override
        public int getItemResource() {
            return R.layout.arouter_item;
        }

        @Override
        public void getItemView(int position, View convertView, ViewHolder holder) {
            ArouterBean item = getItem(position);
            TextView arouter_path = (TextView) holder.getView(R.id.arouter_path);
            TextView arouter_name = (TextView) holder.getView(R.id.arouter_name);
            arouter_path.setText(item.arouterPath);
            arouter_name.setText(item.arouterPageName);
            final int finalPosition = position;
            arouter_name.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    if (iaddParams != null) {
                        iaddParams.addParams(finalPosition);
                    }
                }
            });
        }
    }
}
