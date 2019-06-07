<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>
<style type="text/css">
	table.steelBlueCols {
	  border: 1px solid #555353;
	  background-color: #5C34C4;
	  width: 400px;
	  text-align: center;
	  border-collapse: collapse;
	}
	table.steelBlueCols td, table.steelBlueCols th {
	  border: 1px solid #555555;
	  padding: 5px 7px;
	}
	table.steelBlueCols tbody td {
	  font-size: 12px;
	  font-weight: bold;
	  color: #FFFFFF;
	}
	table.steelBlueCols tfoot td {
	  font-size: 13px;
	}
</style>

<html>
<title>Australia cities Weather Report</title>
</head>

<body onload="access()">
	<h2>Please select your city to View Weather</h2>

	<form:form method="POST" commandName="city">
		<table>
			<tr>
				<td>Please select:</td>
				<td><form:select path="cityName">
					  <form:option value="" label="...." />
					  <form:options items="${cities}" />
				       </form:select>
                                </td>
				<td><form:errors path="cityName" cssStyle="color: #ff0000;" /></td>
				<td><input type="submit" name="submit" value="Submit"></td>
			</tr>
			
				
			<tr>
			
			</table>
			
			<c:if test="${city.weather !=null}">
				<table class="steelBlueCols">
				<tbody>
				<tr>
					<td>City</td><td>${city.cityName}</td></tr>
					<tr>
					<td>Weather</td><td>${city.weather}</td></tr>
					<tr>
					<td>Temperature</td><td>${city.temperature}</td></tr>
					<tr>
					<td>WindSpeed</td><td>${city.windSpeed}</td></tr>
				</tbody>
			</table>
	</c:if>
	</form:form>

</body>
</html>
