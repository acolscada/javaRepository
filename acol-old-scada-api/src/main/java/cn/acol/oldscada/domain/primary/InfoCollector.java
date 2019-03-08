package cn.acol.oldscada.domain.primary;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
@Table(name="InfoCollector")
@Data
@Entity
public class InfoCollector {
	@Id
	@Column(name="ID")
	private int ID;
	@Column(name="Name")
	private String Name;
	
	//private String CreateTime;
	@Column(name="Code")
	private String Code;
	
	//private String CreatePosition;
	@Column(name="OperID")
	private int OperID;
	
	@Column(name="LogTime")
	private Date LogTime;
	
	@Column(name="ParentID")
	private int parentID;
	@Column(name="groups")
	private String groups;
	
	//private int CommID;
	@Column(name="MeterNo")
	private String MeterNo;
	
	
	//private String Remark;
	
	//private int ctype;
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
	//private float dcdl;
	@Column(name="dcdy")
	private float dcdy;
	//private int fmzt;
	//private int sdzt;
	//private int mjzt;
	
	
	@Column(name="updatetime")
	private Date updatetime;
	
	
	//private int Ireader;
	//private int Iupdate;
	
	
	
	
}
