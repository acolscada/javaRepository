package cn.acol.security.rbac.service;

import java.util.List;

import cn.acol.security.rbac.dto.ResourceInfo;

/**
 * 资源服务
 * @author DaveZhang
 *
 */


public interface ResourceService {
	
	/**
	 * 根据资源ID获取资源信息
	 * @param id 资源ID
	 * @return ResourceInfo 资源信息
	 * @date  2015年7月10日下午7:01:48
	 * @since 1.0.0
	*/
	ResourceInfo getInfo(Long id);

	/**
	 * 新增资源
	 *
	 * @param info 页面传入的资源信息
	 * @return ResourceInfo 资源信息
	 * @date  2015年7月10日下午7:01:51
	 * @since 1.0.0
	*/
	ResourceInfo create(ResourceInfo info);
	/**
	 * 更新资源
	 *
	 * @param info 页面传入的资源信息
	 * @return ResourceInfo 资源信息
	 * @date  2015年7月10日下午7:01:54
	 * @since 1.0.0
	*/
	ResourceInfo update(ResourceInfo info);
	/**
	 * 根据指定ID删除资源信息
	 *
	 * @param id 资源ID
	 * @date  2015年7月10日下午7:01:57
	 * @since 1.0.0
	*/
	void delete(Long id);
	/**
	 * 上移/下移资源
	 * @param id
	 * @param up
	 */
	Long move(Long id, boolean up);
	/**
	 * 获取简单的资源树数据  
	 * [
		{id:1, pId:0, name: "父节点1"。。。。},
		{id:11, pId:1, name: "子节点1"。。。。},
		{id:12, pId:1, name: "子节点2"。。。。}
		];
	 * @return
	 */
	List<ResourceInfo> getSimpleResources();
	
	Long editUrls(ResourceInfo resourceInfo);
}
