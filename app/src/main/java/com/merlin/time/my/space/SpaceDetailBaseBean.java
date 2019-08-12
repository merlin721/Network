package com.merlin.time.my.space;

import com.merlin.time.entity.BaseMode;
import com.merlin.time.my.feedback.model.FeedbackListBean;
import java.io.Serializable;
import java.util.List;

/**
 * @author merlin720
 * @date 2019/3/30
 * @mail zy44638@gmail.com
 * @description
 */
public class SpaceDetailBaseBean extends BaseMode implements Serializable {
  private List<SpaceDetailBean> data;

  public List<SpaceDetailBean> getData() {
    return data;
  }

  public void setData(List<SpaceDetailBean> data) {
    this.data = data;
  }
}
