package it.akademija.model.role;

import java.util.Collection;



import it.akademija.model.operation.Operation;
import it.akademija.model.user.User;

public class RoleForClient {

	
	private String id;
	private String comment;
	private Collection<User> users;
	private Collection<Operation> operations;
	

	public RoleForClient() {
	}

	public RoleForClient(String id, String comment, Collection<User> users, Collection<Operation> operations) {
		this.id = id;
		this.comment = comment;
		this.users = users;
		this.operations = operations;
	}

	
	public RoleForClient(String id, String comment) {
		this.id = id;
		this.setComment(comment);
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

	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
	
	
}
