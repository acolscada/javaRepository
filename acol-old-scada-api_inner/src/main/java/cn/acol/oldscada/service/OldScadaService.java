package cn.acol.oldscada.service;

import java.util.List;

import cn.acol.scada.core.dto.PageBean;
import cn.acol.scada.core.dto.UpRecordDto;

public interface OldScadaService {
	/**
	 * 通过采集器编号获取历史上传记录
	 * @param scaNum
	 * @return
	 */
	public List<UpRecordDto> getUpRecordsByScadaNum(String scaNum);
	
	/**
	 * 注意默认按照时间倒序排序
	 * @param scaNum   采集器编号
	 * @param pageSize 页面大小
	 * @param pageNum  需要的页数
	 * @return
	 */
	public PageBean<UpRecordDto> getPageUpRecords(String scaNum, Integer pageSize, Integer pageNum);
}
