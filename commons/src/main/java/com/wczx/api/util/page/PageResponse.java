package com.wczx.api.util.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageResponse {

	public PageResponse(Page page){
		this.current = page.getCurPage();
		this.pageSize = page.getPageSize();
		this.total = page.getTotalCount();
		this.pageCount = page.getPageCount();
	}

	/**
	 * 当前页页码
	 * <pre>
	 *     当前字段名不能更改, 否则会影响商户后台(storeBack)的分页功能
	 * </pre>
	 */
	private Integer current;

	/**
	 * 页面记录条数
	 * <pre>
	 *     当前字段名不能更改, 否则会影响商户后台(storeBack)的分页功能
	 * </pre>
	 */
	private Short pageSize;

	/**
	 * 总记录条数
	 * <pre>
	 *     当前字段名不能更改, 否则会影响商户后台(storeBack)的分页功能
	 * </pre>
	 */
	private Integer total;

	/**
	 * 总页数
	 */
	private Integer pageCount;

}
