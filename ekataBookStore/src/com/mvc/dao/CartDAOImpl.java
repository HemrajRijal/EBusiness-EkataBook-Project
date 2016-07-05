package com.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ebl.PaymentGatewayStub;
import com.mvc.model.BookInfo;
import com.mvc.model.BookInfoForAdd;
import com.mvc.model.BookInfoS;
import com.mvc.model.CartInfo;
import com.mvc.model.OrderInfo;
import com.mvc.util.DBConnector;

public class CartDAOImpl implements CartDAO {
	Transaction tx=null;
	Session session;
	List<String> list;
	List<BookInfo> books;
	List<CartInfo> carts;
	List<OrderInfo> orders;
	List<BookInfoForAdd> booksadd;
	public CartDAOImpl()
	{
		list=new ArrayList<String>();
		books=new ArrayList<BookInfo>();
		booksadd=new ArrayList<BookInfoForAdd>();
		
	}
	public String addAddsRecord(String isbn)
	{
		try
		{
		//	List<BookInfo> lst=retBooks();
			BookInfo b=new EkataDAOImpl().getBook(isbn);
			float price=b.getPrice();
			price=(float) (price-(price*0.2));
			boolean found=false;
			if(carts ==null)//check whether the cart is empty
			{
				carts=new ArrayList<CartInfo>();
				carts.add(new CartInfo(isbn,1,price));
				System.out.println("Cart is Empty and adding add items into the cart");
			}
			else
			{
			//Check whethter an item already exists on the cart, if yes, then simply increase its quantity by one, else add new items into the cart
				System.out.println("Cart is Not Empty");	
				for(int i=0;i<carts.size();i++)
					{
						if(isbn.equals(carts.get(i).getIsbn()))
						{
						   carts.get(i).setQuantity(carts.get(i).getQuantity()+1);
						   carts.get(i).setPrice(price);
							found=true;
							System.out.println("Price and Quantity Adjusted for already item existent on cart");
						}
					}
					if(! found)// if cart is not empty and if there does not exist an add item into the cart
					{
						carts.add(new CartInfo(isbn,1,price));
						System.out.println("Add Item is not on the cart and hence new instance of it created and added into the cart");
					}
			//If an item already exists on the cart, then Give 20% discount on those items
			/*for(int i=0;i<lst.size();i++)
			{
				if(isbn.equals(lst.get(i).getIsbn()))
				{
					lst.get(i).setPrice(price);
				}
			}*/
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Fail";
		}
		finally
		{
			//session.close();
		}
		return "ok";
	}
	
	public String updateRecord(List<OrderInfo> bkinfo)
	{
		try
		{
			
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			for(int i=0;i<bkinfo.size();i++)
			{
			Query query=session.createQuery("update BookInfo set quantity=quantity-"+bkinfo.get(i).getQuantity() + " where isbn='"+bkinfo.get(i).getIsbn()+"'");
			query.executeUpdate();
			}
			
			//session.delete(b);
			session.getTransaction().commit();
			return "ok";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Error While Updating Quantity Record";
		}
		finally
		{
			session.close();
		}
	}
	
	
	public String addBookDetails(List<OrderInfo> bkinfo) {
		// TODO Auto-generated method stub
		try
		{
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			for(int i=0;i<bkinfo.size();i++)
			{
			session.save(bkinfo.get(i));
			
			}
			session.getTransaction().commit();
			return "ok";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Server Error While Commiting the Transaction";
			// you can write code here to sumup the ammount that was previously deducted. 
		}
		finally
		{
			session.close();
		}
		
	}
	public String finalizePayments(String cust_name,String cust_email,String cust_accno,String cust_phone,ArrayList<BookInfoS> bkinfos)
	{
		try
		{
			orders=new ArrayList<OrderInfo>();
			for(int i=0;i<bkinfos.size();i++)
			{
				//new OrderInfo(cust_name,cust_email,cust_phone,bkinfos.get(i).getIsbn(),bkinfos.get(i).getQuantity());
				orders.add(new OrderInfo(cust_name,cust_email,cust_phone,bkinfos.get(i).getIsbn(),bkinfos.get(i).getQuantity()));
			}
			
			// Construct an order confirmation table to be sent to customer via email.
			String tbl_body = "<table border='1' cellpadding='6'> <thead> <th>TITLE</th><th>Author</th><th>QTY</th><th>Price</th></thead>";
		    String tbl_row="";
		    float tot=0;
		    for(int i=0;i<bkinfos.size();i++ )
		    {
		    	tbl_row="";
		    	tbl_row += "<td>"+bkinfos.get(i).getTitle()+"</td>";
		    	tbl_row += "<td>"+bkinfos.get(i).getAuthor()+"</td>";
		    	tbl_row += "<td>"+bkinfos.get(i).getQuantity()+"</td>";
		    	tbl_row += "<td>"+bkinfos.get(i).getPrice()+"</td>";
		    //	tbl_row += "<td><img src='"+bkinfos.get(i).getBookimage()+"' width='50' height='50'/>"+"</td>";
		    	
		    	tbl_body += "<tr>"+tbl_row+"</tr>";
		    	tot=tot+(bkinfos.get(i).getQuantity()*bkinfos.get(i).getPrice());
		    }
		    tbl_row = "<td></td>"; 
		    tbl_row += "<td></td>"; 
		    tbl_row += "<td></td>"; 
		    //tbl_row += "<td></td>"; 
		    tbl_row += "<td>Total Price:"+ tot + "</td>";
		   // tbl_row += "<td></td>"; 
		     tbl_body += "<tr>"+tbl_row+"</tr>";
		    tbl_body=tbl_body + " </table>";
		    //Account Verfification and calling Banking Service
		    ////////////////////////////////////////////////
		    PaymentGatewayStub stub=new PaymentGatewayStub();
			PaymentGatewayStub.CheckBalance params=new PaymentGatewayStub.CheckBalance();
			params.setActno(cust_accno);
			params.setAmount(tot);
			PaymentGatewayStub.CheckBalanceResponse res=stub.checkBalance(params);
			String result=res.get_return();
			System.out.println("Result="+result);
			if(!result.equalsIgnoreCase("ok"))
			{
				return result;
			}
			String inst=addBookDetails(orders);// insert order details into the database
			System.out.println("Insertion Status="+inst);
			if(!inst.equalsIgnoreCase("ok"))// If Order Records are not inserted Successfully, Send Error Message !
			{
				return inst;
			}
			String updtst=updateRecord(orders);
			if(!updtst.equalsIgnoreCase("ok"))
			{
				return updtst;
			}
		    //////////////////////////////////////////////////
		    SendEmail obj=new SendEmail();
		    String emailstatus=obj.sendmail(cust_email, tbl_body,cust_name);
		    if(!emailstatus.equalsIgnoreCase("ok"))// If email cannot be sent Successfully, Send Error Message !
			{
				return emailstatus;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Transaction UnSuccessfull";
		}
		
		return "success";
	}
	public String updateCartItem(String isbn,int quantity)
	{
		
		//List<CartInfo> existingcartitems=retCarts();
		boolean found=false;
		for(int i=0;i<carts.size();i++)
		{
			if(isbn.equalsIgnoreCase(carts.get(i).getIsbn()))
			{
				found=true;
				carts.get(i).setQuantity(quantity);;
				break;
			}
		}
		if(found)
		{
			System.out.println("After deletion from cart, cart size is"+carts.size());  
			return "Record Updated Successfully";
		}
		else
		{
			return "Record Not Updated Successfully";
		}
		
	}
	public String removeFromCart(String isbn)
	{
		
		//List<CartInfo> existingcartitems=retCarts();
		boolean found=false;
		for(int i=0;i<carts.size();i++)
		{
			if(isbn.equalsIgnoreCase(carts.get(i).getIsbn()))
			{
				found=true;
				carts.remove(i);
				break;
			}
		}
		if(found)
		{
			System.out.println("After deletion from cart, cart size is"+carts.size());  
			return "Record Deleted Successfully";
		}
		else
		{
			return "Record Not Deleted Successfully";
		}
		
	}
	public List<BookInfo> returnBooksInCart()
	{
		try
		{
			if (!carts.isEmpty())
			{
				String isbn="";
				for(int i=0;i<carts.size();i++)
				{
				  String is=carts.get(i).getIsbn();
				  isbn+= "'"+is+"'"+   ",";
				}
				String c=isbn.substring(0,isbn.lastIndexOf(",")) +" )";
				String condition=" where isbn in ( "+isbn.substring(0,isbn.lastIndexOf(",")) +" )";
				String query="from BookInfo  "+condition;
				//System.out.println("Query="+query);
					
				session = DBConnector.openSession();
				tx=session.beginTransaction();
				books=session.createQuery(query).list();
				//System.out.println("List Size="+list.size());
				tx.commit();
			}
			else
			{
				books=null;
			}
			
			
		}
		catch(Exception e)
		{
			books=null;
			tx.rollback();
			e.printStackTrace();
		}
		finally
		{
			try{session.close();}catch(Exception p){}
		}
		
		return books;
	}
	/////////////////////////////////////////
	public List<BookInfoForAdd> returnBooksForAdd(List<String> ads)
	{
		try
		{
			System.out.println("Ads="+ads);
			
			/*if((ads !=null) && (carts != null))
			{
				System.out.println("Cart Size="+carts.size());
				for(int i=0;i<carts.size();i++)// Remove isbns from ads if it is already added into the cart. 
			{
				  for(int j=0;j<ads.size();j++)
				  {
					  if(carts.get(i).getIsbn().equalsIgnoreCase(ads.get(j)))
					  {
						  ads.remove(j);
					  }
				  }
			}
			}*/
		System.out.println("ppppppppppp");
			
			if (ads != null)
			{
				booksadd=new ArrayList<BookInfoForAdd>();
				String isbn="";
				for(int i=0;i<ads.size();i++)
				{
				  String is=ads.get(i);
				  isbn+= "'"+is+"'"+   ",";
				}
				System.out.println("ISBNNNNN="+isbn);
				if(isbn.equalsIgnoreCase(""))
				{
					booksadd=null;
				}
				else
				{
					String c=isbn.substring(0,isbn.lastIndexOf(",")) +" )";
					String condition=" where isbn in ( "+isbn.substring(0,isbn.lastIndexOf(",")) +" )";
					String query="from BookInfoForAdd  "+condition;
					System.out.println("Query="+query);
						
					session = DBConnector.openSession();
					tx=session.beginTransaction();
					booksadd=session.createQuery(query).list();
					//System.out.println("List Size="+list.size());
					tx.commit();
				}
				
			}
			else
			{
				booksadd=null;
			}
			
			
		}
		catch(Exception e)
		{
			booksadd=null;
			//tx.rollback();
			e.printStackTrace();
		}
		finally
		{
			try{session.close();}catch(Exception p){}
		}
		
		return booksadd;
	}
	///////////////////////////////////////////////////////
	
	
	
	public List<CartInfo> retCarts()
	{
		//System.out.print("Cart Size Just Before Returning="+carts.size());
		return carts;
	}
	public List<BookInfo> retBookInfoObjectsofCartItems()
	{
		return books;
	}
	
	public void addtoCart(CartInfo[]objs,boolean already)
	{
	//	System.out.println("Add Time Quantity Infos");
		if(already)
		{
			List<CartInfo> existingcartitems=retCarts();
			boolean found=false;
			for(int j=0;j<objs.length;j++)
			{
				found=false;
				//String isbn=existingcartitems.get(i).getIsbn();
				String isbn=objs[j].getIsbn();
				for(int i=0;i<existingcartitems.size();i++)
				{
					if(isbn.equalsIgnoreCase(existingcartitems.get(i).getIsbn()))
					{
						found=true;
						existingcartitems.get(i).setQuantity((objs[j].getQuantity()+existingcartitems.get(i).getQuantity()));
						break;
					}
				}
				 if(found)
				 {
					 //objs[j].setQuantity(objs[j].getQuantity()+);
				 }
				 else
				 {
					 carts.add(objs[j]);
				 }
				
				
			}
			
			
			
			
		}
		else
		{
			carts=new ArrayList<CartInfo>();
			for(int i=0;i<objs.length;i++)
			{
				   
				   carts.add(objs[i]);
				
			}
			System.out.print("Number of Items Added="+carts.size());
		}
		
	}
	public List<BookInfo> retAllBooks(String aonly,String atitle)
	{
		try
		{
			String condition="";
			System.out.println("Aonly="+aonly+"--Atitle="+atitle);
			if(aonly!="")
			{
				condition=" where author='"+aonly+"'";
			}
			else
			{
				if(atitle != "")
				{
					condition=" where author like '"+atitle+"%' OR title like '"+atitle+"%'";
				}
			}
			String query="from BookInfo"+ condition+ " order by isbn asc";
			System.out.println("Query="+query);
				
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			books=session.createQuery(query).list();
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
		
		return books;
	}

	@Override
	public List<String> getAuthors() {
		// TODO Auto-generated method stub
		try
		{
			session = DBConnector.openSession();
			tx=session.beginTransaction();
			list=session.createQuery("select author from BookInfo order by author asc").list();
			//System.out.println("List Size="+list.size());
			tx.commit();
			
		}
		catch(Exception e)
		{
			tx.rollback();
			e.printStackTrace();
			return null;
		}
		finally
		{
			session.close();
		}
		return list;
	}
	
}
