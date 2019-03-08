package cn.acol.security.rbac.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.acol.security.rbac.dto.AdminCondition;
import cn.acol.security.rbac.dto.AdminInfo;
import cn.acol.security.rbac.dto.ResourceInfo;
import cn.acol.security.rbac.dto.RoleInfo;


/**
 *  管理员服务
 * @author DaveZhang
 *
 */

public interface AdminService {

	/**
	 * 创建管理员
	 * @param adminInfo
	 * @return
	 */
	AdminInfo create(AdminInfo adminInfo);
	/**
	 * 修改管理员
	 * @param adminInfo
	 * @return
	 */
	AdminInfo update(AdminInfo adminInfo);
	/**
	 * 删除管理员
	 * @param id
	 */
	void delete(Long id);
	/**
	 * 获取管理员详细信息
	 * @param id
	 * @return
	 */
	AdminInfo getInfo(Long id);
	/**
	 * 获取管理原详细信息
	 * @param name
	 * @return
	 */
	AdminInfo getInfo(String name);
	/**
	 * 分页查询管理员
	 * @param condition
	 * @return
	 */
	Page<AdminInfo> query(AdminCondition condition, Pageable pageable);

	/**
	 * 获取所有管理员信息
	 * @return
	 */
	List<AdminInfo> getAdminInfos();
	/**
	 * 通过用户ID获取用户角色信息
	 * @param id
	 * @return
	 */
	List<RoleInfo> getInfos(Long id);
	ResourceInfo getTree(Long adminId);	
	
}
