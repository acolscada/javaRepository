package cn.acol.oauth2.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * 
 * @author DaveZhang
 *
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return new User("superAdmin", passwordEncoder.encode("123456"),true,
				true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
	}
}
