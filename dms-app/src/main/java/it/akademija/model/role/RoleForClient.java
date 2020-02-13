package it.akademija.model.role;

import java.util.Collection;

import it.akademija.model.user.User;

public class RoleForClient {

	
	private String id;
	private String comment;
	private Collection<User> users;

	

	public RoleForClient() {
	}

	public RoleForClient(String id, String comment, Collection<User> users) {
		this.id = id;
		this.comment = comment;
		this.users = users;
		
	
	}

	
	public RoleForClient(String id, String comment) {
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


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}





	
	
	
}
