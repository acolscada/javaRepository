
package cn.acol.security.rbac.repository;

import org.springframework.stereotype.Repository;

import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.security.rbac.domain.Resource;


/**
 * 
 * @author DaveZhang
 *
 */
@Repository
public interface ResourceRepository extends BaseRepository<Resource> {

	Resource findByName(String name);

}
