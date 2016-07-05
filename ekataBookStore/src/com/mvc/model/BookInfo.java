package com.mvc.model;

public class BookInfo {

String isbn;
String title;
String author;
int quantity;
float price;
String image;
byte[] bookimage;
public BookInfo()
{
	
}
public String getIsbn() {
	return isbn;
}
public void setIsbn(String isbn) {
	this.isbn = isbn;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
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
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public byte[] getBookimage() {
	return bookimage;
}
public void setBookimage(byte[] bookimage) {
	this.bookimage = bookimage;
}
public BookInfo(String isbn, String title, String author, int quantity, float price, String image, byte[] bookimage) {
	super();
	this.isbn = isbn;
	this.title = title;
	this.author = author;
	this.quantity = quantity;
	this.price = price;
	this.image = image;
	this.bookimage = bookimage;
}

}
