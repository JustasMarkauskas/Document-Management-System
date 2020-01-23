package it.akademija.model.user;



public class NewUser {

	private String firstName;
	private String lastName;
	private String password;
	private String username;

	public NewUser() {
	}

	public NewUser(String firstName, String lastName, String password, String username) {

		this.firstName = firstName;
		this.lastName = lastName;
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

}
