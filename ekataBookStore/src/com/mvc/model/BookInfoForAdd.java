package com.mvc.model;

public class BookInfoForAdd {
String isbn;
byte[] bookimage;
float price;
public BookInfoForAdd()
{
	
}
public String getIsbn() {
	return isbn;
}
public void setIsbn(String isbn) {
	this.isbn = isbn;
}
public byte[] getBookimage() {
	return bookimage;
}
public void setBookimage(byte[] bookimage) {
	this.bookimage = bookimage;
}
public float getPrice() {
	return price;
}
public void setPrice(float price) {
	this.price = price;
}
public BookInfoForAdd(String isbn, byte[] bookimage, float price) {
	super();
	this.isbn = isbn;
	this.bookimage = bookimage;
	this.price = price;
}

}
