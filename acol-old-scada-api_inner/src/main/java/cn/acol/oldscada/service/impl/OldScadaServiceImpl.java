package cn.acol.oldscada.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.acol.oldscada.domain.IoData;
import cn.acol.oldscada.respository.IoDataRespository;
import cn.acol.oldscada.service.OldScadaService;
import cn.acol.oldscada.service.utils.Utils;
import cn.acol.scada.core.dto.PageBean;
import cn.acol.scada.core.dto.UpRecordDto;
@Service
public class OldScadaServiceImpl implements OldScadaService{

	@Autowired
	private IoDataRespository ioDataRespository;
	@Override
	public List<UpRecordDto> getUpRecordsByScadaNum(String scaNum) {
		// TODO Auto-generated method stub
		List<IoData> ioDatas = ioDataRespository.findByCommNo(scaNum);
		return Utils.changeIoDatasToUpRecordDtos(ioDatas);
	}
	@Override
	public PageBean<UpRecordDto> getPageUpRecords(String scaNum, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		Specification<IoData> specification = new Specification<IoData>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<IoData> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				Predicate scaNumPredicate = criteriaBuilder.equal(root.get("commNo"), scaNum);
				return criteriaBuilder.and(scaNumPredicate);
			}
		};
		Pageable pageable = PageRequest.of(pageNum, pageSize, new Sort(Sort.Direction.DESC, "recordTime"));
		
		Page<IoData> ioDatas = ioDataRespository.findAll(specification, pageable);
		PageBean<UpRecordDto> upRecordPage= new PageBean<>();
		Utils.ChangeIoDataPageToUpRecordPage(ioDatas, upRecordPage);
		return upRecordPage;
		//ioDatas.get
		//return Utils.changeIoDatasToUpRecordDtos(ioDatas);
	}

}
