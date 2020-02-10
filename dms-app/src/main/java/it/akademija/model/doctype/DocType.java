package it.akademija.model.doctype;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import it.akademija.model.role.Role;

@Entity
public class DocType {
	
	@Id
	private String id;
	private String comment;
	
	@ManyToMany(mappedBy = "docTypesForCreation", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Collection<Role> rolesCanCreate;
	
	@ManyToMany(mappedBy = "docTypesForApproval", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Collection<Role> rolesCanApprove;

	
	public DocType() {}
	
	public DocType(String id) {
		this.id = id;
	}
	
	public DocType(String id, String comment, Collection<Role> rolesCanCreate, Collection<Role> rolesCanApprove) {
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
