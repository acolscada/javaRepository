package cn.acol.scada.domain;


import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.acol.scada.core.dto.PriceType;
import cn.acol.scada.core.dto.TimeType;
import lombok.Data;

@Entity
@Data
@Table(
indexes= {@Index(columnList = "companyAdminName")})
public class PricePlan {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String planName;
	private String companyAdminName; //公司管理员开发人员或者管理员用户名
	/**
	 *新表添加价格方案时
	 *有一个当前方案 exDate <= now
	 *将要执行的方案 exDate > now
	 */
	private Date exDate;//期望方案开始可以使用的时间
	@Enumerated(EnumType.STRING)
	private PriceType priceType; //价格类型
	private float price;//常规价格
	/**
	 * 以季节计价时的周期设置
	 * 年、月、日
	 */
	@Enumerated(EnumType.STRING)
	private TimeType seasonType;
	/**
	 * 阶梯计价时的周期
	 * 年、月、日
	 */
	@Enumerated(EnumType.STRING)
	private TimeType stepType;
	

	
	@OneToMany(mappedBy = "pricePlan",cascade = CascadeType.ALL)
	private Set<SeasonPrice> seasonPrices;//季节价格
	
	@OneToMany(mappedBy = "pricePlan",cascade = CascadeType.ALL)
	private Set<StepPrice> stepPrices;//阶梯价格
	
	
	@OneToMany(mappedBy="pricePlan")
	private Set<ScadaParams> scadaParams;//价格计划
	
	@OneToMany(mappedBy="exPricePlan")
	private Set<ScadaParams> exScadaParams;//期望列
	
}
