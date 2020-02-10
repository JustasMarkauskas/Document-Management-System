package it.akademija.model.role;

import java.util.Collection;

import it.akademija.model.doctype.DocType;
import it.akademija.model.operation.Operation;
import it.akademija.model.user.User;

public class RoleForClient {

	
	private String id;
	private String comment;
	private Collection<User> users;
	private Collection<Operation> operations;
	private Collection<DocType> docTypesForApproval;
	private Collection<DocType> docTypesForCreation;
	private int groupSize;
	

	public RoleForClient() {
	}

	public RoleForClient(String id, String comment, Collection<User> users, Collection<Operation> operations, Collection<DocType> docTypesForApproval, Collection<DocType> docTypesForCreation) {
		this.id = id;
		this.comment = comment;
		this.users = users;
		this.operations = operations;
		this.docTypesForApproval = docTypesForApproval;
		this.docTypesForCreation = docTypesForCreation;
	
	}

	
	public RoleForClient(String id, String comment, int groupSize) {
		this.id = id;
		this.comment = comment;
		this.groupSize = groupSize;
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

	public int getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

	public Collection<DocType> getDocTypesForApproval() {
		return docTypesForApproval;
	}

	public void setDocTypesForApproval(Collection<DocType> docTypesForApproval) {
		this.docTypesForApproval = docTypesForApproval;
	}

	public Collection<DocType> getDocTypesForCreation() {
		return docTypesForCreation;
	}

	public void setDocTypesForCreation(Collection<DocType> docTypesForCreation) {
		this.docTypesForCreation = docTypesForCreation;
	}

	
	
	
}
