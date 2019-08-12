package com.merlin.time.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.merlin.time.R;
import com.merlin.time.main.model.CommonListModel;

import java.util.List;

/**
 * @author merlin720
 * @date 2019/3/2
 * @mail zy44638@gmail.com
 * @description
 */
public class CommonAdapter extends BaseMultiItemQuickAdapter<CommonListModel, BaseViewHolder> {

    public CommonAdapter(@Nullable List<CommonListModel> data) {
        super(data);
        addItemType(1, R.layout.home_child_common_item);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CommonListModel commonListModel) {
        try {
            baseViewHolder.setText(R.id.home_child_common_item_name,commonListModel.getUser_name()+"：");
            baseViewHolder.setText(R.id.home_child_common_item_content,commonListModel.getComment());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
