package cn.acol.oldscada.domain.primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="InfoCustomer")
public class InfoCustomer {
	@Id
	@Column(name="ID")
	private int ID;
	
	@Column(name="Name")
	private String Name;
}
