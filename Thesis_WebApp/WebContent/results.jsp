<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">

<title>Insert title here</title>
</head>
<body >
	<table class="table">
		<thead>
			<tr>
				<th>Name</th>
				<th>Duration</th>
			</tr>
		</thead>
		
			
	</table>
	<script>
	function foo(){
		
		alert("asasd");
		var json = request.getAttribute("json");
		alert(json);
	}
</script>
<%
    Object value = request.getAttribute("json");
%>

<p><%=value%></p>
</body>
</html>