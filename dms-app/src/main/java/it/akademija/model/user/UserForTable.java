package it.akademija.model.user;

import java.util.ArrayList;
import java.util.List;

public class UserForTable {
	
	private String firstName;
	private String lastName;
	private String username;
	private String comment;
	private List<String> userGroups = new ArrayList<String>();
	private long totalUsers;

	public UserForTable() {
	}
	
	public UserForTable(String firstName, String lastName, String username, String comment, List<String> userGroups,
			long totalUsers) {
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.comment = comment;
		this.userGroups = userGroups;
		this.totalUsers = totalUsers;
	}


	


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public List<String> getUserGroups() {
		return userGroups;
	}


	public void setUserGroups(List<String> userGroups) {
		this.userGroups = userGroups;
	}


	public long getTotalUsers() {
		return totalUsers;
	}


	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

}
