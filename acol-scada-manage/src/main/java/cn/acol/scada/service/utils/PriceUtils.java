package cn.acol.scada.service.utils;
import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.PriceType;
import cn.acol.scada.core.exception.AnaysisException;
import cn.acol.scada.domain.PricePlan;

public class PriceUtils {
	/**
	 * 如果方案不合理基本就等于没有方案
	 * @param pricePlan
	 * @throws Exception
	 */
	public static  void assertPricePlan(PricePlan pricePlan) throws AnaysisException{
		if(pricePlan == null || pricePlan.getExDate() == null || pricePlan.getPlanName()==null ||pricePlan.getPlanName().trim().length() == 0) {//无方案时
			throw new AnaysisException("该方案不合理 请将此方案变为不存在");
		}
	}
	public static void assertPricePlan(PricePlanDto pricePlanDto) throws AnaysisException {
		if(pricePlanDto.getPriceType() == PriceType.Normal) {
			if(pricePlanDto.getPlanName() == null || pricePlanDto.getPrice() == null || pricePlanDto.getExDate() == null ||pricePlanDto.getPrice() == 0 || pricePlanDto.getPlanName().length() == 0) {
				throw new AnaysisException("此价格方案无法通过校验");
			}else {
				return ;
			}
		}else if(pricePlanDto.getPriceType()==PriceType.Gas) {
			if(pricePlanDto.getPlanName() == null || pricePlanDto.getExDate() == null || pricePlanDto.getPlanName().length() == 0) {
				throw new AnaysisException("此价格方案无法通过校验");
			}else {
				return ;
			}
		}
		throw new AnaysisException("目前支持普通和气量价格： 其他价格类型敬请期待");
	}
}
