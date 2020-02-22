package it.akademija.model.document;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class NewDocument {
	
	@NotNull
	private String author;
	
	@NotNull
	@Length(min = 1, max = 20)
	private String docType;
	
	@NotNull
	@Length(min = 5, max = 30)
	private String title;
	
	@Length(max = 50)
	private String description;
	
	public NewDocument() {}
	
	public NewDocument(String author, String docType, String title, String description) {
		this.author = author;
		this.docType = docType;
		this.title = title;
		this.description = description;
	}
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}



}
