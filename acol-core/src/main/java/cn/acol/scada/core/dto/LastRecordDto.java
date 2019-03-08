package cn.acol.scada.core.dto;


import lombok.Data;

@Data
public class LastRecordDto {
	private int signal;//信号强度
	private float press;//压力值
	private float temp;//温度值
	private Long colTime;//采集时间
	private double scSum;//标况总量
	private double wcSum;//工况总量
	private float scFlow;//标况流量
	private float wcFlow;//工况流量
	private double surplusSum;//剩余气量
	private float meterVolt;//大芯板电池电压
	private float deviceVolt;//IC控制阀电压
	private int valueStatus;//阀门状态
	
}
