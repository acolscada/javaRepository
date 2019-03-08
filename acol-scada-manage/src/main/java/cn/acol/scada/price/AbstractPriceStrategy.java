package cn.acol.scada.price;


import java.util.Date;
import java.util.Set;

import cn.acol.scada.core.dto.PriceType;
import cn.acol.scada.core.dto.TimeType;
import cn.acol.scada.domain.PricePlan;
import cn.acol.scada.domain.ScadaParams;
import cn.acol.scada.domain.SeasonPrice;
import cn.acol.scada.domain.StepPrice;
import cn.acol.scada.service.utils.PriceUtils;
import cn.acol.scada.service.utils.Utils;

public abstract class AbstractPriceStrategy{
	private ScadaParams scadaParams; //放心使用此方案此方案已经经过了严格的验证基本不会有问题
	private boolean change = false;
	public AbstractPriceStrategy(ScadaParams scadaParams) {
		assertParams(scadaParams);
		this.scadaParams = scadaParams;
	}
	/**
	 * 季节进行预处理
	 * 当处理不了时抛出异常
	 * @param seasonPrices 周期价格
	 * @param seasonType  周期类型
	 * @throws Exception
	 */
	protected abstract void seasonPriceCheck(Set<SeasonPrice> seasonPrices,TimeType seasonType) throws Exception;
	/**
	 * 阶梯价格预处理
	 * 当处理不了时抛出异常
	 * @param stepPrices 阶梯价格
	 * @param stepType 周期类型
	 * @throws Exception
	 */
	protected abstract void stepPriceCheck(Set<StepPrice> stepPrices,TimeType stepType) throws Exception;
	
	
	/**
	 * 如果方案不合理基本就等于没有方案
	 * @param pricePlan
	 * @throws Exception
	 */
	private void assertPricePlan(PricePlan pricePlan) throws Exception{
		PriceUtils.assertPricePlan(pricePlan);
		if(pricePlan.getPriceType() == PriceType.Season) {
			seasonPriceCheck(pricePlan.getSeasonPrices(), pricePlan.getSeasonType());
		}else if(pricePlan.getPriceType() == PriceType.Step) {
			stepPriceCheck(pricePlan.getStepPrices(), pricePlan.getStepType());
		}else if(pricePlan.getPriceType() == PriceType.Normal) {
			if(pricePlan.getPrice() <=0) {
				throw new Exception("常规价格模式价格不能小于等于0");
			}
		}else if(pricePlan.getPriceType() == PriceType.Gas) {
		}else {
			throw new Exception("价格方案不合理");
		}
		
	}
	private void assertParams(ScadaParams scadaParams) {
		if(scadaParams == null ) {
			throw new RuntimeException("设备参数为空");
		}
		
		try {
			assertPricePlan(scadaParams.getPricePlan());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		/**
		 * 当前价格的时间大于改变策略时的时间
		 */
		if( scadaParams.getPricePlan().getExDate().getTime()>scadaParams.getChangeDate().getTime()) {
			throw new RuntimeException("当前方案时间不合理");
		}
		
	}
	public ScadaParams producePriceStrategy(){
		//返回生成价格策略 所在的参数
		try {
			assertPricePlan(scadaParams.getExPricePlan()); //判断计划方案是否合理
		}catch (Exception e) {//不存在时
			// TODO: handle exception
			scadaParams.setExPricePlan(null);//未来方案设置为null
			return scadaParams; //返回方案所在参数
		}
		if(scadaParams.getExPricePlan().getExDate().getTime()<new Date().getTime()) {// 计划方案时间已经要实施了那么
			this.changeProject();//将计划方案加入到当前方案 抛弃当前方案
		}
		return scadaParams;
	}
	
	private void changeProject() {
		scadaParams.setPricePlan(scadaParams.getExPricePlan());
		scadaParams.setExPricePlan(null);
		scadaParams.setChangeDate(new Date());//方案变化了故而需要更新方案 需要同步
		change = true;
	}
	/**
	 * 方案是否有进行变化
	 * @return
	 */
	public boolean isChange() {
		return change;
	}
}
