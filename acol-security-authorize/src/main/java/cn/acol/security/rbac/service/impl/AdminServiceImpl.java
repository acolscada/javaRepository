package cn.acol.security.rbac.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.acol.scada.core.exception.AnaysisException;
import cn.acol.scada.core.exception.SqlNoExistException;
import cn.acol.scada.core.sql.utils.SqlUtil;
import cn.acol.security.rbac.domain.Admin;
import cn.acol.security.rbac.domain.Resource;
import cn.acol.security.rbac.domain.Role;
import cn.acol.security.rbac.dto.AdminCondition;
import cn.acol.security.rbac.dto.AdminInfo;
import cn.acol.security.rbac.dto.ResourceInfo;
import cn.acol.security.rbac.dto.RoleInfo;
import cn.acol.security.rbac.repository.AdminRepository;
import cn.acol.security.rbac.repository.ResourceRepository;
import cn.acol.security.rbac.repository.RoleRepository;
import cn.acol.security.rbac.repository.spec.AdminSpec;
import cn.acol.security.rbac.repository.support.QueryResultConverter;
import cn.acol.security.rbac.service.AdminService;
import cn.acol.security.rbac.util.ResourceUtils;

/**
 * 
 * @author DaveZhang
 *
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private ResourceRepository resourceRepository;
	
	
	
	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);


	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#create(com.imooc.security.rbac.dto.AdminInfo)
	 */
	@Override
	public AdminInfo create(AdminInfo adminInfo) {
		
		Admin admin = new Admin();
		BeanUtils.copyProperties(adminInfo, admin);
		admin.setPassword(adminInfo.getUserPassword());
		adminInfo.setId(admin.getId());
		
		Set<Role> roles = new HashSet<>();
		for(RoleInfo role :adminInfo.getRoles()) {
			roles.add(roleRepository.findById(role.getId()).get());
		}
		admin.setRoles(roles);
		Admin save = adminRepository.save(admin);
		if(save == null) {
			return null;
		}
		return adminInfo;
	}

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#update(com.imooc.security.rbac.dto.AdminInfo)
	 */
	@Override
	public AdminInfo update(AdminInfo adminInfo) {
		
		Admin admin = adminRepository.findById(adminInfo.getId()).get();
		
		BeanUtils.copyProperties(adminInfo, admin);
		//createRoleAdmin(adminInfo, admin);
		
		Set<Role> roles = new HashSet<>();
		for(RoleInfo role :adminInfo.getRoles()) {
			roles.add(roleRepository.findById(role.getId()).get());
		}
		admin.setRoles(roles);
		
		return adminInfo;
	}
	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		adminRepository.deleteById(id);
	}
	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#getInfo(java.lang.Long)
	 */
	@Override
	public AdminInfo getInfo(Long id) {
		Admin admin = adminRepository.findById(id).get();
		
		
		AdminInfo info = new AdminInfo();
		List<RoleInfo> roleInfos = new ArrayList<>();
		BeanUtils.copyProperties(admin, info);
		for(Role role :admin.getRoles()) {
			RoleInfo rInfo = new RoleInfo();
			BeanUtils.copyProperties(role, rInfo);
			roleInfos.add(rInfo);
		}
		info.setRoles(roleInfos);
		return info;
	}
	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#query(com.imooc.security.rbac.dto.AdminInfo, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
		Page<Admin> admins = adminRepository.findAll(new AdminSpec(condition), pageable);
		return QueryResultConverter.convert(admins, AdminInfo.class, pageable);
	}
	@Override
	public List<AdminInfo> getAdminInfos() {
		// TODO Auto-generated method stub
		List<Admin> admins = adminRepository.findAll();
		List<AdminInfo> adminInfos = new ArrayList<>();
		for(Admin admin : admins) {
			AdminInfo adminInfo = new AdminInfo();
			BeanUtils.copyProperties(admin, adminInfo);
			adminInfos.add(adminInfo);
		}
		return adminInfos;
	}

	@Override
	public AdminInfo getInfo(String name) {
		// TODO Auto-generated method stub
		
		Admin admin = adminRepository.findByUsername(name);
		if(admin == null) {
			return null;
		}
		AdminInfo adminInfo = new AdminInfo();
		BeanUtils.copyProperties(admin, adminInfo);
		return adminInfo;
	}
	@Override
	public List<RoleInfo> getInfos(Long id) {
		// TODO Auto-generated method stub
		Admin admin = adminRepository.findById(id).get();
		Set<Role> roles = admin.getRoles();
		List<RoleInfo> roleInfos = new ArrayList<>();
		for(Role role : roles) {
			RoleInfo roleInfo = new RoleInfo();
			BeanUtils.copyProperties(role, roleInfo);
			roleInfos.add(roleInfo);
		}
		return roleInfos;
	}
	private Resource getRootResouce() {
		return resourceRepository.findByName("root");
	}
	@Override
	public ResourceInfo getTree(Long adminId) {
		try {
			Admin admin = SqlUtil.findById(adminRepository, adminId);
			Set<Role> roles = admin.getRoles();
			Set<Long> resources = new HashSet<>();
			for(Role role : roles) {
				resources.addAll(role.getResources());
			}
			Resource rootResouce = getRootResouce();
			return ResourceUtils.toTree(rootResouce,resources);
		} catch (SqlNoExistException  | AnaysisException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			return null;
		}
	}
	
}
