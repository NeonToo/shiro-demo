package com.sap.csc.app.model.jpa;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * @author I326950
 */
@Entity
public class User extends ModifiableEntity {

	private static final long serialVersionUID = -3766742575352344531L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String username;
	
	private String password;
	
	@ManyToOne
	private Department department;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			// self
			joinColumns = @JoinColumn(name = "userId"),
			// inverse
			inverseJoinColumns = @JoinColumn(name = "roleId"))
	private List<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Transient
	public Set<String> getStringRoles() {
		List<Role> roles = this.getRoles();
		Set<String> stringRoles = new LinkedHashSet<>();
		
		for(Role role: roles) {
			stringRoles.add(role.getName());
		}
		
		return stringRoles;
	}

}
