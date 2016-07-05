package com.mvc.dao;

import java.util.List;

import com.mvc.model.BookInfo;
import com.mvc.model.CartInfo;

public interface CartDAO {
	public void addtoCart(CartInfo[]objs,boolean already);
	public List<BookInfo> retAllBooks(String aonly,String atitle);
	public List<String> getAuthors();
}
