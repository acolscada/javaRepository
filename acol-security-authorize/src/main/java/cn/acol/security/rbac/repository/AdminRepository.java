package cn.acol.security.rbac.repository;

import org.springframework.stereotype.Repository;
import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.security.rbac.domain.Admin;


/**
 * 
 * @author DaveZhang
 *
 */
@Repository
public interface AdminRepository extends BaseRepository<Admin> {
	Admin findByUsername(String username);
}
