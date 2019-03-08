package cn.acol.security.rbac.service.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.acol.scada.core.exception.AnaysisException;
import cn.acol.scada.core.exception.SqlGarbageCollectionException;
import cn.acol.scada.core.exception.SqlNoExistException;
import cn.acol.scada.core.sql.utils.SqlUtil;
import cn.acol.security.rbac.domain.Resource;
import cn.acol.security.rbac.domain.Role;
import cn.acol.security.rbac.dto.ResourceInfo;
import cn.acol.security.rbac.dto.RoleInfo;
import cn.acol.security.rbac.repository.ResourceRepository;
import cn.acol.security.rbac.repository.RoleRepository;
import cn.acol.security.rbac.repository.support.QueryResultConverter;
import cn.acol.security.rbac.service.RoleService;
import cn.acol.security.rbac.util.ResourceUtils;



/**
 * 
 * @author DaveZhang
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	
	private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ResourceRepository resourceRepository;
	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.RoleService#create(com.imooc.security.rbac.dto.RoleInfo)
	 */
	@Override
	public RoleInfo create(RoleInfo info) {
		Role role = new Role();
		BeanUtils.copyProperties(info, role);
		Set<Resource> resources = new HashSet<>();
		for(ResourceInfo resourceInfo :info.getResourceInfos()) {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceInfo, resource);
			resources.add(resource);
		}
		//role.setResources(resources);
		info.setId(roleRepository.save(role).getId());
		return info;
	}

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.RoleService#update(com.imooc.security.rbac.dto.RoleInfo)
	 */
	@Override
	public RoleInfo update(RoleInfo info) {
		Role role = roleRepository.findById(info.getId()).get();
		BeanUtils.copyProperties(info, role);
		Set<Resource> resources = new HashSet<>();
		for(ResourceInfo resourceInfo :info.getResourceInfos()) {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceInfo, resource);
			resources.add(resource);
		}
	//	role.setResources(resources);
		roleRepository.save(role);
		
		return info;
	}

	/**
	 * (non-Javadoc)
	 * @see com.idea.ams.service.RoleService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		Role role = roleRepository.findById(id).get();
		if(CollectionUtils.isNotEmpty(role.getAdmins())){
			throw new RuntimeException("不能删除有下挂用户的角色");
		}
		roleRepository.deleteById(id);
	}
//
//	@Override
//	public String[] getRoleMenus(Long id) {
//		return StringUtils.split(roleRepository.findOne(id).getMenus(), ",");
//	}
//
//	/**
//	 * (non-Javadoc)
//	 * @see com.idea.ams.service.RoleService#setRoleMenu(java.lang.Long, java.lang.String)
//	 */
//	@Override
//	public void setRoleMenu(Long roleId, String menuIds) {
//		Role role = roleRepository.findOne(roleId);
//		role.setMenus(menuIds);
//	}

	/**
	 * (non-Javadoc)
	 * @see com.idea.ams.service.RoleService#getRoleInfo(java.lang.Long)
	 */
	@Override
	public RoleInfo getInfo(Long id) {
		Role role = roleRepository.findById(id).get();
		RoleInfo info = new RoleInfo();
		BeanUtils.copyProperties(role, info);
		List<ResourceInfo> resourceInfos = new ArrayList<ResourceInfo>();
		/*for(Resource resource :role.getResources()) {
			ResourceInfo resourceInfo = new ResourceInfo();
			BeanUtils.copyProperties(resource, resourceInfo);
			resourceInfos.add(resourceInfo);
		}*/
		info.setResourceInfos(resourceInfos);
		return info;
	}
	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.RoleService#findAll()
	 */
	@Override
	public List<RoleInfo> findAll() {
		return QueryResultConverter.convert(roleRepository.findAll(), RoleInfo.class);
	}
	
	@Override
	public Set<Long> getRoleResources(Long id){
		try {
			Role role = SqlUtil.findById(roleRepository, id);
			return role.getResources();
		} catch (SqlNoExistException  | AnaysisException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return new HashSet<Long>();
	}
	
	private Resource getRootResouce() {
		return resourceRepository.findByName("root");
	}
	
	
	
	@Override
	public ResourceInfo getTree(Long roleId) {
		try {
			Role role = SqlUtil.findById(roleRepository, roleId);
			//查找role 拥有的资源ID
			Set<Long> resources = role.getResources();
			//List<Resource> allResources = resourceRepository.findAll();
			Resource rootResouce = getRootResouce();
			return ResourceUtils.toTree(rootResouce,resources);
		} catch (SqlNoExistException  | AnaysisException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.idea.ams.service.RoleService#setRoleMenu(java.lang.Long, java.lang.String)
	 */
	@Override
	public void setRoleResources(Long roleId, String resourceIds) {
		try {
			Role role = SqlUtil.findById(roleRepository, roleId);
			role.setResourceIds(resourceIds);
			//检测格式是否按照规定否则抛出异常
			//保存
			roleRepository.save(role);
			
		} catch (SqlNoExistException | SqlGarbageCollectionException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		/*roleResourceRepository.deleteAll(role.getResources());
		String[] resourceIdArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(resourceIds, ",");
		for (String resourceId : resourceIdArray) {
			RoleResource roleResource = new RoleResource();
			roleResource.setRole(role);
			roleResource.setResource(resourceRepository.getOne(new Long(resourceId)));
			roleResourceRepository.save(roleResource);
		}*/
	}

	

}
