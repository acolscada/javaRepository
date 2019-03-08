package cn.acol.oldscada.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.acol.oldscada.domain.primary.InfoCustomer;
@Repository
public interface InfoCustomerRespository extends JpaRepository<InfoCustomer, Integer>, JpaSpecificationExecutor<InfoCustomer>{

}
