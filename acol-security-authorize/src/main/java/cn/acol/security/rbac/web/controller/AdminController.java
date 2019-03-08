
package cn.acol.security.rbac.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.security.rbac.dto.AdminCondition;
import cn.acol.security.rbac.dto.AdminInfo;
import cn.acol.security.rbac.dto.RoleInfo;
import cn.acol.security.rbac.service.AdminService;



/**
 * 
 * @author DaveZhang
 *
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 获取当前登录的管理员信息
	 * @param adminInfo
	 * @return
	 */
	@GetMapping("/me")
	public AdminInfo me(@AuthenticationPrincipal UserDetails user) {
		AdminInfo info = new AdminInfo();
		info.setUsername(user.getUsername());
		return info;
	}

	/**
	 * 创建管理员
	 * @param adminInfo
	 * @return
	 */
	@PostMapping
	public SimpleResponse create(@RequestBody AdminInfo adminInfo) {
		 AdminInfo admin = adminService.create(adminInfo);
		if(admin !=null) {
			return SimpleResponse.getNormalResponse();
		}
		else {
			return new SimpleResponse(-1,"添加失败,用户名重复");
		}
	}
	
	/**
	 * 修改管理员信息
	 * @param adminInfo
	 * @return
	 */
	@PutMapping("/{id}")
	public AdminInfo update(@RequestBody AdminInfo adminInfo) {
		System.out.println(adminInfo.getName()+ adminInfo.getRoles().get(0).getId());
		return adminService.update(adminInfo);
	}
	
	/**
	 * 删除管理员
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		adminService.delete(id);
	}
	
	/**
	 * 获取管理员详情
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public AdminInfo getInfo(@PathVariable Long id) {
		return adminService.getInfo(id);
	}
	
	/**
	 * 分页查询管理员
	 * @param adminInfo
	 * @param pageable
	 * @return
	 */
	@GetMapping
	public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
		return adminService.query(condition, pageable);
	}
	@GetMapping
	@RequestMapping("/getAdmins")
	public List<AdminInfo> getAdmins(){
		return adminService.getAdminInfos();
	}
	@GetMapping("/getAdmins/{name}")
	public List<AdminInfo> getAdminsByName(@PathVariable String name){
		List<AdminInfo> ret = new ArrayList<>();
		AdminInfo info = adminService.getInfo(name);
		if(info !=null) {
			ret.add(info);
		}
		return ret;
	}
	@GetMapping("/getRoles/{id}")
	public List<RoleInfo> findRoleInfosById(@PathVariable Long id){
		return adminService.getInfos(id);
	}
}
