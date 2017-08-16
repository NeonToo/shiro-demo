package com.sap.csc.app.model.jpa;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*
 * @author I326950
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ModifiableEntity implements Serializable {

	private static final long serialVersionUID = -7596052873696314825L;

	@Column(name = "create_on")
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
//	private ZonedDateTime createOn;
	private Calendar createOn;
//	private LocalDateTime createOn;

	@Column(name = "update_on")
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
//	private ZonedDateTime updateOn;
	private Calendar updateOn;
//	private LocalDateTime updateOn;

	public Calendar getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Calendar createOn) {
		this.createOn = createOn;
	}

	public Calendar getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Calendar updateOn) {
		this.updateOn = updateOn;
	}

}
