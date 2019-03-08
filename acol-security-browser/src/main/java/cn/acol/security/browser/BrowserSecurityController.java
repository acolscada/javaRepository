/**
 * 
 */
package cn.acol.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.security.core.properties.SecurityProperties;
import cn.acol.security.core.support.SimpleResponse;



/**
 * @author DaveZhang
 *
 *
 */
@RestController
public class BrowserSecurityController {
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties securityProperties;
	/**
	 * 当需要身份认证时，跳转到这里
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	
	@RequestMapping("/authentication/require")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		System.out.println("---------------"+securityProperties.getBrowser().getLoginPage()+"-------------");
		if(savedRequest !=null) {
			String target = savedRequest.getRedirectUrl();
			System.out.println("引发跳转的url是"+target);
			
			if(StringUtils.endsWithIgnoreCase(target, ".html")) {
				redirectStrategy.sendRedirect(request, response,securityProperties.getBrowser().getLoginPage());
			}
		}
		return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
	}
}
