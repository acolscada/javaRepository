package cn.acol.security.rbac.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;




/**
 * 
 * @author DaveZhang
 *
 */
public class AdminInfo {
	
	private Long id;
	/**
	 * 角色id 
	 */

    /**
     * 用户对应的角色列表
     */
    private List<RoleInfo> roles;
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	private String username;
	/**
	 * 密码
	 */
	private String userPassword;//添加用户时可进行密码的设置
	
	/**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobile;
    
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	



	public List<RoleInfo> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleInfo> roles) {
		this.roles = roles;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}