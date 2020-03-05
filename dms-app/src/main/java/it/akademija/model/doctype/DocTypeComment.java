package it.akademija.model.doctype;

import org.hibernate.validator.constraints.Length;

public class DocTypeComment {
	
	@Length(max = 50)
	private String comment;
	
	public DocTypeComment() {}

	public DocTypeComment(String comment) {
	
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
