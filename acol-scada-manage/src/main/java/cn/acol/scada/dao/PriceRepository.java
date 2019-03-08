package cn.acol.scada.dao;

import java.util.List;

import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.scada.domain.PricePlan;

public interface PriceRepository extends BaseRepository<PricePlan>{
	List<PricePlan> findByCompanyAdminName(String companyAdminName);
}
