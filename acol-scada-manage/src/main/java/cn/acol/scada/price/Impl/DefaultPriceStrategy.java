package cn.acol.scada.price.Impl;

import java.util.Set;

import org.springframework.stereotype.Component;

import cn.acol.scada.core.dto.TimeType;
import cn.acol.scada.domain.ScadaParams;
import cn.acol.scada.domain.SeasonPrice;
import cn.acol.scada.domain.StepPrice;
import cn.acol.scada.price.AbstractPriceStrategy;

/**
 * 正常价格策略
 * @author DaveZhang
 *
 */
public class DefaultPriceStrategy extends AbstractPriceStrategy {
	
	public DefaultPriceStrategy(ScadaParams scadaParams) {
		super(scadaParams);
	}

	@Override
	protected void seasonPriceCheck(Set<SeasonPrice> seasonPrices, TimeType seasonType) throws Exception {
		// TODO Auto-generated method stub
		if(seasonType == TimeType.Year) {
			
		}else if(seasonType == TimeType.Month) {
			
		}else if(seasonType == TimeType.Day) {
			
		}else {
			throw new Exception("季节周期不存在");
		}
		if(seasonPrices == null || seasonPrices.size() == 0) {
			throw new Exception("季节价格不存在");
		}
	}

	@Override
	protected void stepPriceCheck(Set<StepPrice> stepPrices, TimeType stepType) throws Exception {
		// TODO Auto-generated method stub
		if(stepType == TimeType.Year) {
			
		}else if(stepType == TimeType.Month) {
			
		}else if(stepType == TimeType.Day) {
			
		}else {
			throw new Exception("阶梯周期不存在");
		}
		if(stepPrices == null || stepPrices.size() == 0) {
			throw new Exception("阶梯价格不存在");
		}
	}
}
