package cn.acol.scada.jituan.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class JtMessageLog {
	@Id
	@GeneratedValue
	private Long lId;
	private String shebeih;
	private String message;
}
