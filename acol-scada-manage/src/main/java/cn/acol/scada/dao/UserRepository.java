package cn.acol.scada.dao;

import org.springframework.stereotype.Repository;

import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.scada.domain.User;

@Repository
public interface UserRepository extends BaseRepository<User>{

}
