package cn.acol.security.rbac.dto;

import java.util.List;

/**
 * 
 * 
 * @author DaveZhang
 *
 */
public class RoleInfo {
	
	private Long id;
	/**
	 * 角色名称
     */
	private String name;
	
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 资源信息
     */
    private List<ResourceInfo> resourceInfos;
    

	public List<ResourceInfo> getResourceInfos() {
		return resourceInfos;
	}
	public void setResourceInfos(List<ResourceInfo> resourceInfos) {
		this.resourceInfos = resourceInfos;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	
}
