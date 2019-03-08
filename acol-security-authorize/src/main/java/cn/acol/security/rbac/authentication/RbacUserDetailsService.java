package cn.acol.security.rbac.authentication;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import cn.acol.security.rbac.domain.Admin;
import cn.acol.security.rbac.repository.AdminRepository;

/**
 * 
 * @author DaveZhang
 *
 */
@Component
@Transactional
public class RbacUserDetailsService  implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AdminRepository adminRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		logger.info("表单登录用户名:" + username);
		Admin admin = adminRepository.findByUsername(username);
		//admin.getUrls();
		return admin;
	}
}
