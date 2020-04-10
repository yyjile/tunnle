package com.nuc.tunnel.until;

import com.github.pagehelper.PageInfo;

/**
 * @Author: admin
 * @Description: 分页类
 * @Modified By:
 */
public class PageVo<T> {
    /**
     * 数据
     */
    private T data;


    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 数据总数
     */
    private Integer totalElement;

    private Integer number;

    private Integer pageNo;
    /**
     * 每次查询的个数
     */
    private Integer size;

    /**
     * 是否为首页
     */
    private Boolean first;

    /**
     * 是否为尾页
     */
    private Boolean last;

    /**
     * 查出的元素个数
     */
    private Integer numberOfElement;

    /**
     * 排序方式
     */
    private Sort sort;

    public Integer calTotalPage(){
        totalPages = getTotalElement()/getSize();
        return (totalElement%size == 0) ? totalPages:totalPages+1;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(Integer totalElement) {
        this.totalElement = totalElement;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getNumberOfElement() {
        return numberOfElement;
    }

    public void setNumberOfElement(Integer numberOfElement) {
        this.numberOfElement = numberOfElement;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public PageVo getPageVo(T data, PageInfo pageInfo){
        PageVo pageVo = new PageVo();
        pageVo.setData(data);
        pageVo.setFirst(pageInfo.isIsFirstPage());
        pageVo.setLast(pageInfo.isIsLastPage());
        pageVo.setPageNo(pageInfo.getPageNum());
        pageVo.setSize(pageInfo.getPageSize());
        pageVo.setTotalElement(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        pageVo.setTotalPages(Integer.parseInt(String.valueOf(pageInfo.getPages())));
        pageVo.setNumberOfElement(pageInfo.getSize());
        return pageVo;
    }
}
