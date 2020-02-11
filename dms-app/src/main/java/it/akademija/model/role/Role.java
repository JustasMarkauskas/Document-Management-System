package it.akademija.model.role;

import java.util.Collection;


import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import it.akademija.model.user.User;

@Entity
public class Role implements GrantedAuthority {

	@Id
	private String id;
	private String comment;

	@ManyToMany(mappedBy = "roles", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Collection<User> users;
	
	

	public Role() {
	}

	public Role(String id, String comment, Collection<User> users) {
		this.id = id;
		this.comment = comment;
		this.users = users;
	
	}

	public Role(String id, String comment) {
		this.id = id;
		this.comment = comment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@Override
	public String getAuthority() {
		return id;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

	

	

	

}
