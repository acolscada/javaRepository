package cn.acol.oldscada.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name="iodata_201509")
@Data
@Entity
public class IoData {
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="CommNo")
	private String commNo;
	
	@Column(name="bkzl")
	private float bkzl;
	
	@Column(name="gkzl")
	private float gkzl;
	
	@Column(name="bkll")
	private float bkll;
	@Column(name="gkll")
	private float gkll;
	
	@Column(name="wd")
	private float wd;
	
	@Column(name="yl")
	private float yl;
	
	
	//private float zljl;
	//private float syje;
	//private float syql;
	//private float tzje;
	
	//private float dqdj;
	@Column(name="xhqd")
	private float xhqd;
	@Column(name="dcdy")
	private float dcdy;
	
	//private float dcdl;
	//private int fmzt;
	//private int sdzt;
	//private int mjzt;
	@Column(name="RecordTime")
	private Date recordTime;
	@Column(name="LogTime")
	private Date logTime;
	//private String hex;
}
