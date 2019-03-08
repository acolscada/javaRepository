package cn.acol.scada.core.utils;

import org.springframework.data.domain.Page;

import cn.acol.scada.core.dto.PageBean;

public class CUtils {
	/**
	 * 把分页通用属性拷贝到PageBean
	 * @param page
	 * @return
	 */
	public static <T,J> void changePageToPageBean(Page<J> page,PageBean<T> pageBean){
		pageBean.setPageNumber(page.getNumber());
		pageBean.setPageSize(page.getPageable().getPageSize());
		pageBean.setTotal(page.getSize());
		pageBean.setTotalPage(page.getTotalPages());
	}
}
