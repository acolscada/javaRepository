package cn.acol.security.core.validate.code;


import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
	/**
	 * 验证码生成
	 * @param request
	 * @return
	 */
	public ValidateCode generate(ServletWebRequest request);
}
