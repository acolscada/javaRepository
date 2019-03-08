package cn.acol.scada.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class ScadaParams {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int upTime;//上传周期
	private int colTime;//采集周期
	private boolean valueStatus;//当前阀门状态
	private double surplusSum;//剩余气量
	private double desAddPlusSum;//目标加入气量
	private int desUpTime;//目标上传周期
	private int desColTime;//目标采集周期
	private boolean desValueStatus;//目标阀门状态
	
	private long totalRecharge;//总充值额
	
	
	/**
	 * 同步时间  除了阶梯价格  
	 * 其他都将使用远程修改正常价格的形式进行修改
	 * 包括季节价格也是  调价基础为：synchronizationTime字段 和changeTime 给出方案的时间;
	 * 用于两个方案是否已经下发  价格和参数都是如此
	 */
	private Date changeDate; //设置时间
	private Date synchronizationDate;//同步时间
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="pricePlan")
	private PricePlan pricePlan;//当前正在使用的方案 必然存在
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="exPricePlan")
	private PricePlan exPricePlan; //希望使用的方案
	
	
	@OneToOne(mappedBy="scadaParams")
	private Scada scada;
}
