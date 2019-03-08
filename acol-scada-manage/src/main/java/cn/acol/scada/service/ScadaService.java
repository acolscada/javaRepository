package cn.acol.scada.service;

import java.util.List;

import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.domain.ScadaParams;

public interface ScadaService {
	/**
	 * 通过采集器编号是否存在采集器
	 * @param ScaNum
	 * @return 存在返回true 不存在返回false
	 */
	public boolean isExistScada(String scaNum);
	
	/**
	 * 通过采集器编号获取ScadaDto
	 * @param ScaNum
	 * @return
	 */
	public ScadaDto getScadaByScaNum(String scaNum);
	
	/**
	 * 更新最后一次上传的记录
	 * @param lastRecordDto  
	 * @param scaNum 设备号
	 * @return 
	 *  采集器不存在errorCode = -1
	 * 采集器存在但修改失败则 errorCode = 0
	 * 采集器存在且修改成功则errorCode = 1
	 * 
	 */
	public int updateRecords(LastRecordDto lastRecordDto, String scaNum);
	
	/**
	 * 获取采集器设置参数信息
	 * 
	 * @param scaNum
	 * @return 找不到返回null
	 */
	public ScaParamsDto getParams(String scaNum);
	/**
	 * 获取系统中所有的采集器
	 * @return
	 */
	public List<ScadaDto> getDevices();

	/**
	 * 
	 * @param deviceNum
	 * @return
	 */
	List<UpRecordDto> getUpRecords(String deviceNum);
	
	/**
	 * 获取所有的设备参数信息
	 * 
	 * @return
	 */
	public List<ScaParamsDto> getAllParams();
	
	/**
	 * @param deviceNum  表号包含字段查询
	 * @return
	 */
	public List<ScaParamsDto> getParamsbyDeviceNum(String deviceNum);
	/**
	 *认为进行采集器参数设置  修改的是中间缓存des数据
	 * @param scaParamsDto
	 * @return 简单的回复
	 */
	public SimpleResponse upDateParams(ScaParamsDto scaParamsDto);
	
	/**
	 * 远程控制完毕后进行结果的上传
	 * 无des部分
	 * @return
	 */
	public SimpleResponse submitParams(ScaParamsDto scaParamsDto);
	
	/**
	 * 强制设置开关阀
	 * @param deviceNum
	 * @param statusValue
	 * @return
	 */
	public SimpleResponse status(String deviceNum, boolean statusValue);
}
