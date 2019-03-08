package cn.acol.scada.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.scada.domain.Scada;

@Repository
public interface ScadaRepository extends BaseRepository<Scada>{
	Scada findByScaNum(String scaNum);
	
	@Query(value="select s from Scada s where s.scaNum like CONCAT('%',?1,'%')")
	List<Scada> findByNameLike(String scaNum);
}
