package com.wczx.api.util.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {

	/** 分页:当前页页码 */
	private int curPage =Page.DEFAULT_CUR_PAGE;

	/** 分页:当前页 记录条数 */
	private short pageSize = Page.DEFAULT_PAGE_SIZE;

}
