package cn.acol.security.rbac.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.acol.security.rbac.domain.Resource;
import cn.acol.security.rbac.dto.ResourceInfo;
import cn.acol.security.rbac.repository.ResourceRepository;
import cn.acol.security.rbac.service.ResourceService;


/**
 * 
 * @author DaveZhang
 *
 */
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;
	

	

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.ResourceService#getInfo(java.lang.Long)
	 */
	@Override
	public ResourceInfo getInfo(Long id) {
		Resource resource = resourceRepository.findById(id).get();
		ResourceInfo resourceInfo = new ResourceInfo();
		BeanUtils.copyProperties(resource, resourceInfo);
		return resourceInfo;
	}

	@Override
	public ResourceInfo create(ResourceInfo info) {
		Resource parent = resourceRepository.findById(info.getParentId()).get();
		//没有找到父节点
		if(parent == null){
			//认为是根节点下的节点
			parent = resourceRepository.findByName("根节点");
		}
		//获取资源
		Resource resource = new Resource();
		BeanUtils.copyProperties(info, resource);
		parent.addChild(resource);
		info.setId(resourceRepository.save(resource).getId());
		return info;
	}

	@Override
	public ResourceInfo update(ResourceInfo info) {
		Resource resource = resourceRepository.findById(info.getId()).get();
		BeanUtils.copyProperties(info, resource);
		return info;
	}

	@Override
	public void delete(Long id) {
		resourceRepository.deleteById(id);
	}
	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.ResourceService#move(java.lang.Long, boolean)
	 */
	@Override
	public Long move(Long id, boolean up) {
		Resource resource = resourceRepository.findById(id).get();
		int index = resource.getSort();
		List<Resource> childs = resource.getParent().getChilds();
		for (int i = 0; i < childs.size(); i++) {
			Resource current = childs.get(i);
			if(current.getId().equals(id)) {
				if(up){
					if(i != 0) {
						Resource pre = childs.get(i - 1);
						resource.setSort(pre.getSort());
						pre.setSort(index);
						resourceRepository.save(pre);
					}
				}else{
					if(i != childs.size()-1) {
						Resource next = childs.get(i + 1);
						resource.setSort(next.getSort());
						next.setSort(index);
						resourceRepository.save(next);
					}
				}
			}
		}
		resourceRepository.save(resource);
		return resource.getParent().getId();
	}

	@Override
	public List<ResourceInfo> getSimpleResources(){
		// TODO Auto-generated method stub
		List<Resource> resources = resourceRepository.findAll();
		List<ResourceInfo> resourceInfos = new ArrayList<>();
		for(Resource resource : resources) {
			ResourceInfo resourceInfo = new ResourceInfo();
			BeanUtils.copyProperties(resource, resourceInfo);
			resourceInfos.add(resourceInfo);
		}
		return resourceInfos;
	}

	@Override
	public Long editUrls(ResourceInfo resourceInfo) {
		// TODO Auto-generated method stub
		Resource resource = resourceRepository.findById(resourceInfo.getId()).get();
		resourceRepository.save(resource);
		return 0L;
	}

}