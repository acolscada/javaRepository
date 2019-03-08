package cn.acol.security.rbac.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.security.rbac.dto.ResourceInfo;
import cn.acol.security.rbac.service.ResourceService;


/**
 * 
 * @author DaveZhang
 *
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
	@Autowired
	private ResourceService resourceService;
	
	@GetMapping
	@RequestMapping("/simpleResources")
	public List<ResourceInfo> getResources(){
		
		return resourceService.getSimpleResources();
	}
	
	/**
	 * 获取资源信息
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResourceInfo getInfo(@PathVariable Long id){
		return resourceService.getInfo(id);
	}
	/**
	 * 创建资源
	 * @param info
	 * @return
	 */
	@PostMapping
	public ResourceInfo create(@RequestBody ResourceInfo info){
		if(info.getParentId() == null) {
			info.setParentId(0L);
		}
		return resourceService.create(info);
	}
	/**
	 * 修改资源
	 * @param info
	 * @return
	 */
	@PutMapping("/{id}")
	public ResourceInfo update(@RequestBody ResourceInfo info){
		return resourceService.update(info);
	}
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public SimpleResponse delete(@PathVariable Long id){
		try {
			resourceService.delete(id);
		}catch (Exception e) {
			// TODO: handle exception
			return new SimpleResponse(-1,"资源删除失败");
		}
		return SimpleResponse.getNormalResponse();
	}
	
	
}
