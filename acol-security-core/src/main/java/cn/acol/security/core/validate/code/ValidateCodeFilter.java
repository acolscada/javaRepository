package cn.acol.security.core.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.acol.security.core.properties.SecurityProperties;
import cn.acol.security.core.validate.code.image.ImageCode;


public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

	//@Autowired
	private AuthenticationFailureHandler myAuthenticationFailureHandler;
	
	
	
	private Set<String> urls = new HashSet<>();
	
	private SecurityProperties securityProperties;
	
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
		String[] configUrls = StringUtils.splitByWholeSeparator(securityProperties.getValidateCodeProperties().getImageCodeProperties().getUrl(), ",");
		for(String configUrl :configUrls) {
			urls.add(configUrl);
		}
		urls.add("/acolProduct/authentication/login");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean action = false;
		//System.out.println("request:"+request.getRequestURI());
		for(String url :urls) {
			if(pathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		}
		if(action) {
			try {
				validate(request);
			}catch(ValidateCodeException e) {
				myAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	
	public AuthenticationFailureHandler getMyAuthenticationFailureHandler() {
		return myAuthenticationFailureHandler;
	}


	public void setMyAuthenticationFailureHandler(AuthenticationFailureHandler myAuthenticationFailureHandler) {
		this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
	}


	private void validate(HttpServletRequest request) throws ServletRequestBindingException {
		// TODO Auto-generated method stub
		ImageCode codeInSession = (ImageCode) request.getSession().getAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
		String codeInRequest = ServletRequestUtils.getStringParameter(request, "imageCode");
		if(StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空");
		}
		if(codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}
		if(codeInSession.isExpired()) {
			request.getSession().removeAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
			throw new ValidateCodeException("验证码已过期");
		}	
		if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		request.getSession().removeAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
	}


	public Set<String> getUrls() {
		return urls;
	}


	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}


	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}


	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	
	
}
