package cn.acol.scada.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.dao.UserRepository;
import cn.acol.scada.domain.User;
import cn.acol.scada.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public boolean updateUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userDto.getId()).get();
		user.setCustomerAddress(userDto.getCustomerAddress());
		user.setCustomerName(userDto.getCustomerName());
	    userRepository.save(user);
		return true;
	}
	@Override
	public User saveUser(UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
		
	}
	
}
