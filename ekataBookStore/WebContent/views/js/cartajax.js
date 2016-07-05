$(window).load(function(){  
		
   });
function redirectPage(url,data)
{
	var form = $('<form action="' + url + '" method="post">' + '<input type="hidden" name="addtocart" value=' + data + ' />' + '</form>');
	$('body').append(form);
	$(form).submit();
	}



$(document).ready(function() { 
	////////////////////////////////////////////////
	$('body').on('click', '#addsadtocartbtn', function () {
		try
		{
		var isbn=$(this).attr('name');
		
		$.ajax({
		     type: 'post',
		     url: '/ekataBookStore/CartController',
		     data: {addsisbn:isbn},
		    
		     dataType: 'json',
		     async: false,
		     success: function (data) {
		    	 try
				  {
		    		
		    		 if(data == "Fail")
		    			 {
		    			 $("#addResponse").html('');
						    $("#addResponse").empty();
						    $("#addResponse").html('');  
		    			 }
		    		 else
		    			 {
		    			 console.log(data);      
		    			 
		    			 try
						  {
						    var tbl_body = "<table border='1' cellpadding='6'> <thead> <th ><blink>Get 20% Discount on Following Items</blink></th></thead>";
						    var odd_even = false;
						    var tbl_row = "";
						    console.log(data);
						    $.each(data, function() {
						         tbl_row = "";
						        var isbnkey="";
						        var isbnval="";
						        var price=1;
						        var discount=1;
						        $.each(this, function(k , v) {
						        	if(k == "isbn")
						            	{
						            	  isbnval=v;
						            	}
						        	
						            else
						            	{
						            	      
						            	       if(k=="price")
						            	    	   {
						            	    	     price=v;
						            	    	     discount=price-price*0.2;
						            	    	   }
						            	       if(k=="bookimage")
						            	    	  {
						            	            tbl_row += "<td><center><input type='image' src='"+v+"' name='" + isbnval + "' class='btTxt submit' id='addsadtocartbtn' width='200' height='200'/>"+"<br> <p style='color:blue;font-weight: bold;'>Original Price:$" + price + "</p> <p style='color:red;font-weight: bold;'> Discounted Price:$" + discount + "</p></center></td>";
						            	    	  }
						            	}
						        })
						        
						        tbl_body += "<tr>"+tbl_row+"</tr>";
						                   
						    })
						  
						    tbl_body=tbl_body + " </table>";
						  }
						  catch(err)
						  {
							  alert(err);
						  }
						    $("#addResponse").html('');
						    $("#addResponse").empty();
						    $("#addResponse").html(tbl_body);
		    			 /////////////////////////////////////////////////
		    			 
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
		}
		catch(errr)
		{
			alert(errr);
		}
	});
	//////////////////////////////////////////////////////
	
	$('body').on('click', '#addtocartbtn', function () {
		var slct = [];
		$("input[type='checkbox']:checked").each(function(){

			
			var isbn=$(this).val();
			var qty=$("#qty"+isbn).val();
			var price=$("#price"+isbn).val();
			slct.push({
		        isbn: isbn,
		        quantity:qty,
		        price:price
		    });

			});
	
		redirectPage('/ekataBookStore/CartController',JSON.stringify(slct));
		// on clicking add to cart button, above function is called; which in turn passes the selected check box isbn and quantity info to CartController which in turn adds it into the ArrayList with the help of CartDaoImpl class. 
		   
		
      });
	
	
});