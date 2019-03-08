package cn.acol.scada.records.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.scada.records.domain.UpRecord;

@Repository
public interface RecordRepository extends BaseRepository<UpRecord>{
	List<UpRecord> findByScaNum(String scaNum);
}
