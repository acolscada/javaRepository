package cn.acol.scada.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.acol.scada.core.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper=true,exclude= {"scadas","company"})
@Accessors(chain =true)
@Entity
public class User extends BaseDomain implements Serializable{
	private static final long serialVersionUID = -2790786300179519308L;
	
	private String adminName;//绑定管理员帐号用
	
	private String customerName;
	private String customerAddress;
	@ManyToOne
	@JoinColumn(name="company")
	private Company company;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private Set<Scada> scadas;
	private int oldSysId;//用于老系统用户进行scada 获取此用户非同过本平台添加
	
}
