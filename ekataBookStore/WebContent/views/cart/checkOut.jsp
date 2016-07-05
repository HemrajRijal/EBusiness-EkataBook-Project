        <%@ page import="java.util.*" %>
        <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>CHECKOUT</title>
         <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
         <link rel="icon" href="views/images/favicon.ico" type="image/x-icon">
        
		<script src="/ekataBookStore/views/js/jquery.js" type="text/javascript"></script>
		<script src="/ekataBookStore/views/js/modernizr.custom.29473.js" type="text/javascript"></script>
        <script src="/ekataBookStore/views/js/cartajax.js" type="text/javascript"></script>        
        <script src="/ekataBookStore/views/js/ajax.js" type="text/javascript"></script>
        <script src="/ekataBookStore/views/js/loadtableajax.js" type="text/javascript"></script>
        <script src="/ekataBookStore/views/js/megamenu.js" type="text/javascript"></script>
        <link rel="stylesheet" href="/ekataBookStore/views/css/main.css">
        <link rel="stylesheet" href="/ekataBookStore/views/css/style.css">
        <link rel="stylesheet" href="/ekataBookStore/views/css/megamenu.css">
        <link rel="stylesheet" href="/ekataBookStore/views/css/accstyle.css">
        <script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<script>
$(document).ready(function() { 
		$('body').on('click', '#updatebtn', function () {
		var isbn=$(this).attr('name');
		var text='#qty'+isbn;
		var quantity=$('#qty'+isbn).val();
		$.ajax({
		     type: 'post',
		     url: '/ekataBookStore/CartController',
		     data: {updatecart:isbn,quantity:quantity},
		     dataType: 'json',
		     async: false,
		     success: function (data) {
		    	 try
				  {
		    		 if(data == "Fail")
		    			 {
		    			 $("#ajaxResponse").html('');
		    			    $("#ajaxResponse").empty();
		    			    $("#ajaxResponse").html("<p><a href='/ekataBookStore/CartController?action=displayMainPage'>Select More Books...</a></p>");
		    			 }
		    		 else
		    			 {
		    			 displayPage(data);
		    			 }
		    		 
				  }
				  catch(err)
				  {
					  alert(err);
				  }
		     }
		    
		     ,error: function(xhr,textStatus,err)
		     {
		    	
		    	   alert("readyState: " + xhr);
		    	    alert("responseText: "+ xhr.responseText);
		    	    alert("status: " + xhr.status);
		    	    alert("text status: " + textStatus);
		    	    alert("error: " + err);
		    	}
		   });
	});
	function redirectPage(url,data)
	{
		$(location).attr('href',url+"?"+"finalcheckout="+data);
		
		
		
    }
	/////////////////////////////////////////////////////////////////////
	$('body').on('click', '#checkoutbtn', function () {
		var cust_name=$('#cust_name').val();
		var cust_email=$('#cust_email').val();
		var cust_accno=$('#cust_accno').val();
		var cust_phone=$('#cust_phone').val();
		var slct = [];
		slct.push({
	        cust_name: cust_name,
	        cust_email:cust_email,
	        cust_accno:cust_accno,
	        cust_phone:cust_phone
	    });
		redirectPage('/ekataBookStore/views/cart/finalCheckOut.jsp',JSON.stringify(slct));
		
	}); 
	
	/////////////////////////////////////////////
	$('body').on('click', '#removebtn', function () {
		var isbn=$(this).attr('name');
		$.ajax({
		     type: 'post',
		     url: '/ekataBookStore/CartController',
		     data: {removecart:isbn},
		     dataType: 'json',
		     async: false,
		     success: function (data) {
		    	 try
				  {
		    		 console.log(data);
		    		 if(data == "Fail")
		    			 {
		    			 $("#ajaxResponse").html('');
		    			    $("#ajaxResponse").empty();
		    			    $("#ajaxResponse").html("<p><a href='/ekataBookStore/CartController?action=displayMainPage'>Select More Books...</a></p>");
		    			 }
		    		 else
		    			 {
		    			 displayPage(data);
		    			 }
		    		 
				  }
				  catch(err)
				  {
					  alert(err);
				  }
		     }
		    
		     ,error: function(xhr,textStatus,err)
		     {
		    	
		    	   alert("readyState: " + xhr);
		    	    alert("responseText: "+ xhr.responseText);
		    	    alert("status: " + xhr.status);
		    	    alert("text status: " + textStatus);
		    	    alert("error: " + err);
		    	}
		   });
	}); 
});  
function displayPage(data)
{
	try
	{
	
   
	
   var tbl_body = "<table border='1' cellpadding='6'> <thead> <th>TITLE</th><th>Author</th><th>QTY</th><th>Price</th><th>Book Image</th><th>REMOVE</th></thead>";
    var odd_even = false;
    var tbl_row = "";
    var tot=0;
    var q=1;
    var p=1;
    $.each(data, function() {
         tbl_row = "";
        var isbnkey="";
        var isbnval="";
        $.each(this, function(k , v) {
        	if(k == "isbn")
            	{
            	  isbnval=v;
            	}
        	if(k != "bookimage" && k!="isbn" && k!="quantity" && k!="image" )
            	{
        	         tbl_row += "<td>"+v+"</td>";
        	         if(k=="price")
	            		{
	            		  p=v;
	            		}
            	}
            else
            	{
            		if(k=="quantity")
            		{
            			tbl_row +="<td><input type='text' size='3' value="+ v +" id='qty" + isbnval + "' /><input type='submit' value='UPDATE' id='updatebtn' name='" + isbnval + "' /></td>";
            			q=v;
            		}
            		
            		if(k=="bookimage")
            	    {
            			tbl_row += "<td><img src='"+v+"' width='50' height='50'/>"+"</td>";
            	    }
            	         
            	}
        })
         tbl_row +="<td><input type='submit' value='REMOVE' id='removebtn'  name='" + isbnval + "' /></td>";
        tbl_body += "<tr>"+tbl_row+"</tr>";
        tot=tot+(q*p);
                   
    })
    tbl_row = "<td></td>"; 
    tbl_row += "<td></td>"; 
    tbl_row += "<td></td>"; 
    tbl_row += "<td>Total Price:"+ tot + "</td>";
     tbl_body += "<tr>"+tbl_row+"</tr>";
    tbl_body=tbl_body + " </table>";
    tot=0;
  
  tbl_body=tbl_body + "<p><a href='/ekataBookStore/CartController?action=displayMainPage'>Select More Books...</a></p>"; 
  tbl_body=tbl_body + "<br /><br />";
  tbl_body=tbl_body + "<h4>Please fill in your particular before checking out:</h4>";
  tbl_body=tbl_body + "<table>";
  tbl_body=tbl_body + "<tr>";
  tbl_body=tbl_body + "<td>Enter your Name:</td>";
  tbl_body=tbl_body + "<td><input type='text' name='cust_name' id='cust_name' /></td></tr>";
  tbl_body=tbl_body + "<tr>";
  tbl_body=tbl_body + "<td>Enter your Email:</td>";
  tbl_body=tbl_body + "<td><input type='text' name='cust_email' id='cust_email' /></td></tr>";
  tbl_body=tbl_body + "<tr>";
  tbl_body=tbl_body + "<tr>";
  tbl_body=tbl_body + "<td>Enter your Bank Account Number:</td>";
  tbl_body=tbl_body + "<td><input type='text' name='cust_accno' id='cust_accno' /></td></tr>";
  tbl_body=tbl_body + "<tr>";
  tbl_body=tbl_body + "<td>Enter your Phone Number:</td>";
  tbl_body=tbl_body + "<td><input type='text' name='cust_phone' id='cust_phone' /></td></tr>";
    tbl_body=tbl_body + "</table>";
  tbl_body=tbl_body + "<input type='submit' value='CHECK OUT' id='checkoutbtn'>";
  
    $("#ajaxResponse").html('');
    $("#ajaxResponse").empty();
    $("#ajaxResponse").html(tbl_body);
	}
	catch(error)
	{
		alert(error);
	}
}
</script>
 </head>
 
     <body onload='displayPage(<%= request.getSession(false).getAttribute("carts") %>);'>
    <!-- header_top -->
    <div class="top_bg">
    <div class="container">
    <div class="header_top">
        <div class="top_left">
            <h2><a href="#">20%off</a> You Can Get 20% Discount on Item Listed on Right Side of Page </h2>
        </div>
        <div class="top_right">
            <ul>
                <li><a href="/ekataBookStore/views/cart/contact.jsp">Contact</a></li>|
                <li><a href="/ekataBookStore/views/LogInForm.jsp">Log In</a>    </li>
            </ul>
        </div>
        <div class="clearfix"> </div>
    </div>
    </div>
    </div>
    <!-- header -->
    <div class="header_bg">
    <div class="container">
        <div class="header">
            <div class="logo">
                <a href="/ekataBookStore/CartController?action=displayMainPage"><img src="../images/logo.png" alt=""/> </a>
            </div>
            <!-- start header_right -->
            <div class="header_right">
            <div class="create_btn">
                <a class="arrow"  href="#">create account <img src="../images/right_arrow.png" alt=""/>  </a>
            </div>
            <ul class="icon1 sub-icon1 profile_img">
                <li><a class="active-icon c2" href="/ekataBookStore/views/cart/checkOut.jsp"> </a>
                    <ul class="sub-icon1 list">
                        <li><h3>View Your Cart</h3><a href=""></a></li>
                        <li><p>Tap Icon Above to View Your Cart</p></li>
                    </ul>
                </li>
            </ul>
           
           
            <div class="clearfix"> </div>
            </div>
    
            <!-- start header menu -->
            <ul class="megamenu skyblue">
                    <li><a class="color1" href="/ekataBookStore/CartController?action=displayMainPage">HOME</a></li>
                    <li class="grid"><a class="color2" href="#">BOOK</a></li>
                    <li class="active grid"><a class="color4" href="#">EBOOK</a></li>				
                    <li><a class="color5" href="#">TEEN</a></li>
                        <li><a class="color6" href="#">KIDS</a></li>
                        <li><a class="color7" href="#">TECH</a></li>
                        <li><a class="color8" href="#">MAGZINES</a></li>
                        <li><a class="color9" href="#">MOVIES & MUSIC</a></li>
                        <li><a class="color10" href="/ekataBookStore/views/cart/contact.jsp">Contact</a>
                            <div class="megapanel">
                                <div class="row">
                                    <div class="col3">
                                        <div class="h_nav">
                                            <h4>contact us</h4>
                                        </div>
                                        <form class="contact">
                                            <label for="name">Name</label>
                                            <input id="name" type="text"/>
                                            <label for="email">E-mail</label>
                                            <input id="email" type="text"/>
                                            <label for="message">Message</label>
                                            <textarea rows="8" id="message"></textarea>
                                            <input type="submit" value="Send"/>
                                        </form>
                                    </div>
                                    <div class="col3">
                                    </div>
                                </div>
                            </div>
                        </li>
                 </ul> 
        </div>
        
         <div class="header_bottom_left">
         <br>
         	<div class="header_right_content_chkout" >
				<%String data=(String)session.getAttribute("carts");%>    		
