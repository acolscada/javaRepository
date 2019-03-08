/**
 * 
 */
package cn.acol.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author DaveZhang
 *
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2312122741174634886L;

	/**
	 * 
	 */

	public ValidateCodeException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
