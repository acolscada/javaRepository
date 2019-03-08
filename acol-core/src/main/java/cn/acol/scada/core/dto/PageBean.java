package cn.acol.scada.core.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageBean <T>{
	private int total;//总数
	private int totalPage;//总页数
	private int pageNumber;//页码
	private int pageSize;//每页多少条
	private List<T> products;//本页内容
	
}
