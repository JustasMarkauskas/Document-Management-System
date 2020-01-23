package it.akademija.model.user;

public class UserForClient {
	
	private String firstName;
	private String lastName;
	private String username;

	public UserForClient() {
	}

	public UserForClient(String firstName, String lastName,  String username) {

		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
