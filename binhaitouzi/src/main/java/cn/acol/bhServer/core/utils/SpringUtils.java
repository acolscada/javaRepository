package cn.acol.bhServer.core.utils;



import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.acol.bhServer.core.Servlet;
import cn.acol.bhServer.core.ServletTangguImpl;


@Component
public class SpringUtils  implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		if(SpringUtils.applicationContext == null)
			SpringUtils.applicationContext = applicationContext;
	}
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public static Servlet getServlet() {
		return applicationContext.getBean(Servlet.class);
	}
	
	
}
