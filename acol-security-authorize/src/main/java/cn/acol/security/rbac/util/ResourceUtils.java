package cn.acol.security.rbac.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import cn.acol.security.rbac.domain.Resource;
import cn.acol.security.rbac.dto.ResourceInfo;

public class ResourceUtils {
	public static ResourceInfo toTree(Resource resource,Set<Long> resources) {
		ResourceInfo resourceInfo = new ResourceInfo();
		BeanUtils.copyProperties(resource, resourceInfo);//实例化
		List<ResourceInfo> children = new ArrayList<ResourceInfo>();
		for(Resource midResource : resource.getChilds()) {
			if(resources.contains(midResource.getId())) {
				children.add(toTree(midResource,resources));
			}
		}
		resourceInfo.setChildren(children);
		return resourceInfo;
	}
}
