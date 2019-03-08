package cn.acol.oldscada.repository.secondary;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.acol.oldscada.domain.secondary.IoData;


@Repository
public interface IoDataRespository extends JpaRepository<IoData, Integer>,JpaSpecificationExecutor<IoData>{
	List<IoData> findByCommNo(String commNo);
}
