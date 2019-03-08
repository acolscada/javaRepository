package cn.acol.scada.records.utils;



import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.acol.scada.records.core.Request;
import cn.acol.scada.records.core.Response;
import cn.acol.scada.records.core.Servlet;
import cn.acol.scada.records.core.Session;


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
	
	public static Response getResponse(Session session) {
		 Response bean = applicationContext.getBean(Response.class);
		 bean.setSession(session);
		 return bean;
	}
	public static Request getRequest(Session session) {
		 Request bean = applicationContext.getBean(Request.class);
		 bean.setSession(session);
		 return bean;
	}
	
}
