<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>ADMIN PANEL</title>
    <script src="/ekataBookStore/views/js/jquery.js" type="text/javascript"></script>
	<script src="/ekataBookStore/views/js/ajax.js" type="text/javascript"></script>
    <link rel="icon" href="../views/images/favicon.ico" type="image/x-icon">
	<!-- BOOTSTRAP STYLES-->
    <link href="../views/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="../views/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="../views/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/ekataBookStore/views/AddItem.jsp">EKATABOOKSTORE</a> 
            </div>
  <div style="color: white;
padding: 15px 50px 5px 50px;
float: right;
font-size: 16px;"> Last access : 05 April 2016 &nbsp; <a href="/ekataBookStore/views/logout.jsp" class="btn btn-danger square-btn-adjust">Logout</a> </div>
        </nav>   
           <!-- /. NAV TOP  -->
                <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
				<li class="text-center">
                    <img src="../views/images/find_user.png" class="user-image img-responsive"/>
					</li>
				
					
                    <li>
                        <a class="active-menu"  href="/ekataBookStore/views/AddItem.jsp"><i class="fa fa-dashboard fa-5x"></i> Dashboard</a>
                    </li>
                                         			
					 <li  >
                        <a   href="/ekataBookStore/views/LogInForm.jsp"><i class="fa fa-bolt fa-5x"></i> Login</a>
                    </li>	
                     <li  >
                        <a   href="#"><i class="fa fa-laptop fa-5x"></i> Registeration</a>
                    
                </ul>
               
            </div>
            
        </nav>  
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2>Admin Dashboard</h2>   
                        <h5>Welcome <b><%= session.getAttribute("username") %></b> , Love to see you back. </h5>
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr />
                <div class="row">
                <div>
<div align="center">
<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
<jsp:forward page="/views/Error.jsp" />
<%} %>


<h3 align:"center">Book Detail Record Form</h3>
<table>
<tr>
<td>
<table>
<tr>
<td>ISBN</td><td><input type="text" name="isbn" id="isbn"></td> <td><input type="hidden" id="hiddenisbn" name="hiddenisbn" style="color:red;display:inline;float:left;background-color: transparent;border: 0px solid;" disabled></td>
</tr>
<tr>
<td>Title</td><td><input type="text" name="title" id="title"></td><td><input type="hidden" id="hiddentitle" name="hiddentitle" style="color:red;display:inline;float:left;background-color: transparent;border: 0px solid;" disabled></td>
</tr>
<tr>
<td>Author</td><td><input type="text" name="author" id="author"></td><td><input type="hidden" id="hiddenauthor" name="hiddenauthor" style="color:red;display:inline;float:left;background-color: transparent;border: 0px solid;" disabled></td>
</tr>
<tr>
<td>Quantity</td><td><input type="text" name="quantity" id="quantity"></td><td><input type="hidden" id="hiddenquantity" name="hiddenquantity" style="color:red;display:inline;float:left;background-color: transparent;border: 0px solid;" disabled></td>
</tr>
<tr>
<td>Price</td><td><input type="text" name="price" id="price"></td><td><input type="hidden" id="hiddenprice" name="hiddenprice" style="color:red;display:inline;float:left;background-color: transparent;border: 0px solid;" disabled></td>
</tr>
<tr>
<td>Image</td><td><input type="text" name="image" id="image" disabled></td><td><input type="hidden" id="hiddenimage" name="hiddenimage" style="color:red;display:inline;float:left;background-color: transparent;border: 0px solid;" disabled></td>
<td><input type="file" id="FileToUpload" name="FileToUpload" /> </td>
</tr>
<tr>
<td><input type="SUBMIT" value="SAVE" id="saveButton" ></td><td><input type="SUBMIT" value="CLEAR" id="clearButton"></td>
</tr>
</table>
</td>
<td><div id="image-holder"></div></td><td><img id="img1" width="200" height="200" /></td>
</tr></table>


</div>
<div id="ajaxResponse">

</div>

<br></br>

<div id="tableResponse"></div>
</div>
			</div>
                 <!-- /. ROW  -->         
    </div>
             <!-- /. PAGE INNER  -->
            </div>
         <!-- /. PAGE WRAPPER  -->
        </div>
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="../views/js/bootstrap.min.js"></script>
    
      <!-- CUSTOM SCRIPTS -->
    <script src="../views/js/custom.js"></script>
    
   
</body>
</html>
