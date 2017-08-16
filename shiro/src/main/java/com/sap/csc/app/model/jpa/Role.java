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
import javax.persistence.Transient;

/**
 * @author I326950
 */
@Entity
public class Role extends ModifiableEntity {

	private static final long serialVersionUID = -1605744151852948073L;

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			// self
			joinColumns = @JoinColumn(name = "roleId"),
			// inverse
			inverseJoinColumns = @JoinColumn(name = "permissionId"))
	private List<Permission> permissions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@Transient
	public Set<String> getStringPermissions() {
		List<Permission> permissions = this.getPermissions();
		Set<String> stringPermissions = new LinkedHashSet<>();
		
		for(Permission permission: permissions) {
			String[] permissionDescs = permission.getDescription().split(";");
			
			for(String permissionDesc: permissionDescs) {
				stringPermissions.add(permissionDesc);
			}
		}
		
		return stringPermissions;
	}
}
