
package cn.acol.security.rbac.repository;

import org.springframework.stereotype.Repository;

import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.security.rbac.domain.Role;


/**
 * 
 * @author DaveZhang
 *
 */
@Repository
public interface RoleRepository extends BaseRepository<Role> {

}
