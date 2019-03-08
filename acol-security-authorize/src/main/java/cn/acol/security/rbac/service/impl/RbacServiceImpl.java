package cn.acol.security.rbac.service.impl;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import cn.acol.security.rbac.domain.Admin;
import cn.acol.security.rbac.service.RbacService;


/**
 * 
 * @author DaveZhang
 *
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();

		//System.out.println("进入判断了rbac.service.RbacServiceImpl");
		boolean hasPermission = false;
		if (principal instanceof Admin) {
			Admin admin = (Admin) principal;
			if (StringUtils.equals(admin.getUsername(), "superAdmin")) {
				hasPermission = true;
			} else {
			//	Set<Role> roles = admin.getRoles();
				// 读取用户所拥有权限的所有URL
				/*Set<String> urls = ((Admin) principal).getUrls();
				for (String url : urls) {
					if (antPathMatcher.match(url, request.getRequestURI())) {
						hasPermission = true;
						break;
					}
				}*/
			}
		}
		return hasPermission;
	}
}
