package cn.acol.oldscada.repository.primary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.acol.oldscada.domain.primary.InfoCollector;

@Repository
public interface InfoCollectorRespository extends JpaRepository<InfoCollector, Integer>, JpaSpecificationExecutor<InfoCollector>{
	public List<InfoCollector> findByParentID(int parentId);
}
