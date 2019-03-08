package cn.acol.scada.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.acol.scada.core.domain.BaseDomain;
import cn.acol.scada.core.domain.ScadaStatus;
import cn.acol.scada.core.dto.DeviceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;



/**
 * @author DaveZhang
 *
 */
@Data
@EqualsAndHashCode(callSuper=false,of= {"scaNum"})
@Accessors(chain =true)
@Entity
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"scaNum"})},
	 indexes= {@Index(columnList = "scaNum")})
public class Scada extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 5150310760797801443L;
	@Column(name = "scaNum")
	private String scaNum;
	private String colType;//422或485
	private String version;//软件版本
	@Column(name="scadescribe")
	private String describe;//描述
	private String ICCID;//手机卡号
	@Enumerated(EnumType.STRING)
	private DeviceType deviceType; //设备类型
	@Enumerated(EnumType.STRING)
	private ScadaStatus status;
	private Date lastConnetionTime;//设备最近一次与服务器通讯的时间
	@ManyToOne
	@JoinColumn(name="user")
	private User user;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="lastRecord")
	private LastRecord lastRecord;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="scadaParams")
	private ScadaParams scadaParams;
}
