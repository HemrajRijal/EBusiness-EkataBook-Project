package com.mvc.model;

public class CartInfo {
String isbn;
int quantity;
float price;
public CartInfo()
{
	
}
public String getIsbn() {
	return isbn;
}
public void setIsbn(String isbn) {
	this.isbn = isbn;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public float getPrice() {
	return price;
}
public void setPrice(float price) {
	this.price = price;
}
public CartInfo(String isbn, int quantity, float price) {
	super();
	this.isbn = isbn;
	this.quantity = quantity;
	this.price = price;
}

}
