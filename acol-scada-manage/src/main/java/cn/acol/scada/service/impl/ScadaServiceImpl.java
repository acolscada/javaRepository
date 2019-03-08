package cn.acol.scada.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.acol.scada.core.dto.DeviceType;
import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.core.utils.DiscoveryUtil;
import cn.acol.scada.dao.PriceRepository;
import cn.acol.scada.dao.ScadaRepository;
import cn.acol.scada.domain.LastRecord;
import cn.acol.scada.domain.PricePlan;
import cn.acol.scada.domain.Scada;
import cn.acol.scada.domain.ScadaParams;
import cn.acol.scada.domain.User;
import cn.acol.scada.price.Impl.DefaultPriceStrategy;
import cn.acol.scada.service.ScadaService;
import cn.acol.scada.service.utils.Utils;

@Service
public class ScadaServiceImpl implements ScadaService{
	
	
	private static final Logger log = LoggerFactory.getLogger(ScadaServiceImpl.class);

	@Autowired
	private ScadaRepository scadaRepository;
	@Autowired
	private PriceRepository priceRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DiscoveryUtil discoverUtil;
	@Override
	public boolean isExistScada(String scaNum) {
		// TODO Auto-generated method stub
		Scada scada = scadaRepository.findByScaNum(scaNum);
		if(scada!=null) {
			return true;
		}
		return false;
	}
	@Override
	public ScadaDto getScadaByScaNum(String scaNum) {
		// TODO Auto-generated method stub
		Scada scada = scadaRepository.findByScaNum(scaNum);
		ScadaDto scadaDto = new ScadaDto();
		if(scada != null) {
			Utils.ScadaToScadaDto(scada, scadaDto);
			User user = scada.getUser();
			UserDto userDto = new UserDto();
			Utils.changeUserToUserDtoWithoutScada(user, userDto);
			scadaDto.setUserDto(userDto);
			return scadaDto;
		}
		return null;
	}
	@Override
	public int updateRecords(LastRecordDto lastRecordDto, String scaNum) {
		// TODO Auto-generated method stub
		Scada scada = scadaRepository.findByScaNum(scaNum);
		if(scada !=null) {
			LastRecord lastRecord = scada.getLastRecord();
			if(lastRecord == null) {
				lastRecord = new LastRecord();
			}
			Utils.LastRecordDtoToLastRecord(lastRecordDto, lastRecord);
			scada.setLastRecord(lastRecord);
			scadaRepository.save(scada);
			return 1;
		}
		return -1;
		
	}
	@Override
	public ScaParamsDto getParams(String scaNum) {
		// TODO Auto-generated method stub
		Scada scada = scadaRepository.findByScaNum(scaNum);
		if(scada == null) {
			return null;
		}
		//获取参数
		ScadaParams scadaParams = scada.getScadaParams();
		if(scada.getDeviceType() == DeviceType.PrepaymentIC) {
			/**
			 * 对参数部分的价格进行检测
			 */
			DefaultPriceStrategy defaultPriceStrategy = new DefaultPriceStrategy(scadaParams);
			defaultPriceStrategy.producePriceStrategy();
		}
		scada.setLastConnetionTime(new Date());
		scadaRepository.save(scada);
		ScaParamsDto scaParamsDto = new ScaParamsDto();
		Utils.changeScaParamsToScaParamsDto(scadaParams, scaParamsDto);
		return scaParamsDto;
	}
	@Override
	public List<ScadaDto> getDevices() {
		// TODO Auto-generated method stub
		List<Scada> devices = scadaRepository.findAll();
		List<ScadaDto> devDtos = new ArrayList<ScadaDto>();
		for(Scada device : devices) {
			ScadaDto devDto = new ScadaDto();
			Utils.ScadaToScadaDto(device, devDto);
			devDtos.add(devDto);
		}
		return devDtos;
	}
	@Override
	public List<UpRecordDto> getUpRecords(String deviceNum){
		try {
			 UpRecordDto[] upRecordDtos = restTemplate.getForObject(discoverUtil.getRecordsUrl()+"/device/"+deviceNum, UpRecordDto[].class);
			 if(upRecordDtos == null || upRecordDtos.length == 0) {
				 return null;
			 }
			 List<UpRecordDto> lists = Arrays.asList(upRecordDtos);
			 Collections.sort(lists);
			 return lists;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("可能是上传记录服务器没找到  也可能是请求失败了："+ e.getMessage());
			return null;
		}
	}
	/**
	 * 应按照用户获取采集器然后再获取ScaParams
	 */
	@Override
	public List<ScaParamsDto> getAllParams() {
		// TODO Auto-generated method stub
		List<Scada> findAll = scadaRepository.findAll();
		return getScadaParamsByScadas(findAll);
	}
	@Override
	public List<ScaParamsDto> getParamsbyDeviceNum(String deviceNum) {
		// TODO Auto-generated method stub
		List<Scada> findAll = scadaRepository.findByNameLike(deviceNum);
		return getScadaParamsByScadas(findAll);
	}
	
