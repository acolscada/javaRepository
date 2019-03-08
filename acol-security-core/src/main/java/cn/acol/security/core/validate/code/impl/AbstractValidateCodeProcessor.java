package cn.acol.security.core.validate.code.impl;


import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import cn.acol.security.core.validate.code.ValidateCode;
import cn.acol.security.core.validate.code.ValidateCodeException;
import cn.acol.security.core.validate.code.ValidateCodeGenerator;
import cn.acol.security.core.validate.code.ValidateCodeProcessor;


/**
 * 
 * @author DaveZhang
 *
 * @param <C>
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	
	
	
	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	
	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		
		
		String type = getProcessorType(request);
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type+"CodeGenerator");
		
		return (C) validateCodeGenerator.generate(request);
	}
	
	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		System.out.println("验证码为："+validateCode.getCode());
		request.getRequest().getSession().setAttribute(SESSION_KEY_PREFIX+getProcessorType(request).toUpperCase(), validateCode);
		//sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
	}
	
	/**
	 * 发送校验码，由子类实现
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;
	/**
	 * 根据请求的url获取验证码类型
	 * @param request
	 * @return
	 */
	private String getProcessorType(ServletWebRequest request) {
		return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
	}
	
	
	private String getRequestParamCode(ServletWebRequest servletWebRequest) {
		return getProcessorType(servletWebRequest)+"Code";
	}
	
	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase();
	}
	
	@Override
	public void validate(ServletWebRequest servletWebRequest) {
		String sessionKey = getSessionKey(servletWebRequest);
		
		
		@SuppressWarnings("unchecked")
		C codeInSession = (C) servletWebRequest.getRequest().getSession().getAttribute(sessionKey);
		
		String codeInRequest = null;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), getRequestParamCode(servletWebRequest));
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			throw new ValidateCodeException("获取验证码的值失败");
		}
		
		if(StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空");
		}
		if(codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}
		if(codeInSession.isExpired()) {
			servletWebRequest.getRequest().getSession().removeAttribute(sessionKey);
			throw new ValidateCodeException("验证码已过期");
		}	
		if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		
		servletWebRequest.getRequest().getSession().removeAttribute(sessionKey);
		// TODO Auto-generated method stub
		/*ValidateCode processorType = getValidateCodeType(request);
		String sessionKey = getSessionKey(request);

		C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(processorType + "验证码不存在");
		}

		if (codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException(processorType + "验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码不匹配");
		}

		sessionStrategy.removeAttribute(request, sessionKey);
*/
	}


	

		
}

