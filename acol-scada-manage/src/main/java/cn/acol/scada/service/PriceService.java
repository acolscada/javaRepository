package cn.acol.scada.service;

import java.util.List;

import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.SimpleResponse;

public interface PriceService {
	/**
	 * 新加一个价格方案
	 * @param price
	 * @param companyAdminName
	 * @return
	 */
	public SimpleResponse addPricePlan(PricePlanDto price,String companyAdminName);
	
	/**
	 * 通过公司名获取价格
	 * @param companyAdminName
	 * @return
	 */
	public List<PricePlanDto> getPricePlan(String companyAdminName);
}
