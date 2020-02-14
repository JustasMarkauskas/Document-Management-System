package it.akademija.model.doctype;

import java.util.Collection;

import it.akademija.model.group.Group;


public class DocTypeForClient {
	
	private String id;
	private String comment;
	private Collection<Group> groupsCanCreate;
	private Collection<Group> groupsCanApprove;
	
	public DocTypeForClient() {}
	
	public DocTypeForClient(String id) {
		this.id = id;
	}
	
	public DocTypeForClient(String id, String comment) {
		this.id = id;
		this.comment = comment;
	}
		
	public DocTypeForClient(String id, String comment, Collection<Group> groupsCanCreate,
			Collection<Group> groupsCanApprove) {

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
