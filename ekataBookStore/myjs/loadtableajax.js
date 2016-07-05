$(window).load(function(){  
		//initialize after images are loaded  
	 // alert("${pageContext.request.contextPath}");
   });
$(document).ready(function() { 
	displayTable();
	
	function displayTable()
	{
		//alert("displayTable() method called");
		$.get('../AdminController', {
			
			   tabledata:  "displayTable",
				dataType: 'json'
				}, function(data) {
				//$('#ajaxResponse').text(responseText);
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
					       // alert(isbnval);
					        //var a="/ekataBookStore/AdminController?action=edit&isbn='"+ isbnval +"'   ";
					       // alert("<td>"+ "<a href='/ekataBookStore/AdminController?action=edit&isbn='"+isbnval+"''>"+"</a>"+ "Edit" +"</td>");
					        //alert("/ekataBookStore/AdminController?action=edit&isbn='"+ isbnval +"'   ");
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
					   // alert(tbl_body);
					    $("#tableResponse").html('');
					    $("#tableResponse").empty();
					    $("#tableResponse").html(tbl_body);
					
				
				
				
				});   
	}
	
	$('#FileToUpload').change(function () {
		var countFiles = $(this)[0].files.length;

	     var imgPath = $(this)[0].value;
	   //  alert($(this)[0].size);
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

	             //loop for each file selected for uploaded.
	             for (var i = 0; i < countFiles; i++) {

	                 var reader = new FileReader();
	                 reader.onload = function (e) {
	                	 var element = document.getElementById("image-holder");
	                	// var img1="<img src= '"+e.target.result +"' id='bookingimg' class='thumb-image' width='50' height='50' />";
	                	// alert(img1);
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
	                	 //var im1=document.createElement(img1);
	                	 var node = document.createTextNode("Book to Be Added");
	                	 para.appendChild(node);
	                	 element.appendChild(para);
	                	 element.appendChild(x);
	                	 
	                	 }
	                	 catch(errr)
	                	 {
	                		 alert(errr);
	                	 }
	                	/* element.appendChild(
	                	 $("<img />", {
	                         "src": e.target.result,
	                         "id:":"bookimg",
	                             "class": "thumb-image"
	                            
	                     }));*/
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

       // alert("Original width=" + realWidth + ", " + "Original height=" + realHeight);
        
		//getContent();
		//alert(bookimgElem);
		//var imginfo = getBase64Image(imgElem);
		//var imgar ={ imgdata:imginfo   };
		//alert(imginfo);
		var imginfo = getBase64Image(bookimgElem,realWidth,realHeight);
		//alert(imginfo);
		var imgar ={ imgdata:imginfo   };
		var pdata=JSON.stringify(imgar);
		//var pdata=JSON.stringify("abc");
		var pdata=JSON.stringify(pdata);
		//console.log(pdata);
		//alert(pdata);
		}
		catch(err)
		{
			alert(err);
		}
		$.post('../AdminController', {
            pdata: JSON.stringify(imgar),
            dataType: 'json'
    }, function(imgText) {
           // $('#ajaxResponse').text(responseText);
            //displayTable();
            //$("#tableResponse").load(location.href + " #tableResponse");
    	//$("#img1").attr('src', 'data:image/png; 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');
    	//alert("Success");     
    	//$("#img1").attr('src',imgText);
    	$("#img1").attr('src',imgText);
    	//alert(imgText);
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
 	 //alert(document.getElementById("bookimg"));
 	  //$('#bookingimg').attr('src', 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==');
 	  $('#bookingimg').remove();
 	 $('#parag').remove();
 	$('#FileToUpload').val("");
 	//$('#hiddenisbn').clear();
 	$('#hiddenisbn').attr('type', 'hidden');
 	$('#hiddentitle').attr('type', 'hidden');
 	$('#hiddenauthor').attr('type', 'hidden');
 	$('#hiddenquantity').attr('type', 'hidden');
 	$('#hiddenprice').attr('type', 'hidden');
 	$('#hiddenimage').attr('type', 'hidden');
 	$('input[id="saveButton"]').prop('disabled', false);
 	
 	  //var element = document.getElementById("image-holder");
 	  //element.removeChild(document.getElementById("bookimg"));
		}
		catch(error)
		{
			alert(error);
		}
 	//  $("#bookimg").remove();
		});
	//////////////////////////////
	function getBase64Image(imgElem,realWidth,realHeight) {
		// imgElem must be on the same server otherwise a cross-origin error will be thrown "SECURITY_ERR: DOM Exception 18"
		    
		var canvas = document.createElement("canvas");
		   // canvas.width = imgElem.clientWidth;
		    //canvas.height = imgElem.clientHeight;
		canvas.width = realWidth;
	    canvas.height = realHeight;
		    //alert("after canvas"); 
		    try
		    {
		    var ctx = canvas.getContext("2d");
		   // alert("Before Draw");
		    ctx.drawImage(imgElem, 0, 0);
		   // alert("After Draw");
		    var dataURL = canvas.toDataURL("image/png");
		  // alert(dataURL);
	       }
		    catch(err)
		    {
		      alert(err);
		    }
		    //return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
		    return dataURL;
		}
	
	
	
	$('#saveButton').click(function(event) {
    	//alert("SAVUU Button Clicked");
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
    	 //  var imgar ={ imgdata:imginfo   };
    	   
            $.post('../AdminController', {
                    savedata:  JSON.stringify(sendInfo),
                  //  pdata: JSON.stringify(imgar),
                    dataType: 'json'
            }, function(responseText) {
                    $('#ajaxResponse').text(responseText);
                    displayTable();
                    //$("#tableResponse").load(location.href + " #tableResponse");
            });
    		}
    	else
    		{
    		$('input[id="saveButton"]').prop('disabled', true);
    		}
    	
    });
	
	$('#loginbtn').click(function(event) {
		//alert("Login Button Clicked");
        	var username=$('#username').val();
        	   var userpwd=$('#userpwd').val();
        	   var empaddress=$('#empadd').val();
        	   var empmail=$('#empmail').val();
        	   var sendInfo = {username:username,userpwd:userpwd };
        	   
                $.get('LoginController', {
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