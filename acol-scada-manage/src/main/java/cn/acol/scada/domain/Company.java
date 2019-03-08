package cn.acol.scada.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import cn.acol.scada.core.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false,exclude= {"users"})
public class Company extends BaseDomain implements Serializable{
	private static final long serialVersionUID = 8678399055392357867L;
	
	private String adminName;//绑定管理员帐号用
	
	private String companyName;
	private String phone;
	private String address;
	
	@OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
	private Set<User> users;

	@Override
	public String toString() {
		return "Company [companyName=" + companyName + ", phone=" + phone + ", address=" + address + ", users=" + users
				+ "]";
	}

	
	
}
