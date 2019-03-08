package cn.acol.security.rbac.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;


import cn.acol.scada.core.domain.BaseDomain;
import cn.acol.security.rbac.dto.ResourceType;


/**
 * 需要控制权限的资源，以业务人员能看懂的name呈现.实际关联到一个或多个url上。
 * 
 * 资源：
 * 		规约 1.仅有超级管理员角色 可进行资源管理(即其拥有所有权限)
 * 			 2. 添加资源：十分简单（略）
 * 			3.存在一个根目录资源：其名称为root  所有认为添加的资源都在根目录的子孙节点
 * 			4.角色拥有资源的规定：  
 * 				每个角色拥有的资源以  id1;id2;id3;id4;id5;id6 方式存储在数据库中
 * 				
 * 				约定：当勾选某一节点时依次勾选,其父节点直到根节点为止
 *				如无法从根节点追溯至子节点 则改子节点将丢失
 * 				为了效率算法希望： 当设置角色资源时距离根节点最近的节点在前
 * 				容错性：即使没有按照希望排序 依然会给出期望的结果但是效率上是不一样的
 * 			5.用户所拥有的资源：
 * 				所有用户拥有的角色所拥有的资源并集则为用户的资源
 * 树形结构
 * @author DaveZhang
 */
@Entity
public class Resource extends BaseDomain{
	/**
	 * 资源名称，如xx菜单，xx按钮
	 */
	private String name;
	/**
	 * 资源链接 url
	 */
	private String link;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 资源类型
	 */
	@Enumerated(EnumType.STRING)
	private ResourceType type;
	
	/**
	 * 实际需要控制权限的url
	 */
	@ElementCollection
	private Set<String> urls;
	
	/**
	 * 父资源
	 */
	@ManyToOne
	@JoinColumn(name="parent")
	private Resource parent;
	
	
	/**
	 * 子资源
	 */
	@OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
	@OrderBy("sort ASC")
	private List<Resource> childs = new ArrayList<>();
	
	/**
	 * 序号
	 */
	private int sort;
	
	/**
	 * 权限字符串
	 */
	private String permission;

	
	public void addChild(Resource child) {
		childs.add(child);
		child.setParent(this);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public Set<String> getUrls() {
		return urls;
	}
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public List<Resource> getChilds() {
		return childs;
	}

	public void setChilds(List<Resource> childs) {
		this.childs = childs;
	}
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the type
	 */
	public ResourceType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
