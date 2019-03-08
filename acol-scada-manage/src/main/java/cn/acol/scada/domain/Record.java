package cn.acol.scada.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import cn.acol.scada.core.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 采集器系统从生产到销毁的记录信息
 * @author DaveZhang
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Record extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String people;//处理人
	private String handle;//处理方式
	private String reason;//原因
	private float press;//压力值
	private float temp;//温度值
	private double sfmSum;//小芯版显示总量
	private double fmSum;//大芯板标总
	@Column(name="sEnergy")
	private int signal;//信号强度
	private int scaMinColNum;//采集数
	private int scaMinUpNum;//上传数
	private String event;
}
