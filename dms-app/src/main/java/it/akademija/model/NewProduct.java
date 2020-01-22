package it.akademija.model;

import javax.persistence.Column;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class NewProduct {
	
	@NotNull
	@Length(min = 1, max = 30)
	@Column
	private String title;
	@NotNull
	@Length(min = 1, max = 30)
	@Column
	private String image;
	@NotNull
	@Length(min = 1, max = 100)
	@Column
	private String description;
	@NotNull
	@Column
	private double price;
	@NotNull
	@Column
	private int quantity;

	


	public NewProduct() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	

}
