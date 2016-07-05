<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>FINAL CHECKOUT</title>
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
 </head>
 
     <body>
    <!-- header_top -->
    <div class="top_bg">
    <div class="container">
    <div class="header_top">
        <div class="top_left">
            <h2><a href="#">20%off</a> You Can Get 20% Discount on Item Listed on Right Side of Page </h2>
        </div>
        <div class="top_right">
            <ul>
                <li><a href="contact.jsp">Contact</a></li>|
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
                        <li><a class="color10" href="contact.html">Contact</a>
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
	<div align="center" class="header_right_message">
         <br>
         	<h1> Transaction is UnsuccessFul !</h1>
			<br>
			<p>Reason is: <%= request.getParameter("errmsg") %></p>
			<br>
			<p> <a href='/ekataBookStore/CartController?action=displayMainPage'>Click Here to Shop Again</a></p></h2>

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
