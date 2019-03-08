package cn.acol.scada.core.domain;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDomain {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@CreatedDate
	private DateTime createTime;
	@LastModifiedDate
	private DateTime updateTime;
	@CreatedBy
	private String createBy;
	@LastModifiedBy
	private String lastModifiedBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	public DateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
}
