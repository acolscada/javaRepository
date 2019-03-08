package cn.acol.scada.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.core.utils.AdminUtils;

@RestController
@RequestMapping("/user")
public class UserController {
	/*@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@PutMapping
	public SimpleResponse updateUser(@RequestParam UserDto userDto) {
		String userName = AdminUtils.getUserName();
		
		/*boolean result = userService.updateUser(userDto);
		if(result == true) {
			return new SimpleResponse(0,"ok");
		}
		return new SimpleResponse(-1,"应该不会到这里？   用户ID："+userDto.getId());*/
	/*}
	
	@PostMapping
	public SimpleResponse saveUser(@RequestParam UserDto userDto) {
		
		companyService.addUser(userDto);
		return new SimpleResponse(0,"ok");
	}*/
}
