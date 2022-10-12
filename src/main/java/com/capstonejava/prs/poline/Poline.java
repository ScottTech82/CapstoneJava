package com.capstonejava.prs.poline;

import javax.persistence.Column;


public class Poline {

	
	private int id;
	@Column(length=30, nullable=false)
	private String product;
	private int quantity;
	@Column(columnDefinition="decimal (11,2) NOT NULL DEFAULT 0")
	private double price;
	@Column(columnDefinition="decimal (11,2) NOT NULL DEFAULT 0")
	private double lineTotal;
	
	
	
	public Poline() {}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getProduct() {
		return product;
	}



	public void setProduct(String product) {
		this.product = product;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public double getLineTotal() {
		return lineTotal;
	}



	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}
	
	
	
	
}
