package cn.acol.scada.records.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import cn.acol.scada.core.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(
indexes= {@Index(columnList = "scaNum")})
public class UpRecord extends BaseDomain{
	private String scaNum;//编号
	@Column(name="sEnergy")
	private int signal;//信号强度
	private float press;//压力值
	private float temp;//温度值
	private Date colTime;//采集时间
	private double scSum;//标况总量
	private double wcSum;//工况总量
	private float scFlow;//标况流量
	private float wcFlow;//工况流量
	private double surplusSum;//剩余气量
	private float meterVolt;//大芯板电池电压
	private float deviceVolt;//IC控制阀电压
	private int valueStatus;//阀门状态
	@Column(name="upDescribe")
	private String describe;//描述
	private Date upTime;//上传时间
}
