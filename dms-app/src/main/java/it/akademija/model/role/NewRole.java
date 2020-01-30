package it.akademija.model.role;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class NewRole {
	
	
	@NotNull
	@Length(min = 1, max = 15)
	private String id;
	
	
	public NewRole(@NotNull @Length(min = 1, max = 15) String id) {
		this.id = id;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

}
