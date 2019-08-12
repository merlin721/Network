package com.merlin.time.my.space;

import android.content.Context;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.merlin.time.R;
import com.merlin.time.common.CommonUtils;

/**
 * @author merlin720
 * @date 2019/3/30
 * @mail zy44638@gmail.com
 * @description
 */
public class SpaceDetailAdapter extends BaseQuickAdapter<SpaceDetailBean, BaseViewHolder> {

  Context context;

  public SpaceDetailAdapter(Context context) {
    super(R.layout.space_detail_item);
    this.context = context;
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, SpaceDetailBean spaceDetailBean) {
    baseViewHolder.setText(R.id.space_item_name, spaceDetailBean.getDesc());
    baseViewHolder.setText(R.id.space_item_time, spaceDetailBean.getC_t());
    baseViewHolder.setText(R.id.space_item_size,
        "+" + CommonUtils.setSize(Long.parseLong(spaceDetailBean.getSpace()) * 1024 * 1024));
  }
}
