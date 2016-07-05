package com.mvc.model;

public class OrderInfo {
String cust_name;
String cust_email;
String cust_phone;
String isbn;
int quantity;
public OrderInfo()
{
	
}
public String getCust_name() {
	return cust_name;
}
public void setCust_name(String cust_name) {
	this.cust_name = cust_name;
}
public String getCust_email() {
	return cust_email;
}
public void setCust_email(String cust_email) {
	this.cust_email = cust_email;
}
public String getCust_phone() {
	return cust_phone;
}
public void setCust_phone(String cust_phone) {
	this.cust_phone = cust_phone;
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
public OrderInfo(String cust_name, String cust_email, String cust_phone, String isbn, int quantity) {
	super();
	this.cust_name = cust_name;
	this.cust_email = cust_email;
	this.cust_phone = cust_phone;
	this.isbn = isbn;
	this.quantity = quantity;
}

}
