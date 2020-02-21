package it.akademija.model.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import it.akademija.model.doctype.DocType;

import it.akademija.model.user.User;

@Entity
@Table(name = "Group_table")
public class Group {

	@Id
	private String id;
	private String comment;

	@ManyToMany(mappedBy = "groups", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Collection<User> users;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinTable(name = "group_doctypes_for_creation", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "doctype_id", referencedColumnName = "id"))
	private Collection<DocType> docTypesForCreation;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinTable(name = "group_doctypes_for_approval", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "doctype_id", referencedColumnName = "id"))
	private Collection<DocType> docTypesForApproval;

	public Group() {
	}

	public Group(String id, String comment, Collection<User> users, Collection<DocType> docTypesForCreation,
			Collection<DocType> docTypesForApproval) {
		this.id = id;
		this.comment = comment;
		this.users = users;
		this.docTypesForApproval = docTypesForApproval;
		this.docTypesForCreation = docTypesForCreation;
	}

	public List<String> getGroupUsernames() {
		List<String> groupUsernames = new ArrayList<String>();
		for(User user: users) {
			groupUsernames.add(user.getUsername());
		}	
		return groupUsernames;
	}
	
	public List<String> getGroupDocTypesForApproval() {
		List<String> docTypesForApprovalNames = new ArrayList<String>();
		for(DocType docType: docTypesForApproval) {
			docTypesForApprovalNames.add(docType.getId());
		}	
		return docTypesForApprovalNames;
	}
	
	public List<String> getGroupDocTypesForCreation() {
		List<String> docTypesForCreationNames = new ArrayList<String>();
		for(DocType docType: docTypesForCreation) {
			docTypesForCreationNames.add(docType.getId());
		}	
		return docTypesForCreationNames;
	}
	
	public Group(String id, String comment) {
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

	public Collection<DocType> getDocTypesForCreation() {
		return docTypesForCreation;
	}

	public void setDocTypesForCreation(Collection<DocType> docTypesForCreation) {
		this.docTypesForCreation = docTypesForCreation;
	}

	public Collection<DocType> getDocTypesForApproval() {
		return docTypesForApproval;
	}

	public void setDocTypesForApproval(Collection<DocType> docTypesForApproval) {
		this.docTypesForApproval = docTypesForApproval;
	}

}
