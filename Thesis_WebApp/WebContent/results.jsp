<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"
	type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/1.10.10/css/jquery.dataTables.min.css">
<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.10/js/dataTables.jqueryui.min.js"></script>
<script
	src="//cdn.datatables.net/plug-ins/725b2a2115b/integration/bootstrap/3/dataTables.bootstrap.js"></script>
<script
	src="//datatables.net/release-datatables/extensions/ColReorder/js/dataTables.colReorder.js"></script>

<script type="text/javascript">
	function returnJSONData() {
		var processData = 'JSON';
		$.ajax({
			type : "POST",
			url : "./search",
			data : "processData=" + processData,
			success : function(result) {
				console.log(result);
				makeTable(result);
				/* alert("Data type process: "+result.name
				        +" First Name: "+result.duration) */

			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert("Error status code: " + xhr.status);
				alert("Error details: " + thrownError);
			}
		});

	}
	function makeTable(result) {
		var columns = [];
		var columns1= [];
		
		 jQuery.each(result, function(i, value) {
		 var obj = {};
		 var ct =0;
		 	jQuery.each(value, function(j, v1){
				obj[ct++] = v1;
				
			});
			columns1.push(obj);
		}); 
		
		var obj = {
			sTitle : "duration"
			
		};
		columns.push(obj);
		var obj1 = {
			sTitle : "difficulty"
			
		};
		columns.push(obj1);
		var obj1 = {
			sTitle : "name"
			
		};
		columns.push(obj1);
		$('#myTable').dataTable({
			"columns" : columns,
			"data" : columns1,
		});
	}
</script>
</head>
<body onload="returnJSONData();">

	<!-- <input type="Submit" value="JSON data from servlet" 
            onclick="returnJSONData();"> -->

	<table id="myTable" class="display cell-border compact"></table>

</body>
</html>