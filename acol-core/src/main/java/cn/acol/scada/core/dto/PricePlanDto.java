package cn.acol.scada.core.dto;



import lombok.Data;

@Data
public class PricePlanDto {
	private Long id;
	private String planName;
	/**
	 * 以下为当前的正在使用的
	 */
	private PriceType priceType; //价格类型
	
	private Float price; //价格
	
	private Long exDate; //方案开始可以使用的时间
}
