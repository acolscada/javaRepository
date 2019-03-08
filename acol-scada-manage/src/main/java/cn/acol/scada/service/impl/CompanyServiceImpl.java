package cn.acol.scada.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.acol.scada.core.dto.CompanyDto;
import cn.acol.scada.core.dto.DeviceType;
import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.core.exception.AnaysisException;
import cn.acol.scada.dao.CompanyRepository;
import cn.acol.scada.dao.PriceRepository;
import cn.acol.scada.dao.ScadaRepository;
import cn.acol.scada.domain.Company;
import cn.acol.scada.domain.PricePlan;
import cn.acol.scada.domain.Scada;
import cn.acol.scada.domain.ScadaParams;
import cn.acol.scada.domain.User;
import cn.acol.scada.price.AbstractPriceStrategy;
import cn.acol.scada.service.CompanyService;
@Service
public class CompanyServiceImpl implements CompanyService{
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private ScadaRepository scadaRepository;
	@Autowired
	private PriceRepository priceRepository;
	
	@Override
	public User addUser(UserDto userDto) {
		// TODO Auto-generated method stub
		//AdminBase adminInfo = AdminUtils.getAdminInfo();
		/*if(adminInfo.getType().equals(AdminType.Company)){//确保为公司
			Company company = adminInfo.getCompany();
			Set<User> users = company.getUsers();
			if(users == null) {
				users = new HashSet<>();
			}
			User user = new User();
			Utils.changeUserDtoToUserWithoutScada(userDto, user);
			List<ScadaDto> scadas = userDto.getScadas();
			Set<Scada> scadaSet = new HashSet<>();
			for(ScadaDto scadaDto : scadas) {
				Scada scada = new Scada();
				Utils.ScadaDtoToScada(scadaDto, scada);
				scadaSet.add(scada);
				user.setScadas(scadaSet);
			}
			users.add(user);
			company.setUsers(users);
			companyRepository.save(company);
			return user;
		}
		return null;*/
		return null;
	}


	@Override
	public Company addCompany(CompanyDto companyDto,String adminName) {
		// TODO Auto-generated method stub
		Company company = new Company();
		try {
			BeanUtils.copyProperties(company, companyDto);
			company.setAdminName(adminName);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return companyRepository.save(company);
	}


	@Override
	public List<CompanyDto> getCompanys() {
		// TODO Auto-generated method stub
		List<Company> companys = companyRepository.findAll();
		List<CompanyDto> companyDtos = new ArrayList<>();
		for(Company company : companys) {
			try {
				CompanyDto companyDto = new CompanyDto();
				BeanUtils.copyProperties( companyDto,company );
				companyDtos.add(companyDto);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
		return companyDtos;
	}


	@Override
	public void deleteCompany(Long id) {
		// TODO Auto-generated method stub
		 companyRepository.deleteById(id);
	}
	
	@Override
	public SimpleResponse addDevice(ScadaDto scadaDto) {
		// TODO Auto-generated method stub
		if(scadaDto.getCompanyDto() == null || scadaDto.getCompanyDto().getId() == null) {
			return new SimpleResponse(-1, "确定有选择公司");
		}
		Company company = companyRepository.findById(scadaDto.getCompanyDto().getId()).get();
		if(company == null) {
			return new SimpleResponse(-1, "确认公司");
		}
		if(scadaDto.getUserDto() == null) {
			return new SimpleResponse(-1,"使用快捷开户API设置表时一定要设置用户信息");
		}
		Scada findByScaNum = scadaRepository.findByScaNum(scadaDto.getScaNum());
		if(findByScaNum !=null) {
			return new SimpleResponse(-1, "设备号不要重复");
		}
		Scada scada = new Scada();
		scada.setScaNum(scadaDto.getScaNum());
		scada.setColType(scadaDto.getColType());
		scada.setDeviceType(scadaDto.getDeviceType());
		ScadaParams scadaParams = new ScadaParams();
		scadaParams.setColTime(0);
		scadaParams.setDesColTime(60);
		scadaParams.setUpTime(0);
		scadaParams.setDesUpTime(1440);
		scadaParams.setChangeDate(new Date());
		if(scadaDto.getDeviceType() == DeviceType.PrepaymentIC) {
			ScaParamsDto scaParamsDto = scadaDto.getScaParamsDto();
			PricePlanDto pricePlanDto = scaParamsDto.getPricePlanDto();
			PricePlanDto exPricePlanDto = scaParamsDto.getExPricePlanDto();
			if(pricePlanDto == null || pricePlanDto.getId() ==null) {
				return new SimpleResponse(-1,"错误 IC类型设备必须要存在价格");
			}
			PricePlan pricePlan = priceRepository.findById(pricePlanDto.getId()).get();
			if(pricePlan == null) {
				return new SimpleResponse(-1,"错误 IC类型设备必须要存在价格");
			}
			if(exPricePlanDto!=null && exPricePlanDto.getId()!=null) {
				PricePlan exPricePlan = priceRepository.findById(exPricePlanDto.getId()).get();
				if(exPricePlan !=null) {
					scadaParams.setExPricePlan(exPricePlan);
				}
			}
			scadaParams.setPricePlan(pricePlan);
		}
		scada.setScadaParams(scadaParams);
		User user = new User();
		user.setCustomerAddress(scadaDto.getUserDto().getCustomerAddress());
		user.setCustomerName(scadaDto.getUserDto().getCustomerName());
		HashSet<Scada> hashSet = new HashSet<>();
		scada.setUser(user);
		hashSet.add(scada);
		user.setScadas(hashSet);
		user.setCompany(company);
		Set<User> users = company.getUsers();
		if(users == null || users.size() == 0) {
			users = new HashSet<>();
		}
		users.add(user);
		company.setUsers(users);
		 companyRepository.save(company);
		 return new  SimpleResponse(0,"ok");
	}
}
