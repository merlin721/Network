package com.merlin.time.main.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description  "page": "2",
 *         "per_page": "2",
 *         "total": "7",
 *         "total_page": 4
 */
public class HomeChildModel implements Serializable , MultiItemEntity {
    int type;
    private List<HomeChildListModel> rows;
    private int page;
    private int per_page;
    private int total;
    private int total_page;

    public List<HomeChildListModel> getRows() {
        return rows;
    }

    public void setRows(List<HomeChildListModel> rows) {
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

    @Override
    public int getItemType() {
        return type;
    }
}
