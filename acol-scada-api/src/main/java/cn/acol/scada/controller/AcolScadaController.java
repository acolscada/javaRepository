package cn.acol.scada.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.acol.scada.core.dto.PageBean;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.core.utils.DiscoveryUtil;

@RestController
@RequestMapping("/oldScada")
public class AcolScadaController {
	@Autowired
	private DiscoveryUtil discoveryUtil;
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/users")
	@GetMapping
	public UserDto[] getUsers(){
		try {
			String uri = discoveryUtil.getAcolOldScadaApiUrl();
			return restTemplate.getForObject(uri+"/oldSys/custumers", UserDto[].class);
			//return discoveryUtil.restTemplate(uri).getForObject(uri+"/oldSys/custumers", UserDto[].class);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	@RequestMapping("/{userId}")
	@GetMapping
	public ScadaDto[] getCollectors(@PathVariable int userId) {
		try {
			String uri = discoveryUtil.getAcolOldScadaApiUrl();
			return restTemplate.getForObject(uri+"/scada/"+userId, ScadaDto[].class);
		} catch (Exception e) {
			return null;
		}
	}
	 @RequestMapping("/{scaNum}/records")
	 @GetMapping
	public UpRecordDto[] upRecordDtos(@PathVariable String scaNum) {
		 try {
			 String url = discoveryUtil.getAcolOldScadaApiUrl();
			 UpRecordDto[] upRecordDtos =  restTemplate.getForObject(url+"/scada/records/"+scaNum, UpRecordDto[].class);
			 return upRecordDtos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	 }
	 
	 @RequestMapping("/{scaNum}/upRecords")
	 @GetMapping
		public PageBean<UpRecordDto> upRecords(@PathVariable String scaNum, Integer pageSize, Integer pageNum) {
		 	 if(pageSize == null) {
		 		 pageSize = 10;
		 	 }
		 	 if(pageNum == null) {
		 		 pageNum = 0;
		 	 }
			 try {
				 String url = discoveryUtil.getAcolOldScadaApiUrl();
				 System.out.println("url:   "+ url);
				@SuppressWarnings("unchecked")
				PageBean<UpRecordDto> pageBean =  restTemplate.getForObject(url+"/scada/"+scaNum+"/pageRecords?pageSize="+pageSize+"&pageNum="+pageNum, PageBean.class);
				return pageBean;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		 }
}
