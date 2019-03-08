package cn.acol.scada.dao;

import org.springframework.stereotype.Repository;

import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.scada.domain.LastRecord;

@Repository
public interface LastRecordRepository extends BaseRepository<LastRecord>{
}
