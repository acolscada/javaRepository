package cn.acol.scada.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.discovery.Discovery;

@RestController
@RequestMapping("/price")
public class PriceController {
	private RestTemplate restTemplate;
	private Discovery discovery;
	@PostMapping
	public SimpleResponse addPrice(@RequestBody PricePlanDto pricePlanDto) {
		return restTemplate.postForObject(discovery.getScadaManageUrl()+"/price", pricePlanDto, SimpleResponse.class);
	}
	@GetMapping
	public List<PricePlanDto> prices(){
		PricePlanDto[] prices = restTemplate.getForObject(discovery.getScadaManageUrl()+"/price", PricePlanDto[].class);
		if(prices == null || prices.length == 0) {
			return null;
		}
		return Arrays.asList(prices);
	}
}
