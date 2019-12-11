package com.ayundao.base;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.*;

/**
 * @ClassName: Page<T>
 * @project: IYunDao
 * @author: 念
 * @Date: 2019/5/13 16:24
 * @Description: 分页
 * @Version: V2.0
 */
public class Page<T> implements Serializable, Iterator<T> {

    private static final long serialVersionUID = -2053800594583879853L;

    /**
     * 内容
     */
    private final List<T> content = new ArrayList<>();

    /**
     * 总记录数
     */
    private final long total;

    /**
     * 分页信息
     */
    private Pageable pageable;

    /**
     * 构造方法
     */
    public Page() {
        this.total = 0L;
        this.pageable = new Pageable();
    }

    /**
     * 构造方法
     *
     * @param content
     *            内容
     * @param total
     *            总记录数
     * @param pageable
     *            分页信息
     */
    public Page(List<T> content, long total, Pageable pageable) {
        this.content.addAll(content);
        this.total = total;
        this.pageable = pageable;
    }

    /**
     * 获取空分页
     *
     * @param pageable
     *            分页信息
     * @return 空分页
     */
    public static final <T> Page<T> emptyPage(Pageable pageable) {
        return new Page<>(Collections.<T>emptyList(), 0L, pageable);
    }

    /**
     * 获取页码
     *
     * @return 页码
     */
    public int getPageNumber() {
        return pageable.getPageNumber();
    }

    /**
     * 获取每页记录数
     *
     * @return 每页记录数
     */
    public int getPageSize() {
        return pageable.getPageSize();
    }

    /**
     * 获取搜索属性
     *
     * @return 搜索属性
     */
    public String[] getSearchKey() {
        return pageable.getSearchKey();
    }

    /**
     * 获取搜索值
     *
     * @return 搜索值
     */
    public String[] getSearchValue() {
        return pageable.getSearchValue();
    }

    /**
     * 获取排序
     *
     * @return 排序
     */
    public List<Order> getOrders() {
        return pageable.getOrders();
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public int getTotalPages() {
        return (int) Math.ceil((double) getTotal() / (double) getPageSize());
    }

    /**
     * 获取内容
     *
     * @return 内容
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 获取分页信息
     *
     * @return 分页信息
     */
    public Pageable getPageable() {
        return pageable;
    }

    /**
     * 重写equals方法
     *
     * @param obj
     *            对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * 重写hashCode方法
     *
     * @return HashCode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean hasNext() {
        return getContent().listIterator().hasNext();
    }

    @Override
    public T next() {
        return getContent().listIterator().next();
    }
}