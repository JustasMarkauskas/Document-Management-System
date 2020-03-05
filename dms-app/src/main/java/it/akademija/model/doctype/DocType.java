package it.akademija.model.doctype;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import it.akademija.model.group.Group;


@Entity
public class DocType {
	
	@Id
	private String id;
	private String comment;
	
	@ManyToMany(mappedBy = "docTypesForCreation", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Collection<Group> groupsCanCreate;
	
	@ManyToMany(mappedBy = "docTypesForApproval", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Collection<Group> groupsCanApprove;

	
	public DocType() {}
	
	public DocType(String id) {
		this.id = id;
	}
	
	public DocType(String id, String comment, Collection<Group> groupsCanCreate, Collection<Group> groupsCanApprove) {
		this.id = id;
		this.comment = comment;
		this.groupsCanCreate = groupsCanCreate;
		this.groupsCanApprove = groupsCanApprove;
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

	public Collection<Group> getGroupsCanCreate() {
		return groupsCanCreate;
	}

	public void setGroupsCanCreate(Collection<Group> groupsCanCreate) {
		this.groupsCanCreate = groupsCanCreate;
	}

	public Collection<Group> getGroupsCanApprove() {
		return groupsCanApprove;
	}

	public void setGroupsCanApprove(Collection<Group> groupsCanApprove) {
		this.groupsCanApprove = groupsCanApprove;
	}



}
