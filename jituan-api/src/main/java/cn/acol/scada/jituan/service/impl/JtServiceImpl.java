package cn.acol.scada.jituan.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.core.utils.DiscoveryUtil;
import cn.acol.scada.jituan.dto.FailedSca;
import cn.acol.scada.jituan.dto.JtMessageLog;
import cn.acol.scada.jituan.dto.JtRMessage;
import cn.acol.scada.jituan.dto.JtReplayMessage;
import cn.acol.scada.jituan.repository.FailedScaRepository;
import cn.acol.scada.jituan.repository.JtRMessageRepository;
import cn.acol.scada.jituan.service.JtServcie;
@Component
public class JtServiceImpl implements JtServcie{
	Logger logger = LoggerFactory.getLogger(JtServiceImpl.class);
	//@Value("${jituan.huitian.url}")
	private String jtHuitianAddress;
	@Autowired
	private DiscoveryUtil discoveryUtil;
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private FailedScaRepository failedScaRepository;
	
	@Autowired
	private JtRMessageRepository jtRMessageRepository;
	@Override
	public JtRMessage paigong(FailedSca failedSca) {
		// TODO Auto-generated method stub
		
		//保存设备号  派工单号  派工时间 原因 以及派工备注 等
		failedScaRepository.save(failedSca);
		
		/**
		 * 与自己公司采集器同步信息
		 */
		
		//获取采集器
		String url;
		try {
			url = discoveryUtil.getAcolScadaManageUrl();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("scaNum"+failedSca.getShebeih());
			return new JtRMessage(-1,"维护人员维护时才会派工，如紧急请电话联系");
		}
		
		ScadaDto scadaDto = restTemplate.getForObject(url+"/scada/"+failedSca.getShebeih(), ScadaDto.class);
		
		/**
		 * 没有采集器 在对应公司中新建一个用户 并在用户中添加一个采集器
		 */
		if(scadaDto == null){
			UserDto userDto = new UserDto();
			userDto.setCustomerAddress(failedSca.getDz());
			userDto.setCustomerName(failedSca.getHm());
			List<ScadaDto> scadaDtos = new ArrayList<ScadaDto>();
			scadaDto = new ScadaDto();
			scadaDto.setScaNum(failedSca.getShebeih());
			scadaDtos.add(scadaDto);
			userDto.setScadas(scadaDtos);
			SimpleResponse simpleResponse = restTemplate.postForObject(url+"/user", userDto, SimpleResponse.class);
			if(simpleResponse.getErrorCode() != 0) {
				logger.error("scaNum"+failedSca.getShebeih()+"errorCode:"+simpleResponse.getErrorCode()+"  Msg:"+simpleResponse.getMessage());
				return new JtRMessage(-1,"维护人员维护时才会派工，如紧急请电话联系");
			}
			
		}
		
		/**
		 * 有采集器如果不相同修改一下采集器的用户信息
		 */
		else {
			UserDto userDto = scadaDto.getUserDto();
			boolean isSame = userIsSame(failedSca, userDto);
			if(!isSame) {
				//* 有采集器修改一下采集器的用户信息
				userDto.setCustomerAddress(failedSca.getDz());
				userDto.setCustomerName(failedSca.getHm());
				SimpleResponse simpleResponse = putForSimpleResponse(url+"/user", restTemplate, userDto);
				if(simpleResponse.getErrorCode() != 0) {
					//System.out.println(simpleResponse.getErrorCode()+simpleResponse.getMessage());
					logger.error("scaNum"+failedSca.getShebeih()+"errorCode:"+simpleResponse.getErrorCode()+"  Msg:"+simpleResponse.getMessage());
					return new JtRMessage(-1,"维护人员维护时才会派工，如紧急请电话联系");
				}
			}
		}
		return new JtRMessage(0, "ok");
	}
	private SimpleResponse putForSimpleResponse(String url, RestTemplate restTemplate, Object object) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		ObjectMapper mapper = new ObjectMapper();
		String requestBody = null;
		try {
			requestBody = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity<String> entity = new HttpEntity<>(requestBody,httpHeaders);
		ResponseEntity<SimpleResponse> resultEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, SimpleResponse.class);
		return  resultEntity.getBody();
	}
	private boolean userIsSame(FailedSca failedSca, UserDto userDto) {
		if(userDto.getCustomerAddress()!=null && userDto.getCustomerAddress().equals(failedSca.getDz())) {
			if(userDto.getCustomerName()!=null && userDto.getCustomerName().equals(failedSca.getHm())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public JtRMessage messagelog(JtMessageLog jtMessageLog) {
		// TODO Auto-generated method stub
		jtRMessageRepository.save(jtMessageLog);
		return new JtRMessage(0, "ok");
	}
	@Override
	public SimpleResponse huitian(JtReplayMessage jtReplayMessage) {
		// TODO Auto-generated method stub
		if(jtHuitianAddress == null) {
			throw new RuntimeException("集团回填地址为空，没有设置");
		}
		JtRMessage jtRMessage =  restTemplate.postForObject(jtHuitianAddress, jtReplayMessage, JtRMessage.class);
		if(jtRMessage.getErrorcode() == 0)
			return new SimpleResponse(0,"OK");
		else {
			return new SimpleResponse(-1, "回填失败----错误码："+jtRMessage.getErrorcode()+"  错误信息："+jtRMessage.getErrorcode());
		}
	}
}
