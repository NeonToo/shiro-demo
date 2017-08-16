package com.sap.csc.app.model.jpa;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import com.sap.csc.app.model.enumeration.ResourceType;
import com.sap.csc.app.model.enumeration.RestrictionType;

/**
 * @author I326950
 */
@Entity
public class Permission extends ModifiableEntity {

	private static final long serialVersionUID = -4516140738983567481L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private ResourceType resource;
	
	@Enumerated(EnumType.STRING)
	private RestrictionType readRestriction;
	
	@Enumerated(EnumType.STRING)
	private RestrictionType writeRestriction;

	private String description;

	@ManyToMany(mappedBy = "permissions")
	private List<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ResourceType getResource() {
		return resource;
	}

	public void setResource(ResourceType resource) {
		this.resource = resource;
	}

	public RestrictionType getReadRestriction() {
		return readRestriction;
	}

	public void setReadRestriction(RestrictionType readRestriction) {
		this.readRestriction = readRestriction;
	}

	public RestrictionType getWriteRestriction() {
		return writeRestriction;
	}

	public void setWriteRestriction(RestrictionType writeRestriction) {
		this.writeRestriction = writeRestriction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
