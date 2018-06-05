package com.itacademy.jd2.po.hotel.web.dto.list;

import java.util.List;

public class ListDTO<T> {

    public static final String SESSION_ATTR_NAME = "listDTO";

    private List<T> list;

    private long pageCount;

    private SortDTO sort;

    private int page = 1;

    private int itemsPerPage;

    private long totalCount;

    public ListDTO(final int itemsPerPage) {
        super();
        this.itemsPerPage = itemsPerPage;
    }

    public ListDTO() {
        this(5);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(final List<T> list) {
        this.list = list;
    }

    public SortDTO getSort() {
        return sort;
    }

    public void setSort(final SortDTO sort) {
        this.sort = sort;
    }

    public void setSort(final String sort) {
        if (sort == null) {
            return;
        }

        final String[] sortParams = sort.split(":");
        // unsafe operation below but assumes that JSP doesn't have bugs
        if (sortParams.length == 1) {
            setSort(new SortDTO(sortParams[0]));
        } else {
            setSort(new SortDTO(sortParams[0], "asc".equals(sortParams[1])));
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(final Integer pageNumber) {
        if ((pageNumber == null) || (pageNumber == 0)) {
            this.page = 1;
        } else {
            this.page = pageNumber;
        }
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public boolean isFirstPage() {
        return getPage() == 1;
    }

    public boolean isLastPage() {
        return getPage() >= this.pageCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
        this.pageCount = (totalCount / itemsPerPage);
        if ((totalCount % itemsPerPage) > 0) {
            this.pageCount++;
        }
    }

    public long getTotalCount() {
        return totalCount;
    }
}
