$(window).load(function(){  
		//initialize after images are loaded  
	 // alert("${pageContext.request.contextPath}");
   });
$(document).ready(function() { 
	displayTable();
	
	function displayTable()
	{
		var requestpath=$(location).attr('pathname');
		  var rpath=requestpath.substring(requestpath.lastIndexOf("/")+1);
		  var ppath="./Additem.jsp"
		  if(rpath == "AddItem.jsp")
			  {
			    ppath="../AdminController";
			  }
		  if(rpath=="AdminController")
			  {
			    ppath="AdminController";
			  }
		try
		{
		$.get(ppath, {
			
			   tabledata:  "displayTable",
				dataType: 'json'
				}, function(data) {
					  try
					  {
					    var tbl_body = "<table border='1' cellspacing='5'> <thead> <th>ISBN</th><th>TITLE</th><th>Author</th><th>Quantity</th><th>Price</th><th>File Name</th><th>Book Image</th><th>Edit Record</th><th>Delete Record</th> </thead>";
					    var odd_even = false;
					    $.each(data, function() {
					        var tbl_row = "";
					        var isbnkey="";
					        var isbnval="";
					        $.each(this, function(k , v) {
					            if(k == "isbn")
					            	{
					            	  isbnval=v;
					            	}
					        	if(k != "bookimage" )
					            	{
					        	tbl_row += "<td>"+v+"</td>";
					            	}
					            else
					            	{
					            	tbl_row += "<td><img src='"+v+"' width='50' height='50'/>"+"</td>";
					            	}
					        })
					       tbl_row += "<td><a href=" +"/ekataBookStore/AdminController?action=edit&isbn='"+ isbnval +"'"+ ">Edit</a</td>";
					        tbl_row += "<td><a href=" +"/ekataBookStore/AdminController?action=delete&isbn='"+ isbnval +"'"+ ">Delete</a</td>";
					        tbl_body += "<tr>"+tbl_row+"</tr>";
					                   
					    })
					    tbl_body=tbl_body + " </table>";
					  }
					  catch(err)
					  {
						  alert(err);
					  }
					    $("#tableResponse").html('');
					    $("#tableResponse").empty();
					    $("#tableResponse").html(tbl_body);
					
				
				
				
				});   
		}
		catch(err)
		{
			alert(err)
		}
	}
	
	$('#FileToUpload').change(function () {
		var countFiles = $(this)[0].files.length;

	     var imgPath = $(this)[0].value;
	     var startIndex = (imgPath.indexOf('\\') >= 0 ? imgPath.lastIndexOf('\\') : imgPath.lastIndexOf('/'));
	 	var filename = imgPath.substring(startIndex);
	 	if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
	 		filename = filename.substring(1);
	 	}
	     $("#image").val(filename);
	     var extn = imgPath.substring(imgPath.lastIndexOf('.') + 1).toLowerCase();
	     var image_holder = $("#image-holder");
	     var myDiv = document.createElement('div');
	     myDiv.id = 'myDiv';
	     document.body.appendChild(myDiv);
	     image_holder.empty();

	     if (extn == "gif" || extn == "png" || extn == "jpg" || extn == "jpeg") {
	         if (typeof (FileReader) != "undefined") {

	             for (var i = 0; i < countFiles; i++) {

	                 var reader = new FileReader();
	                 reader.onload = function (e) {
	                	 var element = document.getElementById("image-holder");
	                	 try
	                	 {
	                	 var para = document.createElement("p");
	                	 para.setAttribute("id", "parag"); 
	                	 var x=document.createElement("IMG");
	                	 x.setAttribute("src", e.target.result); 
	                	 x.setAttribute("id", "bookingimg"); 
	                	 x.setAttribute("class", "thumb-image"); 
	                	 x.setAttribute("width", "200"); 
	                	 x.setAttribute("height", "200"); 
	                	 var node = document.createTextNode("Book to Be Added");
	                	 para.appendChild(node);
	                	 element.appendChild(para);
	                	 element.appendChild(x);
	                	 
	                	 }
	                	 catch(errr)
	                	 {
	                		 alert(errr);
	                	 }
	                 }

	                 image_holder.show();
	                
	                 reader.readAsDataURL($(this)[0].files[i]);
	             }

	         } else {
	             alert("This browser does not support FileReader.");
	         }
	     } else {
	         alert("Pls select only images");
	     }
	});
	$('#sendData').click(function(){
		try
		{
		var imgElem = document.getElementById('img');
		var bookimgElem = document.getElementById('bookingimg');
		var myImg = document.querySelector("#bookingimg");
        var realWidth = myImg.naturalWidth;
        var realHeight = myImg.naturalHeight;
		var imginfo = getBase64Image(bookimgElem,realWidth,realHeight);
		var imgar ={ imgdata:imginfo   };
		var pdata=JSON.stringify(imgar);
		var pdata=JSON.stringify(pdata);
		}
		catch(err)
		{
			alert(err);
		}
		$.post('../AdminController', {
            pdata: JSON.stringify(imgar),
            dataType: 'json'
    }, function(imgText) {
    	$("#img1").attr('src',imgText);
    });
		  
		});
	/////////////////////////////
	$('#clearButton').click(function(){
		try
		{
		$('#isbn').val("");
 	   $('#title').val("");
 	   $('#author').val("");
 	   $('#quantity').val("");
 	   $('#price').val("");
 	   $('#image').val("");
 	  $('#img').attr('src', 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==');
	  $('#bookingimg').remove();
 	 $('#parag').remove();
 	$('#FileToUpload').val("");
 	$('#hiddenisbn').attr('type', 'hidden');
 	$('#hiddentitle').attr('type', 'hidden');
 	$('#hiddenauthor').attr('type', 'hidden');
 	$('#hiddenquantity').attr('type', 'hidden');
 	$('#hiddenprice').attr('type', 'hidden');
 	$('#hiddenimage').attr('type', 'hidden');
 	$('input[id="saveButton"]').prop('disabled', false);
 	
		}
		catch(error)
		{
			alert(error);
		}
		});
	//////////////////////////////
	function getBase64Image(imgElem,realWidth,realHeight) {
		    
		var canvas = document.createElement("canvas");
		canvas.width = realWidth;
	    canvas.height = realHeight;
		    try
		    {
		    var ctx = canvas.getContext("2d");
		    ctx.drawImage(imgElem, 0, 0);
		    var dataURL = canvas.toDataURL("image/png");
	       }
		    catch(err)
		    {
		      alert(err);
		    }
		    return dataURL;
		}
	
	
	
	$('#saveButton').click(function(event) {
    	if(fieldValidator())   
    		{
		var isbn=$('#isbn').val();
    	   var title=$('#title').val();
    	   var author=$('#author').val();
    	   var quantity=$('#quantity').val();
    	   var price=$('#price').val();
    	   var image=$('#image').val();
    	   var myImg = document.querySelector("#bookingimg");
           var realWidth = myImg.naturalWidth;
           var realHeight = myImg.naturalHeight;
    	   var bookimgElem = document.getElementById('bookingimg');
    	   
    	   var imginfo = getBase64Image(bookimgElem,realWidth,realHeight);
    	   var sendInfo = {isbn:isbn,title:title,author:author,quantity:quantity,price:price,image:image,bookimage:imginfo };
    	   var requestpath=$(location).attr('pathname');
   		  var rpath=requestpath.substring(requestpath.lastIndexOf("/")+1);
   		  var ppath="./Additem.jsp"
   		  if(rpath == "AddItem.jsp")
   			  {
   			    ppath="../AdminController";
   			  }
   		  if(rpath=="AdminController")
   			  {
   			    ppath="AdminController";
   			  }
    	   
            $.post(ppath, {
                    savedata:  JSON.stringify(sendInfo),
                    dataType: 'json'
            }, function(responseText) {
                    $('#ajaxResponse').text(responseText);
                    displayTable();
            });
    		}
    	else
    		{
    		$('input[id="saveButton"]').prop('disabled', true);
    		}
    	
    });
	//
	$('#updateButton').click(function(event) {
    	if(fieldValidator())   
    		{
		var isbn=$('#isbn').val();
    	   var title=$('#title').val();
    	   var author=$('#author').val();
    	   var quantity=$('#quantity').val();
    	   var price=$('#price').val();
    	   var image=$('#image').val();
    	   var myImg = document.querySelector("#bookingimg");
           var realWidth = myImg.naturalWidth;
           var realHeight = myImg.naturalHeight;
    	   var bookimgElem = document.getElementById('bookingimg');
    	   
    	   var imginfo = getBase64Image(bookimgElem,realWidth,realHeight);
    	   var sendInfo = {isbn:isbn,title:title,author:author,quantity:quantity,price:price,image:image,bookimage:imginfo };
    	   var requestpath=$(location).attr('pathname');
   		  var rpath=requestpath.substring(requestpath.lastIndexOf("/")+1);
   		  var ppath="./Additem.jsp"
   		  if(rpath == "AddItem.jsp")
   			  {
   			    ppath="../AdminController";
   			  }
   		  if(rpath=="AdminController")
   			  {
   			    ppath="AdminController";
   			  }
    	   
            $.post(ppath, {
                    updatedata:  JSON.stringify(sendInfo),
                    dataType: 'json'
            }, function(responseText) {
                    $('#ajaxResponse').text(responseText);
                    displayTable();
            });
    		}
    	else
    		{
    		$('input[id="saveButton"]').prop('disabled', true);
    		}
    	
    });
	
	$('#loginbtn').click(function(event) {
        	var username=$('#username').val();
        	   var userpwd=$('#userpwd').val();
        	   var empaddress=$('#empadd').val();
        	   var empmail=$('#empmail').val();
        	   var sendInfo = {username:username,userpwd:userpwd };
        	   var requestpath=$(location).attr('pathname');
        	   
                $.get('../LoginController', {
                        logindata:  JSON.stringify(sendInfo),
                        dataType: 'json'
                }, function(responseText) {    
                	
                	if( responseText == "true")
                		{
                		$(location).attr("href", "/ekataBookStore/views/AddItem.jsp");
                		}
                	else
                		{
                		$(location).attr("href", "/ekataBookStore/views/Error.jsp");
                		}
                	
                        
                });
        });
	function fieldValidator()
	{
		 var isbn=$('#isbn').val().trim();
		 var title=$('#title').val().trim();
	 	 var author=$('#author').val().trim();
	 	 var quantity=$('#quantity').val().trim();
	 	// alert(quantity);
	 	 var price= $('#price').val().trim();
	 	 var image= $('#image').val().trim();
		if(isbn =="")
			{
              $('#hiddenisbn').attr('type', 'text');
              $('input[name=hiddenisbn]').val("Please Fill the Required Info!");
              return false;
			}
		if(title =="")
		{
          $('#hiddentitle').attr('type', 'text');
          $('input[name=hiddentitle]').val("Please Fill the Required Info!");
          return false;
		}
		if(author =="")
		{
          $('#hiddenauthor').attr('type', 'text');
          $('input[name=hiddenauthor]').val("Please Fill the Required Info!");
          return false;
		}
		if(quantity =="")
		{
          $('#hiddenquantity').attr('type', 'text');
          $('input[name=hiddenquantity]').val("Please Fill the Required Info!");
          return false;
		}
		else
		{
		   
			if ( ! (quantity % 1 === 0))
				{
					$('#hiddenquantity').attr('type', 'text');
					$('input[name=hiddenquantity]').val("Must be Integer Number");
				}
		}
		if(price =="")
		{
          $('#hiddenprice').attr('type', 'text');
          $('input[name=hiddenprice]').val("Please Fill the Required Info!");
          return false;
		}
		else
			{
			if ( ! (!isNaN(parseFloat(price)) && isFinite(price)))
			{
				$('#hiddenprice').attr('type', 'text');
				$('input[name=hiddenprice]').val("Must be Float Number");
			}
			}
		if(image =="")
		{
          $('#hiddenimage').attr('type', 'text');
          $('input[name=hiddenimage]').val("Please Choose Book Image!");
          return false;
		}
		return true;
	}
       
});