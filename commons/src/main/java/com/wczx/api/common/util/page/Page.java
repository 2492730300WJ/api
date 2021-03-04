package com.wczx.api.common.util.page;

import lombok.Getter;

import java.util.List;

/**
 * @author Administrator
 */
@Getter
public class Page<T> {

	public Page(List<T> list, int curPage, int totalCount){
		this(list, DEFAULT_PAGE_SIZE, curPage, totalCount);
	}

	public Page(short pageSize, int curPage, int totalCount){
		this(null, pageSize, curPage, totalCount);
	}

	public Page(int curPage, int totalCount){
		this(null, DEFAULT_PAGE_SIZE, curPage, totalCount);
	}

	public Page(List<T> list, short pageSize, int curPage, int totalCount){
		this.pageSize = pageSize <= 0 || pageSize > 100 ? DEFAULT_PAGE_SIZE : pageSize;
		this.pageCount = totalCount / this.pageSize + (totalCount % this.pageSize > 0 ? 1 : 0);
		this.curPage = (curPage > pageCount ? pageCount : curPage) <= 0 ? DEFAULT_CUR_PAGE : curPage;// 不能大于总页数, 且不能小于 0
		this.totalCount = totalCount;
		this.startIndex = (this.curPage - 1) * this.pageSize;
		this.list = list;
	}

	/** 页面大小 */
	private short pageSize;

	/** 当前页码 */
	private int curPage;

	/** 页码总计 */
	private int pageCount;

	/** 记录总条数 */
	private int totalCount;

	/** 当前页起始位置 */
	private int startIndex;

	/** 默认页面大小 */
	public static final short DEFAULT_PAGE_SIZE = 10;

	/** 默认页码 */
	public static final short DEFAULT_CUR_PAGE = 1;

	private List<T> list;

	public List<T> getList() {
		return list;
	}
}
