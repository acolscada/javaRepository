
package cn.acol.security.rbac.repository.support;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @author DaveZhang
 *
 * @param <T>  源
 * @param <I>  目标
 */
public interface Domain2InfoConverter<T, I> extends Converter<T, I> {
	
}
