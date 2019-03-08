package cn.acol.scada.service;


import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.domain.User;

public interface UserService {
	public boolean updateUser(UserDto userDto);
	public User saveUser(UserDto userDto);
}
