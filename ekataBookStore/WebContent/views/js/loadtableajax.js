$(window).load(function(){  
   });
$(document).ready(function() { 
	displayTable();
	
	function displayTable()
	{
		
		$.get('AdminController', {
			
			   tabledata:  "displayTable",
				dataType: 'json'
				}, function(data) {
					  try
					  {
					    var tbl_body = "<table border='1' cellspacing='5'> <thead> <th>ISBN</th><th>TITLE</th><th>Author</th><th>Quantity</th><th>Price</th><th>File Name</th><th>Book Image</th> </thead>";
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
	
	
		});
	/////////////////////////////
	

	
       
