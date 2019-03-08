package cn.acol.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 
 * @author DaveZhang
 *
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider{

	private UserDetailsService userDetailsService;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken)authentication;
		UserDetails user = userDetailsService.loadUserByUsername((String)authenticationToken.getPrincipal());
		if(user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		
		SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
		authenticationResult.setDetails(authentication.getDetails());
		return authenticationResult;
		
		//Authentication authenticationResult;
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
 
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
}
