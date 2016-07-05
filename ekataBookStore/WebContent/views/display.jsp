<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Operation Status Display</title>

<script src="/ekataBookStore/views/js/jquery.js" type="text/javascript"></script>
	<script src="/ekataBookStore/views/js/ajax.js" type="text/javascript"></script>
    <link rel="icon" href="views/images/favicon.ico" type="image/x-icon">
	<!-- BOOTSTRAP STYLES-->
    <link href="views/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="../views/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="views/css/custom.css" rel="stylesheet" />
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
                    <img src="views/images/find_user.png" class="user-image img-responsive"/>
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
               <%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
<jsp:forward page="/views/Error.jsp" />
<%} %>
<!--  
<%=request.getContextPath()%>
-->
<p><%= request.getAttribute("status") %></p>
<p>Following are the Database Content</p>
<fieldset>
<legend>Main Table </legend>
<div id="tableResponse"></div>
</fieldset>
<a href="/ekataBookStore/views/AddItem.jsp">Click Here to Return Back to Record Add Page</a>

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
    <script src="../views/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="../views/js/bootstrap.min.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="../views/js/jquery.metisMenu.js"></script>
     <!-- MORRIS CHART SCRIPTS -->
     
      <!-- CUSTOM SCRIPTS -->
    <script src="../views/js/custom.js"></script>
    
   
</body>
</html>
