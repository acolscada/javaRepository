package cn.acol.scada.core.dto;


import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ScadaDto {
	@NotBlank(message="设备编号不能为空")
	private String scaNum;
	private Long lastConTime;//最近一次联系时间
	private String colType;//422或485
	private Long scaPcbProducerTime;//pcb创建时间
	private DeviceType deviceType;
	private String version;//软件版本
	private String describe;//描述
	private String ICCID;//手机卡号
	private Integer signal;//最后一条信号
	private Float press;//压力值
	private Float temp;//温度值
	private Long colTime;//采集时间
	private Long upTime;//上传时间
	private Double scSum;//标况总量
	private Double wcSum;//工况总量
	private Float scFlow;//标况流量
	private Float wcFlow;//工况流量
	
	private Double surplusSum;//剩余气量
	private String pricePlan;
	
	private Float meterVolt;//大芯板电池电压
	private Float deviceVolt;//IC控制阀电压
	private Boolean valueStatus;//阀门状态
	private ScadaStatus scadaStatus;//系统状态
	private CompanyDto companyDto;
	private ScaParamsDto scaParamsDto;//采集器参数
	private UserDto userDto;
}
