package com.mvc.controller;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Convert;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mvc.dao.EkataDAOImpl;
import com.mvc.model.BookInfo;
import com.mvc.model.BookInfoS;

import  org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;




/**
 * Servlet implementation class AdminController
 */
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       EkataDAOImpl daoimpl;
       byte[] imageByte;
       List<BookInfoS> lstr;
       HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
        daoimpl=new EkataDAOImpl();
        
        System.out.println("Inside AdminController");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Get Method Called");
		String view_name="";
		try
		{
			String savedata=request.getParameter("savedata");
			String imgdata=request.getParameter("pdata");
			String tabledata=request.getParameter("tabledata");
			String action=request.getParameter("action");
			if(action!=null)
			{
				if(action.equalsIgnoreCase("delete"))
				{
					//view_name="/ekataBookStore/views/display.jsp";
					view_name="/views/display.jsp";
					String isbn=request.getParameter("isbn").trim();
					request.setAttribute("status",daoimpl.deleteRecord(isbn));
					//response.sendRedirect(view_name);
					
				}
				if(action.equalsIgnoreCase("edit"))
				{
					//view_name="/ekataBookStore/views/display.jsp";
					String lead="data:image/png;base64,";
					view_name="/views/editRecordForm.jsp";
					String isbn=request.getParameter("isbn").trim();
					BookInfo bi=daoimpl.getBook(isbn);
					//System.out.println("ISBN from request="+isbn);
					//System.out.println("ISBN from Database="+isbn);
					byte ib[]=bi.getBookimage();
					byte[] img64=Base64.encodeBase64(ib);
		            String photo64 = new String(img64);
		            lead=lead+photo64;
					BookInfoS bis=new BookInfoS(bi.getIsbn(),bi.getTitle(),bi.getAuthor(),bi.getQuantity(),bi.getPrice(),bi.getImage(),lead);
					request.setAttribute("book", bis);
					/*String isbn=request.getParameter("isbn").trim();
					request.setAttribute("status",);*/
					//response.sendRedirect(view_name);
					
				}
				if(action.equalsIgnoreCase("displaymainpage"))
				{
					session=request.getSession(false);
					if(session == null)
					{
						view_name="/views/Error.jsp";
					}
					else
					{
						int userid=(Integer)session.getAttribute("userid");
						if(String.valueOf(userid)==null)
						{
							view_name="/views/Error.jsp";
						}
						else
						{
							view_name="/views/AddItem.jsp";
						}
					}
					
				}
				RequestDispatcher view=request.getRequestDispatcher(view_name);
				view.forward(request, response);
				
				
			}
			if(tabledata!=null)
			{
				//System.out.println("Hi,I am inside tabledata");
				Gson gson=new Gson();
				String lead="data:image/png;base64,";
				List<BookInfo> lst=daoimpl.retAllBooks();
				lstr=new ArrayList();
				
				for(int i=0;i<lst.size();i++)
				{
					BookInfo b=lst.get(i);
					byte ib[]=b.getBookimage();
					byte[] img64=Base64.encodeBase64(ib);
		            String photo64 = new String(img64);
		            lead=lead+photo64;
		            lstr.add(new BookInfoS(b.getIsbn(),b.getTitle(),b.getAuthor(),b.getQuantity(),b.getPrice(),b.getImage(),lead));
		            lead="data:image/png;base64,";
					//lst.get(0).setBookimage(bookimage);
				}
				//String jsonstr=gson.toJson(lst);
				String jsonstr=gson.toJson(lstr);
				//System.out.println("JSOn String---->"+jsonstr);
				response.setContentType("json");
				//System.out.println(new Gson().toJson(data));
		        //response.getWriter().write(new Gson().toJson(lst));
		       
				//System.out.println("JSON TABLE String==>>"+jsonstr);
				 response.getWriter().write(jsonstr);
			}
			//System.out.println("Value of Img Data="+imgdata);
			if(imgdata != null)
			{
				System.out.println("JSON Image Data="+imgdata);
				JsonParser parser = new JsonParser();
				JsonElement jsonelement = parser.parse(imgdata);
				JsonObject jsonobject=jsonelement.getAsJsonObject();
				String actimg=jsonobject.get("imgdata").getAsString();
				System.out.println("Actual Image String="+actimg);
				//byte[] decodedByte = Base64.decode(actimg, 0); 
				StringTokenizer tokens=new StringTokenizer(actimg,",");
				String lead=tokens.nextToken();
				lead=lead+",";
				System.out.println("Lead Val="+lead);
				String imgstr=tokens.nextToken();
				System.out.println("Next Token="+imgstr);
				BASE64Decoder decoder = new BASE64Decoder();
				imageByte = decoder.decodeBuffer(imgstr);
				Blob blob = new javax.sql.rowset.serial.SerialBlob(imageByte);
				//int l=(Integer)blob.length();
				long pos=Long.parseLong("1");
				Long length=blob.length();
		        //Integer.parseInt(length.toString());
				//int length=Integer.pars)
				byte[] ba = blob.getBytes(Long.parseLong("1"), Integer.parseInt(length.toString())); 
		       
	              // b.length() throws exception, no message, no cause
	             // byte[] img64 = Base64.encode(ba);
	              byte[] img64=Base64.encodeBase64(ba);
	            
	              String photo64 = new String(img64);
	              System.out.println("Decoded base 64 image="+photo64);
	              photo64=lead+photo64;
	              System.out.println("Returned val="+photo64);


				
				response.getWriter().write(photo64);
				//response.getWriter().write(actimg);
				//String eid=jsonobject.get("empid").toString().trim();
				//int empid=jsonobject.get("empid").getAsInt();
			}
			if(savedata != null)
			{
				Gson gson=new GsonBuilder().create();
				BookInfo b=gson.fromJson(savedata, BookInfo.class);
				//System.out.println("ID="+p.getEmpid()+"-Name="+p.getEmpname()+"-Address="+p.getEmpaddress()+"-Email="+p.getEmpmail());
				String savestatus=daoimpl.addBookDetails(b);
				response.getWriter().write(savestatus);
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
		//System.out.println("Get Method Called");
		String view_name="";
		try
		{
			String updatedata=request.getParameter("updatedata");
			String savedata=request.getParameter("savedata");
			String pdata=request.getParameter("pdata");
			if(updatedata!=null)
			{
				JsonParser parser = new JsonParser();
				JsonElement jsonelement = parser.parse(updatedata);
				JsonObject jsonobject=jsonelement.getAsJsonObject();
				String actimg=jsonobject.get("bookimage").getAsString();
				StringTokenizer tokens=new StringTokenizer(actimg,",");
				String lead=tokens.nextToken();
				String imgstr=tokens.nextToken();
				BASE64Decoder decoder = new BASE64Decoder();
				imageByte = decoder.decodeBuffer(imgstr);
				Blob blob = new javax.sql.rowset.serial.SerialBlob(imageByte);
				long pos=Long.parseLong("1");
				Long length=blob.length();
				byte[] ba = blob.getBytes(Long.parseLong("1"), Integer.parseInt(length.toString())); 
				System.out.println("Image size in Bytes="+ba.length);
				String isbn=jsonobject.get("isbn").getAsString();
				String title=jsonobject.get("title").getAsString();
				String author=jsonobject.get("author").getAsString();
				int quantity=jsonobject.get("quantity").getAsInt();
				float price=jsonobject.get("price").getAsFloat();
				String image=jsonobject.get("image").getAsString();
				BookInfo b=new BookInfo(isbn,title,author,quantity,price,image,ba);
				String updatestatus=daoimpl.editRecord(b);
				response.getWriter().write(updatestatus);
			}
			if(pdata!=null)
			{
				JsonParser parser = new JsonParser();
				JsonElement jsonelement = parser.parse(pdata);
				JsonObject jsonobject=jsonelement.getAsJsonObject();
				String actimg=jsonobject.get("imgdata").getAsString();
				//System.out.println("Actual Image String="+actimg);
				//byte[] decodedByte = Base64.decode(actimg, 0); 
				StringTokenizer tokens=new StringTokenizer(actimg,",");
				String lead=tokens.nextToken();
				lead=lead+",";
				System.out.println("Lead Val="+lead);
				String imgstr=tokens.nextToken();
				//System.out.println("Next Token="+imgstr);
				BASE64Decoder decoder = new BASE64Decoder();
				imageByte = decoder.decodeBuffer(imgstr);
				Blob blob = new javax.sql.rowset.serial.SerialBlob(imageByte);
				//int l=(Integer)blob.length();
				long pos=Long.parseLong("1");
				Long length=blob.length();
		        byte[] ba = blob.getBytes(Long.parseLong("1"), Integer.parseInt(length.toString())); 
				System.out.println("Image size in Bytes="+ba.length);
	              byte[] img64=Base64.encodeBase64(ba);
	              String photo64 = new String(img64);
	              photo64=lead+photo64;
				  response.getWriter().write(photo64);
				
			}
			//System.out.println("Value of Img Data="+imgdata);
			if(savedata != null)
			{
				//System.out.println("JSON Image Data="+imgdata);
				JsonParser parser = new JsonParser();
				JsonElement jsonelement = parser.parse(savedata);
				JsonObject jsonobject=jsonelement.getAsJsonObject();
				String actimg=jsonobject.get("bookimage").getAsString();
				//System.out.println("Actual Image String="+actimg);
				//byte[] decodedByte = Base64.decode(actimg, 0); 
				StringTokenizer tokens=new StringTokenizer(actimg,",");
				String lead=tokens.nextToken();
				//lead=lead+",";
				//System.out.println("Lead Val="+lead);
				String imgstr=tokens.nextToken();
				//System.out.println("Next Token="+imgstr);
				BASE64Decoder decoder = new BASE64Decoder();
				imageByte = decoder.decodeBuffer(imgstr);
				Blob blob = new javax.sql.rowset.serial.SerialBlob(imageByte);
				//int l=(Integer)blob.length();
				long pos=Long.parseLong("1");
				Long length=blob.length();
		        //Integer.parseInt(length.toString());
				//int length=Integer.pars)
				byte[] ba = blob.getBytes(Long.parseLong("1"), Integer.parseInt(length.toString())); 
				System.out.println("Image size in Bytes="+ba.length);
				
				String isbn=jsonobject.get("isbn").getAsString();
				String title=jsonobject.get("title").getAsString();
				String author=jsonobject.get("author").getAsString();
				int quantity=jsonobject.get("quantity").getAsInt();
				float price=jsonobject.get("price").getAsFloat();
				String image=jsonobject.get("image").getAsString();
				BookInfo b=new BookInfo(isbn,title,author,quantity,price,image,ba);
				String savestatus=daoimpl.addBookDetails(b);
				response.getWriter().write(savestatus);
				
	              // b.length() throws exception, no message, no cause
	             // byte[] img64 = Base64.encode(ba);
	              //byte[] img64=Base64.encodeBase64(ba);
	            
	              //String photo64 = new String(img64);
	              //System.out.println("Decoded base 64 image="+photo64);
	              //photo64=lead+photo64;
	              //System.out.println("Returned val="+photo64);


				
				//response.getWriter().write(photo64);
				//response.getWriter().write(actimg);
				//String eid=jsonobject.get("empid").toString().trim();
				//int empid=jsonobject.get("empid").getAsInt();
			}
			/*if(savedata != null)
			{
				Gson gson=new GsonBuilder().create();
				BookInfo b=gson.fromJson(savedata, BookInfo.class);
				//System.out.println("ID="+p.getEmpid()+"-Name="+p.getEmpname()+"-Address="+p.getEmpaddress()+"-Email="+p.getEmpmail());
				String savestatus=daoimpl.addBookDetails(b);
				response.getWriter().write(savestatus);
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
