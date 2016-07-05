package com.mvc.dao;

import java.util.List;

import com.mvc.model.BookInfo;

public interface BookDAO {
	public String editRecord(BookInfo b);
	public BookInfo getBook(String isbn);
	public String deleteRecord(String isbn);
	public String addBookDetails(BookInfo book);
    public List<BookInfo> retAllBooks();
}
