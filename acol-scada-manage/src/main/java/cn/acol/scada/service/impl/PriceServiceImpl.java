package cn.acol.scada.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.PriceType;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.exception.AnaysisException;
import cn.acol.scada.dao.PriceRepository;
import cn.acol.scada.domain.PricePlan;
import cn.acol.scada.service.PriceService;
import cn.acol.scada.service.utils.PriceUtils;

@Service
public class PriceServiceImpl implements PriceService{

	@Autowired
	private PriceRepository priceRepository;
	@Override
	public SimpleResponse addPricePlan(PricePlanDto price,String companyAdminName) {
		// TODO Auto-generated method stub
		try {
			PriceUtils.assertPricePlan(price);
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			return new SimpleResponse(-1,"无法正确解析 价格方案");
		}
		PricePlan pricePlan = new PricePlan();
		if(price.getPriceType() == PriceType.Normal) {
			pricePlan.setPriceType(PriceType.Normal);
			pricePlan.setPrice(price.getPrice());
			pricePlan.setPlanName(price.getPlanName());
			pricePlan.setCompanyAdminName(companyAdminName);//设置公司
			pricePlan.setExDate(new Date(price.getExDate()));//设置期望日期
			priceRepository.save(pricePlan);
			return SimpleResponse.getNormalResponse();
		}else if(price.getPriceType() == PriceType.Gas) {
			pricePlan.setPriceType(PriceType.Gas);
			pricePlan.setPlanName(price.getPlanName());
			pricePlan.setCompanyAdminName(companyAdminName);
			pricePlan.setExDate(new Date(price.getExDate()));
			priceRepository.save(pricePlan);
			return SimpleResponse.getNormalResponse();
		}
		return new SimpleResponse(-1, "价格类型错误 无此价格类型");
	}
	@Override
	public List<PricePlanDto> getPricePlan(String companyAdminName) {
		// TODO Auto-generated method stub
		List<PricePlan> pricePlans = priceRepository.findByCompanyAdminName(companyAdminName);
		List<PricePlanDto> pricePlanDtos = new ArrayList<PricePlanDto>();
		for(PricePlan pricePlan : pricePlans) {
			PricePlanDto pricePlanDto = new PricePlanDto();
			if(pricePlan.getPriceType() == PriceType.Normal) {
				pricePlanDto.setPriceType(PriceType.Normal);
				pricePlanDto.setPrice(pricePlan.getPrice());
				pricePlanDto.setPlanName(pricePlan.getPlanName());
				pricePlanDto.setExDate(pricePlan.getExDate().getTime());//设置期望日期
				pricePlanDto.setId(pricePlan.getId());
				pricePlanDtos.add(pricePlanDto);
			}else if(pricePlan.getPriceType() == PriceType.Gas) {
				pricePlanDto.setPriceType(PriceType.Gas);
				pricePlanDto.setPlanName(pricePlan.getPlanName());
				pricePlanDto.setExDate(pricePlan.getExDate().getTime());//设置期望日期
				pricePlanDto.setId(pricePlan.getId());
				pricePlanDtos.add(pricePlanDto);
			}
		}
		return  pricePlanDtos;
	}

}
