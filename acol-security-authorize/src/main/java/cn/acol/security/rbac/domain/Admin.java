package cn.acol.security.rbac.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.acol.scada.core.domain.BaseDomain;


/**
 * 管理员(用户)
 * 
 * @author DaveZhang
 *
 */
@Entity
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"username"})},
indexes= {@Index(columnList = "username")})
public class Admin extends BaseDomain implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2710101553992478496L;
	
	
	private AdminType adminType;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	
	private String name;

	private String phone;
	
	private String mobile;
	
	private String email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Long> getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(Set<Long> resourceIds) {
		this.resourceIds = resourceIds;
	}
	
	
	public AdminType getAdminType() {
		return adminType;
	}
	public void setAdminType(AdminType adminType) {
		this.adminType = adminType;
	}


	/**
	 * 用户有权访问的所有url，不持久化到数据库
	 */
	@Transient
	private Set<String> urls = new HashSet<>();
	/**
	 * 用户有权的所有资源id，不持久化到数据库
	 */
	@Transient
	private Set<Long> resourceIds = new HashSet<>();
	
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
	  @JoinTable(name = "RoleAdmin", joinColumns = @JoinColumn(name = "admin_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getAuthorities(
	 * )
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	
	
	
	
	

	/**
	 * @param urls
	 *            the urls to set
	 */
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
