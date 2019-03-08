package cn.acol.scada.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class LastRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
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
	
	@OneToOne(mappedBy="lastRecord")
	private Scada scada;
}
