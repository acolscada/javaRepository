package cn.acol.security.rbac.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

/**
 * 
 * @author DaveZhang
 *
 */
public interface RbacService {
	
	boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
