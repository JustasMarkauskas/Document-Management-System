package it.akademija.model.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class NewUser {

	@NotNull
	@Length(min = 1, max = 30)
	private String firstName;
	@NotNull
	@Length(min = 1, max = 30)
	private String lastName;
	@Length(max = 50)
	private String comment;
	@NotNull
	@Length(min = 8, max = 20)
	private String password;
	@NotNull
	@Length(min = 5, max = 15)
	private String username;

	public NewUser() {
	}

	public NewUser(String firstName, String lastName, String comment, String password, String username) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.comment = comment;
		this.password = password;
		this.username = username;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}
