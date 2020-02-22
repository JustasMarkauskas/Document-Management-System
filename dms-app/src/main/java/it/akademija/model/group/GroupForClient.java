package it.akademija.model.group;

import java.util.ArrayList;
import java.util.List;


public class GroupForClient {
	
	private String id;
	private String comment;
	private int groupSize;
	private List<String> groupUsers = new ArrayList<String>();
	private List<String> docTypesForCreation = new ArrayList<String>();
	private List<String> docTypesForApproval = new ArrayList<String>();

	public GroupForClient() {
	}
	
	public GroupForClient(String id, String comment, int groupSize) {
		this.id = id;
		this.comment = comment;
		this.groupSize = groupSize;
	}
	
	public GroupForClient(String id, String comment, int groupSize, List<String> groupUsers, List<String> docTypesForCreation, List<String> docTypesForApproval) {
		this.id = id;
		this.comment = comment;
		this.groupSize = groupSize;
		this.groupUsers = groupUsers;
		this.docTypesForCreation = docTypesForCreation;
		this.docTypesForApproval = docTypesForApproval;
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

	public int getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}



	public List<String> getGroupUsers() {
		return groupUsers;
	}

	public void setGroupUsers(List<String> groupUsers) {
		this.groupUsers = groupUsers;
	}




	public List<String> getDocTypesForCreation() {
		return docTypesForCreation;
	}




	public void setDocTypesForCreation(List<String> docTypesForCreation) {
		this.docTypesForCreation = docTypesForCreation;
	}




	public List<String> getDocTypesForApproval() {
		return docTypesForApproval;
	}




	public void setDocTypesForApproval(List<String> docTypesForApproval) {
		this.docTypesForApproval = docTypesForApproval;
	}

	

}
