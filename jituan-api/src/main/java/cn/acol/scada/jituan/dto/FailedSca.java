package cn.acol.scada.jituan.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class FailedSca {
	@Id
	@GeneratedValue
	private Long fId;
	private Long id;
	private String shebeih;
	private String cid;
	private String hm;
	private String dz;
	private String gongyings;
	private String paigongsj;
	private Integer yuanyin;
	private String paigongbz;
}
