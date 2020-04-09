package com.javashitang.tool.page;

import java.io.Serializable;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = 9024038516714926576L;

    private int totalItem;
    private int totalPage;
    private int pageSize;
    private int curPage;
    private int maxLife = 1;

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public void setTotalPage(int pageSize, int totalItem) {
        if (pageSize != 0 & totalItem != 0) {
            this.totalPage = (totalItem - 1) / totalPage + 1;
        }
    }
}
