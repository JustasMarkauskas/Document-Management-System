package it.akademija.model.doctype;

import java.util.Collection;

import it.akademija.model.role.Role;

public class DocTypeForClient {
	
	private String id;
	private String comment;
	private Collection<Role> rolesCanCreate;
	private Collection<Role> rolesCanApprove;
	
	public DocTypeForClient() {}
	
	public DocTypeForClient(String id) {
		this.id = id;
	}
		
	public DocTypeForClient(String id, String comment, Collection<Role> rolesCanCreate,
			Collection<Role> rolesCanApprove) {

		this.id = id;
		this.comment = comment;
		this.rolesCanCreate = rolesCanCreate;
		this.rolesCanApprove = rolesCanApprove;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Collection<Role> getRolesCanCreate() {
		return rolesCanCreate;
	}
	public void setRolesCanCreate(Collection<Role> rolesCanCreate) {
		this.rolesCanCreate = rolesCanCreate;
	}
	public Collection<Role> getRolesCanApprove() {
		return rolesCanApprove;
	}
	public void setRolesCanApprove(Collection<Role> rolesCanApprove) {
		this.rolesCanApprove = rolesCanApprove;
	}
	
	
	

}
