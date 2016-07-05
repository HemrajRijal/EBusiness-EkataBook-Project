package com.mvc.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mvc.model.BookInfo;
import com.mvc.model.UserInfo;
import com.mvc.util.DBConnector;

public class EkataDAOImpl implements UserDAO,BookDAO {
	Transaction tx=null;
	Session session;
	List<BookInfo> list;
	
	public String editRecord(BookInfo b)
	{
		try
		{
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			session.update(b);
			session.getTransaction().commit();
			
			return "Record Edited Successfully";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Record Could Not Be Modified Successfully";
			
		}
		finally
		{
			session.close();
		}
	}
	public BookInfo getBook(String isbn)
	{
		try
		{
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			isbn=isbn.replaceAll("'", "");
			/*
			Query query=session.createQuery("from Person where std_id = :stdid");
			query.setInteger("stdid",id);
			Person p=(Person)query.uniqueResult();
			*/
			BookInfo b=session.get(BookInfo.class, isbn);
			//Person p=session.load(Person.class, new Integer(id));
			return b;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			session.close();
		}
		
	}
	

	public String deleteRecord(String isbn)
	{
		try
		{
			isbn=isbn.replaceAll("'", "");
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			BookInfo b=(BookInfo)session.load(BookInfo.class, new String(isbn));
			//System.out.println("delete from BookInfo where isbn="+isbn+"");
			//Query query=session.createQuery("delete from BookInfo where isbn='"+isbn+"'");
			//query.executeUpdate();
			
			session.delete(b);
			session.getTransaction().commit();
			return "Record Deleted Successfully";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Error While Deleting Record";
		}
		finally
		{
			session.close();
		}
	}
	public List<BookInfo> retAllBooks()
	{
		try
		{
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			list=session.createQuery("from BookInfo order by isbn asc").list();
			//System.out.println("List Size="+list.size());
			tx.commit();
			
		}
		catch(Exception e)
		{
			tx.rollback();
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
		
		return list;
	}
	@Override
	public boolean verifyAdmin(UserInfo user) {
		// TODO Auto-generated method stub
		try
		{
			session=DBConnector.openSession();
			tx=session.beginTransaction();
			list=session.createQuery("from UserInfo where username='"+ user.getUsername() +"' and userpwd='"+ user.getUserpwd()+"'").list();
			//session.getTransaction().commit();
			if(list.size()>=1)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
			
		}
		finally
		{
			session.close();
		}
		
	}
	@Override
	public String addBookDetails(BookInfo book) {
		// TODO Auto-generated method stub
		try
		{
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			session.save(book);
			session.getTransaction().commit();
			return "Record Added Successfully!";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Error While Inserting Record";
		}
		finally
		{
			session.close();
		}
		
	}

}
