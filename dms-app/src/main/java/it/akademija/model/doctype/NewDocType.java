package it.akademija.model.doctype;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class NewDocType {
	
	@NotNull
	@Length(min = 1, max = 25)
	private String id;
	
	@Length(max = 50)
	private String comment;

	public NewDocType( String id, String comment) {
		
		this.id = id;
		this.comment = comment;
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

}
