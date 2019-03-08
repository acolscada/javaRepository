package cn.acol.security.rbac.domain;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import cn.acol.scada.core.domain.BaseDomain;
import cn.acol.scada.core.exception.AnaysisException;


/**
 * 
 * @author DaveZhang
 *
 */

@Entity
public class Role extends BaseDomain{
	/**
	 * 角色名称
	 */
	@Column(length = 20, nullable = false)
	private String name;
	/**
     * 备注信息
     */
    private String remarks;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	@ManyToMany( mappedBy="roles")
	private Set<Admin> admins;
	
	
	//角色拥有的资源  id1:id2:id3 
	//当拥有id1 时则直接拥有 其及其一下的所有子资源
	//当拥有id2时则直接拥有
	private String resourceIds;
	
	/**
	 * 获取角色拥有资源的id
	 * @return
	 * @throws AnaysisException
	 */
	public Set<Long> getResources() throws AnaysisException{
		Set<Long> resources = new HashSet<>();
		if(resourceIds!=null) {
			String[] ids = resourceIds.split(";");
			for(int i=0;i<ids.length;i++) {
				try {
					resources.add(Long.parseLong(ids[i]));
				}catch(NumberFormatException e) {
					throw new AnaysisException("角色拥有的资源必须为整数id");
				}
			}
		}
		return resources;
	}
	
	
	public Set<Admin> getAdmins() {
		return admins;
	}
	public void setAdmins(Set<Admin> admins) {
		this.admins = admins;
	}
	public String getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	
	
}