	private List<ScaParamsDto> getScadaParamsByScadas(List<Scada> findAll) {
		List<ScaParamsDto> scaParamsDtos = new ArrayList<>();
		for(Scada s:findAll) {
			ScaParamsDto scaParamsDto = new ScaParamsDto();
			Utils.changeScaParamsToScaParamsDto(s.getScadaParams(), scaParamsDto);
			scaParamsDto.setDeviceNum(s.getScaNum());
			scaParamsDtos.add(scaParamsDto);
		}
		return scaParamsDtos;
	}
	@Override
	public SimpleResponse upDateParams(ScaParamsDto scaParamsDto) {
		// TODO Auto-generated method stub
		if(scaParamsDto == null || scaParamsDto.getDeviceNum() == null || scaParamsDto.getId() == null) {
			return new SimpleResponse(-1, null);
		}
		Scada scada = scadaRepository.findByScaNum(scaParamsDto.getDeviceNum());
		if(scada == null) {
			return new SimpleResponse(-1, "请不要修改设备编号");
		}
		ScadaParams scadaParams = scada.getScadaParams();
		if(scadaParams.getId() == scaParamsDto.getId()) {
			scadaParams.setChangeDate(new Date());
			scadaParams.setDesColTime(scaParamsDto.getDesColTime());//修改为目标采集间隔
			if(scada.getDeviceType() == DeviceType.PrepaymentIC) {
				if(scaParamsDto.getExPricePlanDto().getId() !=null) {
					Optional<PricePlan> findById = priceRepository.findById(scaParamsDto.getExPricePlanDto().getId());
					if(!findById.equals(Optional.empty())) {
						PricePlan pricePlan = findById.get();
						scadaParams.setExPricePlan(pricePlan);
					}
					
				}
			}
			scadaParams.setDesValueStatus(scaParamsDto.isDesValueStatus());//是否开关阀
			scadaParams.setDesAddPlusSum(scaParamsDto.getDesAddPlusSum());//剩余气量的加减
			scadaParams.setDesUpTime(scaParamsDto.getDesUpTime());//修改目标上传间隔
			scadaRepository.save(scada);
			return new SimpleResponse(0, "修改成功");
		}else {
			return new SimpleResponse(-1, "请不要修改设备编号");
		}
	}
	
	@Override
	public SimpleResponse submitParams(ScaParamsDto scaParamsDto) {
		// TODO Auto-generated method stub
		if(scaParamsDto == null || scaParamsDto.getDeviceNum() == null || scaParamsDto.getId() == null) {
			return new SimpleResponse(-1, null);
		}
		Scada scada = scadaRepository.findByScaNum(scaParamsDto.getDeviceNum());
		if(scada == null) {
			return new SimpleResponse(-1, "请不要修改设备编号");
		}
		ScadaParams scadaParams = scada.getScadaParams();
		if(scadaParams.getId() == scaParamsDto.getId()) {
			scadaParams.setSynchronizationDate(new Date());
			scadaParams.setUpTime(scaParamsDto.getUpTime());
			scadaParams.setColTime(scaParamsDto.getColTime());
			scadaParams.setValueStatus(scaParamsDto.isValueStatus());
			scadaParams.setDesAddPlusSum(scaParamsDto.getDesAddPlusSum());
			scadaParams.setTotalRecharge(scaParamsDto.getTotalRecharge());
			scadaRepository.save(scada);
			return new SimpleResponse(0, "修改成功");
		}else {
			return new SimpleResponse(-1, "请不要修改设备编号");
		}
	}
	@Override
	public SimpleResponse status(String deviceNum, boolean statusValue) {
		// TODO Auto-generated method stub
		Scada scada = scadaRepository.findByScaNum(deviceNum);
		if(scada == null) {
			return new SimpleResponse(-1, "请不要修改设备编号");
		}
		ScadaParams scadaParams = scada.getScadaParams();
		scadaParams.setValueStatus(!statusValue);
		scadaParams.setDesValueStatus(statusValue);
		scadaParams.setChangeDate(new Date());
		scadaRepository.save(scada);
		return SimpleResponse.getNormalResponse();
	}
	
	
}
