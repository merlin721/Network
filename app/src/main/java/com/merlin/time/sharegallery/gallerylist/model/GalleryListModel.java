package com.merlin.time.sharegallery.gallerylist.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouyang
 * @date 2018/12/22
 * @desc
 */
public class GalleryListModel implements Serializable {

    private List<GalleryListItemModel> rows;
    private int page;
    private int per_page;
    private int total;
    private int total_page;

    public List<GalleryListItemModel> getRows() {
        return rows;
    }

    public void setRows(
        List<GalleryListItemModel> rows) {
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
