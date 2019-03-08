package cn.acol.security.core.validate.code;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;



/**
 * 
 * @author DaveZhang
 *
 */
@RestController
public class ValidateCodeController {
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessor;
	/**
	 * 创建验证码，根据验证码类型不同，调用不同接口实现
	 * @param request
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	@GetMapping("/code/{type}")
	public void CreateCode(HttpServletRequest request, HttpServletResponse response,@PathVariable String type) throws Exception {
		validateCodeProcessor.get(type+"CodeProcessor").create(new ServletWebRequest(request,response));
	}
}
