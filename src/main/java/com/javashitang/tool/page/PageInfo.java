package com.javashitang.tool.page;

import java.io.Serializable;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = 9024038516714926576L;

    private int totalItem;
    private int totalPage;
    private int pageSize;
    private int nextPage;
    private int curPage;
    private int maxLife = 1;
    private boolean useCache = true;
    private boolean queryCount;


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

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
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

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public boolean isQueryCount() {
        return queryCount;
    }

    public void setQueryCount(boolean queryCount) {
        this.queryCount = queryCount;
    }
}
