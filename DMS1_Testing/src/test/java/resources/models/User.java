package resources.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("User")
public class User {
	
	@XStreamAlias("testDataGroup")
	private String testDataGroup;
	
	@XStreamAlias("identificator")
	private String identificator;
	
	@XStreamAlias("userName")
	private String userName;
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("surname")
	private String surname;
	
	@XStreamAlias("password")
	private String password;
	
	@XStreamAlias("confirmPassword")
	private String confirmPassword;
	
	@XStreamAlias("comments")
	private String comments;
	
	
	

	public String getTestDataGroup() {
		return testDataGroup;
	}

	public void setTestDataGroup(String testDataGroup) {
		this.testDataGroup = testDataGroup;
	}

	public String getIdentificator() {
		return identificator;
	}

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	

	
	

}
