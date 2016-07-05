package com.mvc.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mvc.dao.CartDAOImpl;
import com.mvc.model.BookInfo;
import com.mvc.model.BookInfoForAdd;
import com.mvc.model.BookInfoS;
import com.mvc.model.CartInfo;
import com.mvc.dao.SendEmail;

/**
 * Servlet implementation class CartController
 */
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CartDAOImpl cdaoimpl;   
    List<BookInfoS> lstr;
    List<BookInfoS> lstradd;
    List<BookInfo> lst;
    List<CartInfo> carts;
    List<String> cartcookies;
    HttpSession session;
    boolean firstime=true;//checking first time display of CartController?action=displayMainPage
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();
        cdaoimpl=new CartDAOImpl();
     //   System.out.println("Cart Controller Constructor");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		 System.out.println("Cart Controller Get Method");
		String view_name="";
		try
		{
		
		String atitle=request.getParameter("atitle");
		String aonly=request.getParameter("aonly");
		//System.out.println("JSON SEARCH DATA="+atitle);
		String action=request.getParameter("action");
		
		
		if(((atitle != null) || (aonly !=null))   )
		{
			
			List<BookInfo> lst=cdaoimpl.retAllBooks(aonly,atitle);
			Gson gson=new Gson();
			String lead="data:image/png;base64,";
			lstr=new ArrayList();
			System.out.println("List Size="+lstr.size());
			for(int i=0;i<lst.size();i++)
			{
				BookInfo b=lst.get(i);
				byte ib[]=b.getBookimage();
				byte[] img64=Base64.encodeBase64(ib);
	            String photo64 = new String(img64);
	            lead=lead+photo64;
	            lstr.add(new BookInfoS(b.getIsbn(),b.getTitle(),b.getAuthor(),b.getQuantity(),b.getPrice(),b.getImage(),lead));
	            lead="data:image/png;base64,";
			}
			String jsonstr=gson.toJson(lstr);
			response.setContentType("json");
			 System.out.println("Alread Wrote="+jsonstr.length());
			 response.getWriter().write(jsonstr);
			
		}
		if(action != null)
		{
			//System.out.println("Value of Action="+action);
			if(action.equalsIgnoreCase("exitshopping"))
			{
				doPost(request, response);
				
			}
		if(action.equalsIgnoreCase("displayMainPage"))
		{
			Cookie[] cookies = request.getCookies();
			System.out.println("Inside Display Main Page");
			boolean olduser=false;
			for(int i=0;i<cookies.length;i++)//Delete the cookies that were previously set; only cookies for those books which had not been purchased yet will persist on buyers computer
			{
				//System.out.println(cookies[i].getName());
				if(cookies[i].getName().trim().equalsIgnoreCase("userid"))
				{
					
					//System.out.println("Inside For IF");
					olduser=true;
					break;
				}
				//response.addCookie(cookies[i]);
			}
			if(! olduser)
			{
				System.out.println("Outside For IF");
				Cookie cookie=new Cookie("userid",String.valueOf(java.util.UUID.randomUUID()));
				cookie.setMaxAge(24*60*60*30);
				response.addCookie(cookie);
				System.out.println("Cookies Set");
				olduser=true;
				
			}
			//String cartcookies[]=new String[100];
			
			int j=0;
			boolean checkcookie=false;
		if(firstime)
		{
			cartcookies=new ArrayList<String>();
			for(int i=0;i<cookies.length;i++)//For Displaying Ads
			{
				if(cookies[i].getValue().equalsIgnoreCase("isbn"))
				{
					cartcookies.add(cookies[i].getName());
					//cartcookies[j]=cookies[i].getName();
					checkcookie=true;
					firstime=false;
					j=j+1;
				}
			}
		}
			
		System.out.println("Outside Setting Adds="+cartcookies.toString());	
		if( !cartcookies.isEmpty())
			{
			  System.out.println("Inside Setting Adds");
				List<BookInfoForAdd> isbnads=cdaoimpl.returnBooksForAdd(cartcookies);
			  if(isbnads != null)
			  {
				  
			  
				Gson gson=new Gson();
				String lead="data:image/png;base64,";
				lstradd=new ArrayList();
				//System.out.println("List Size="+lstr.size());
				for(int i=0;i<isbnads.size();i++)
				{
					BookInfoForAdd b=isbnads.get(i);
					byte ib[]=b.getBookimage();
					byte[] img64=Base64.encodeBase64(ib);
		            String photo64 = new String(img64);
		            lead=lead+photo64;
		            //lstr.add(new BookInfoS(b.getIsbn(),b.getTitle(),b.getAuthor(),b.getQuantity(),b.getPrice(),b.getImage(),lead));
		            lstradd.add(new BookInfoS(b.getIsbn(),b.getPrice(),lead));
		            lead="data:image/png;base64,";
				}
			  
				  String jsonstr=gson.toJson(lstradd);
				//  System.out.println("Main Page Adds="+jsonstr);
				 request.setAttribute("adds", jsonstr);
			  }
				///////////////////////////////////////////////
			}
			
			view_name="/views/cart/searchPage.jsp";
	      request.setAttribute("ids", cdaoimpl.getAuthors());
	      RequestDispatcher view=request.getRequestDispatcher(view_name);
			view.forward(request, response);
			//System.out.println("After Dispatching Request");
		}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String view_name="";
		String paymentstatus="";
		String addcarts=request.getParameter("addtocart");
		String removecart=request.getParameter("removecart");
		String updatecart=request.getParameter("updatecart");
		String checkout=request.getParameter("checkoutvals");
		String action=request.getParameter("action");
		String isbn1=request.getParameter("addsisbn");
		System.out.println("Hi val="+isbn1);
		String lead="";
		//for adding add items into the cart
		if(isbn1 != null)
		{
			
			System.out.println("Inside");
			String status=cdaoimpl.addAddsRecord(isbn1);
/////////////////////////////////////////////////////////////pppppppppppppppp
			if(session == null)
			{
				session=request.getSession(true);
			}
			else
			{
				session=request.getSession(false);
			}
			List<BookInfo> lst=cdaoimpl.returnBooksInCart();
		 	lead="data:image/png;base64,";
		 	List<CartInfo> carts=cdaoimpl.retCarts();
		 	lstr=new ArrayList<BookInfoS>();
		 	System.out.println("\nLst Sizeeeee="+lst.size());
		 	System.out.println("Cart Sizeeee="+carts.size());
	
		for(int i=0;i<lst.size();i++)
		{
			for(int j=0;j<carts.size();j++)
			{
				
				if(lst.get(i).getIsbn().equalsIgnoreCase(carts.get(j).getIsbn()))
				{
					BookInfo b=lst.get(i);
					byte ib[]=b.getBookimage();
					byte[] img64=Base64.encodeBase64(ib);
		            String photo64 = new String(img64);
		            lead=lead+photo64;
		            System.out.println(carts.get(i).getQuantity());
		            lstr.add(new BookInfoS(b.getIsbn(),b.getTitle(),b.getAuthor(),carts.get(j).getQuantity(),carts.get(j).getPrice(),b.getImage(),lead));
		            lead="data:image/png;base64,";
		            break;
		            
				}
			}
		}
		Gson gson=new Gson();
		String jsonstr1=gson.toJson(lstr);
		System.out.println("Returned Book Size="+lstr.size());
		String str="asda";
		///response.getWriter().write("true");
		for(int i=0;i<carts.size();i++)
		{
          Cookie cookie=new Cookie(carts.get(i).getIsbn(),"isbn");
          cookie.setComment("ekatabookstore");
          cookie.setMaxAge(24*60*60*30);
          response.addCookie(cookie);
		}
		
		if(session != null) 
		{
		  session.setAttribute("carts", jsonstr1);
		}
			
//////////////////////////////////////////////////////////////////pppppp
			response.setContentType("json");
			Gson gson1=new Gson();
			if(status.equalsIgnoreCase("ok"))
			{
				///////////////////////////////////
				//List<String> cartcookies=new ArrayList<String>();
				System.out.println("Removed="+isbn1);
				cartcookies.remove(cartcookies.indexOf(isbn1));
				List<BookInfoForAdd> isbnads=cdaoimpl.returnBooksForAdd(cartcookies);
				System.out.println("isbnads="+isbnads);
				  if(isbnads != null)
				  {
							 lead="data:image/png;base64,";
							lstradd=new ArrayList();
							for(int i=0;i<isbnads.size();i++)
							{
								BookInfoForAdd b=isbnads.get(i);
								byte ib[]=b.getBookimage();
								byte[] img64=Base64.encodeBase64(ib);
					            String photo64 = new String(img64);
					            lead=lead+photo64;
					            //lstr.add(new BookInfoS(b.getIsbn(),b.getTitle(),b.getAuthor(),b.getQuantity(),b.getPrice(),b.getImage(),lead));
					            lstradd.add(new BookInfoS(b.getIsbn(),b.getPrice(),lead));
					            lead="data:image/png;base64,";
							}
							System.out.println("Lstr Size="+lstradd.size());
							String jsonstr=gson1.toJson(lstradd);
							//System.out.println("Returned JSON String="+jsonstr);
							//request.setAttribute("adds", jsonstr);
							response.getWriter().write(jsonstr);
					 //////////////////////////////////////////////////////////////
							
					 //////////////////////////////////////////////////////////
				  }
				  else
					{
						response.getWriter().write(gson.toJson("Fail"));
					}
				///////////////////////////////////////////////
			}
			
			
			
			//System.out.println("Hi,I am inside addddsss section");
		}
		
		if(action !=null)
		{
			if(action.equalsIgnoreCase("exitshopping"))
			{
				
				view_name="/views/cart/close.jsp";
				request.getSession(false).invalidate();
				RequestDispatcher view=request.getRequestDispatcher(view_name);
				view.forward(request, response);
			}
		}
		if(checkout !=null)
		{
			try
			{
				//System.out.println("Checkouts="+checkout);
				checkout=checkout.substring(1,checkout.length());
				checkout=checkout.substring(0,checkout.length()-1);
				//System.out.println("Checkouts="+checkout);
				JsonParser parser = new JsonParser();
				JsonElement jsonelement = parser.parse(checkout);
				JsonObject jsonobject=jsonelement.getAsJsonObject();
				String cust_name=jsonobject.get("cust_name").getAsString();
				String cust_email=jsonobject.get("cust_email").getAsString();
				String cust_accno=jsonobject.get("cust_accno").getAsString();
				String cust_phone=jsonobject.get("cust_phone").getAsString();
				String jsonTable=(String)session.getAttribute("carts");
				//jsonTable=jsonTable.substring(1,jsonTable.length());
				//jsonTable=jsonTable.substring(0,jsonTable.length()-1);
				Gson gson=new Gson();
		//System.out.println(jsonTable);
		JsonReader reader = new JsonReader(new StringReader(jsonTable));
		reader.setLenient(true);
				//ArrayList<BookInfoS> bkinfos=gson.fromJson(reader, (BookInfoS.class));
		ArrayList<BookInfoS> bkinfos=gson.fromJson(reader, new TypeToken<List<BookInfoS>>(){}.getType());
		paymentstatus=cdaoimpl.finalizePayments(cust_name, cust_email, cust_accno, cust_phone, bkinfos);
		//System.out.println("Printing ISBNsss");
		/*for(int i=0;i<bkinfos.size();i++)
		{
			System.out.println(bkinfos.get(i).getIsbn());
		}*/
				//gson.fromJson(reader, new TypeToken<List<BookInfoS>>(){}.getType());
			
	
				/*SendEmail sndmail=new SendEmail();
				sndmail.sendmail("mukeshregmi123@gmail.com");*/
				//System.out.println("Customer Name="+cust_name);
			
			
			if(paymentstatus.equals("success"))// If Payment is Successful, then Invalidate Session. 
			{
			//session.invalidate();
				Cookie[] cookies = request.getCookies();
				for(int i=0;i<cookies.length;i++)//Delete the cookies that were previously set; only cookies for those books which had not been purchased yet will persist on buyers computer
				{
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
				Cookie cookie=new Cookie("customer",cust_email);
				cookie.setMaxAge(60*60*24*30);
				response.addCookie(cookie);
				session.removeAttribute("carts");
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			String resp="hello";
			Gson gson=new Gson();
			///////////////////////////////////////
			
			//////////////////////////////////////////////////
			/*SendEmail sndmail=new SendEmail();
			sndmail.sendmail("mukeshregmi123@gmail.com");*/
			response.setContentType("json");
			response.getWriter().write(gson.toJson(paymentstatus));
		}
		if(updatecart !=null)
		{
			
			String isbn=updatecart.trim().toString();
			int quantity=Integer.parseInt(request.getParameter("quantity").trim().toString());
			String responsetext=cdaoimpl.updateCartItem(isbn, quantity);
			Gson gson=new Gson();
			lst=cdaoimpl.returnBooksInCart();
			 lead="data:image/png;base64,";
			carts=cdaoimpl.retCarts();
			lstr=new ArrayList<BookInfoS>();
		if(lst !=null)	
		{
			for(int i=0;i<lst.size();i++)
			{
				for(int j=0;j<carts.size();j++)
				{
					
					if(lst.get(i).getIsbn().equalsIgnoreCase(carts.get(j).getIsbn()))
					{
						BookInfo b=lst.get(i);
						byte ib[]=b.getBookimage();
						byte[] img64=Base64.encodeBase64(ib);
			            String photo64 = new String(img64);
			            lead=lead+photo64;
			            System.out.println(carts.get(i).getQuantity());
			            lstr.add(new BookInfoS(b.getIsbn(),b.getTitle(),b.getAuthor(),carts.get(j).getQuantity(),carts.get(j).getPrice(),b.getImage(),lead));
			            lead="data:image/png;base64,";
			            break;
			            
					}
				}
			}
			//String jsonstr=gson.toJson(lstr);
			session.removeAttribute("carts");
			session.setAttribute("carts", gson.toJson(lstr));
			/////////////////////////////////////////////////
		response.setContentType("json");
		response.getWriter().write(gson.toJson(lstr));
		}
		else// noe there is no  more items on the cart
		{
			session.removeAttribute("carts");
			response.getWriter().write(gson.toJson("Fail"));
		}
			
			
		}
		//System.out.println("Remove Cart="+removecart);
		if(removecart !=null)
		{
			
			String isbn=removecart.trim().toString();
			System.out.println("Item to Be Deleted isbn="+isbn);
			Gson gson=new Gson();
			String responsetext=cdaoimpl.removeFromCart(isbn);
			
			 lst=cdaoimpl.returnBooksInCart();
			 lead="data:image/png;base64,";
			carts=cdaoimpl.retCarts();
			lstr=new ArrayList<BookInfoS>();
		if(lst !=null)	
		{
			for(int i=0;i<lst.size();i++)
			{
				for(int j=0;j<carts.size();j++)
				{
					
					if(lst.get(i).getIsbn().equalsIgnoreCase(carts.get(j).getIsbn()))
					{
						BookInfo b=lst.get(i);
						byte ib[]=b.getBookimage();
						byte[] img64=Base64.encodeBase64(ib);
			            String photo64 = new String(img64);
			            lead=lead+photo64;
			            System.out.println(carts.get(i).getQuantity());
			            lstr.add(new BookInfoS(b.getIsbn(),b.getTitle(),b.getAuthor(),carts.get(j).getQuantity(),carts.get(j).getPrice(),b.getImage(),lead));
			            lead="data:image/png;base64,";
			            break;
			            
					}
				}
			}
			//String jsonstr=gson.toJson(lstr);
			session.removeAttribute("carts");
			session.setAttribute("carts", gson.toJson(lstr));
			/////////////////////////////////////////////////
		response.setContentType("json");
		response.getWriter().write(gson.toJson(lstr));
		}
		else// noe there is no  more items on the cart
		{
			session.removeAttribute("carts");
			response.getWriter().write(gson.toJson("Fail"));
		}
			//response.getWriter().write(jsonstr);
		}
		if(addcarts !=null)
		{
			
			Gson gson=new Gson();
			CartInfo[] cart = gson.fromJson(addcarts, CartInfo[].class);
			
			if(cart.length >=1)
			{
				//System.out.println("Cart Data="+addcarts);
				System.out.println("Length of Itemsto beadded="+cart.length);
				if(session == null)
				{
					session=request.getSession(true);
				}
				else
				{
					session=request.getSession(false);
				}
				if(session.getAttribute("carts") == null)//for first items added in the cart
				{
					cdaoimpl.addtoCart(cart,false);
				}
				else
				{
					cdaoimpl.addtoCart(cart,true);  //if items are searched again to be added on the cart
				}
				
				List<BookInfo> lst=cdaoimpl.returnBooksInCart();
				 lead="data:image/png;base64,";
				List<CartInfo> carts=cdaoimpl.retCarts();
				lstr=new ArrayList<BookInfoS>();
				//System.out.println("Retrieval Time Quantity Infos");
				/*for(int i=0;i<carts.size();i++)
				{
					
					System.out.println("ISBN="+carts.get(i).getIsbn()+",Quantity="+carts.get(i).getQuantity());
				}*/
				System.out.println("\nLst Sizeeeee="+lst.size());
				System.out.println("Cart Sizeeee="+carts.size());
			
				for(int i=0;i<lst.size();i++)
				{
					for(int j=0;j<carts.size();j++)
					{
						
						if(lst.get(i).getIsbn().equalsIgnoreCase(carts.get(j).getIsbn()))
						{
							BookInfo b=lst.get(i);
							byte ib[]=b.getBookimage();
							byte[] img64=Base64.encodeBase64(ib);
				            String photo64 = new String(img64);
				            lead=lead+photo64;
				            System.out.println(carts.get(i).getQuantity());
				            lstr.add(new BookInfoS(b.getIsbn(),b.getTitle(),b.getAuthor(),carts.get(j).getQuantity(),carts.get(j).getPrice(),b.getImage(),lead));
				            lead="data:image/png;base64,";
				            break;
				            
						}
					}
				}
				String jsonstr=gson.toJson(lstr);
				System.out.println("Returned Book Size="+lstr.size());
				String str="asda";
				///response.getWriter().write("true");
				for(int i=0;i<carts.size();i++)
				{
	              Cookie cookie=new Cookie(carts.get(i).getIsbn(),"isbn");
	              cookie.setComment("ekatabookstore");
	              cookie.setMaxAge(24*60*60*30);
	              response.addCookie(cookie);
				}
				
				if(session != null) 
				{
				  session.setAttribute("carts", jsonstr);
				}
				  view_name="/views/cart/checkOut.jsp";
				  
				  RequestDispatcher view=request.getRequestDispatcher(view_name);
				  view.forward(request, response);
			}
			
		}
	}

}
