package cn.acol.security.rbac.util;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class AdminUtils {
	public static String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
		if(principal instanceof UserDetails) {
			return ((UserDetails)principal).getUsername();
		}
		return null;
	}
}
