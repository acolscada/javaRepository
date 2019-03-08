package cn.acol.scada.dao;

import org.springframework.stereotype.Repository;

import cn.acol.scada.core.repository.BaseRepository;
import cn.acol.scada.domain.Company;

@Repository
public interface CompanyRepository extends BaseRepository<Company>{
	Company findByCompanyName(String companyName);
}
