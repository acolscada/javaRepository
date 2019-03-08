package cn.acol.security.rbac.repository.spec;

import cn.acol.security.rbac.domain.Admin;
import cn.acol.security.rbac.dto.AdminCondition;
import cn.acol.security.rbac.repository.support.AuthorizeSpecification;
import cn.acol.security.rbac.repository.support.QueryWraper;

/**
 * 
 * @author DaveZhang
 *
 */
public class AdminSpec extends AuthorizeSpecification<Admin, AdminCondition> {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9113143939691648762L;

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
