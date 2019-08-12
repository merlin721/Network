package com.merlin.time.sharegallery.model;

import com.merlin.time.entity.BaseMode;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhouyang
 * @date 2018/11/18
 * @desc
 */
public class ShareGalleryBaseModel extends BaseMode {

    private ShareGalleryInnerModel data;

    public ShareGalleryInnerModel getData() {
        return data;
    }

    public void setData(ShareGalleryInnerModel data) {
        this.data = data;
    }

    public static class ShareGalleryInnerModel implements Serializable {
        private List<ShareGalleryModel> rows;
        private int page;
        private int per_page;
        private int total;
        private int total_page;

        public List<ShareGalleryModel> getRows() {
            return rows;
        }

        public void setRows(List<ShareGalleryModel> rows) {
            this.rows = rows;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }
    }
}
