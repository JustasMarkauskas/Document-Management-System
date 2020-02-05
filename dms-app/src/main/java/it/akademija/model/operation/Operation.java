package it.akademija.model.operation;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import it.akademija.model.role.Role;

@Entity
public class Operation implements GrantedAuthority{
	
	
	@Id
	private String id;

	@ManyToMany(mappedBy = "operations", cascade = {CascadeType.MERGE, CascadeType.DETACH})
	private Collection<Role> roles;

	
	public Operation() {
	}

	public Operation(String id) {

		this.id = id;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Collection<Role> getRoles() {
		return roles;
	}


	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String getAuthority() {
		return id;
	}
}