<div id="ajaxResponse"></div>
<div id="editupdateajaxResponse"></div>

		</div>
<div class="category">
 				<section class="ac-container">
				<div class="p">
					<input id="ac-1" name="accordion-1" type="checkbox" />
					<label for="ac-1">About us</label>
					<article class="ac-large">
						<p>Ekta Books has a 33 year long experience in the book publishing industry. 
	It is one among the line of a few book publishers that have successfully completed a long journey.
		From its humble roots in Patan, Ekta Books has come a long way to being the best in the publishing sector. 
	Its quality in both material and content in textbooks is unrivalled. </p>
					</article>
				</div>
				<div >
					<input id="ac-2" name="accordion-1" type="checkbox" />
					<label for="ac-2">Ekata Associates</label>
					<article class="ac-large">
						<p>Sister Organisations<br>
						Ekta Educational Palace, Man Bhawan Lalitpur<br>
						Ekta Book House Pvt. Ltd., Pradhan Nagar, Siligari, India<br>
						Timothy Publishing House Pvt. Ltd, Gandhi Nagar, Jalpaigadi, India <br>
						Branches<br>
						Ekta Books Distributors, Lamachaur, Pokhara 
						</p>
					</article>
				</div>
				<div>
					<input id="ac-3" name="accordion-1" type="checkbox" />
					<label for="ac-3">ShowRoom</label>
					<article class="ac-large">
						<p>Ekta Books showroom is the largest educational store in Nepal. 
						Located at the heart of Kathmandu, Thapathali, the showroom has an extensive collection of more than 130,000 titles ranging from Rs 15 to Rs. 1, 01,600 of which 80 percent are general subjects. 
						With spacious parking facility, Ekta showroom is a book lover's paradise providing access to treasury of infinite knowledge. </p>
					</article>
				</div>
				<div>
					<input id="ac-4" name="accordion-1" type="checkbox" />
					<label for="ac-4">Contact us</label>
					<article class="ac-small">
						<p>Corporate Office
							Thapathali, Kathmandu<br>
							Phone: 4245787, 4230729<br>
							Office : (+977-1) 4260482, 4262091<br>
							Email : ektabook@mos.com.np
							 </p>
					</article>
				</div>
			</section>
        </div>
        </div>
	
	</div>

  
   
	
    <!-- footer_top -->
    <div class="footer_top">
    
      </div>
    
    <!-- footer -->
    <div class="footer">
     <div class="container">
        <div class="copy">
            <p class="link">&copy; All rights reserved | Design by&nbsp; Hemraj Rijal</p>
        </div>
     </div>
    </div>
    </body>
        </html>