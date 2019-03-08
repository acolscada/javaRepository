package cn.acol.scada.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.utils.AdminUtils;
import cn.acol.scada.service.PriceService;

@RestController
@RequestMapping("/price")
public class PriceController {
	@Autowired
	private PriceService priceService;
	
	@PostMapping
	public SimpleResponse addPrice(@RequestBody PricePlanDto pricePlanDto) {
		return priceService.addPricePlan(pricePlanDto, AdminUtils.getUserName());
	}
	@GetMapping
	public List<PricePlanDto> prices(){
		return priceService.getPricePlan(AdminUtils.getUserName());
	}
}
