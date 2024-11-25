<%@ page import="java.util.List" %>
<%@ page import="Model.IndyWinner" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Indy Winners</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body>
<h1>Indy Winners</h1>
<table border="1" cellpadding="5" cellspacing="5">

    <tr>
        <th>Year</th>
        <th>Driver Name</th>
        <th>Average Speed</th>
        <th>Country</th>
    </tr>

    <%-- Looping through employeeList --%>
    <%
        List<IndyWinner> winnerList = (List<IndyWinner>) request.getAttribute("winners");
        for (IndyWinner winner : winnerList) {
    %>
    <tr>
        <td><%= winner.getYear() %></td>
        <td><%= winner.getDriver() %></td>
        <td><%= winner.getAverageSpeed() %></td>
        <td><%= winner.getCountry() %></td>
    </tr>
    <%
        }
    %>
</table>

<%-- Show previous link/button if not on the first page --%>
<%
    int currentPage = (int) request.getAttribute("page");
    int noOfPages = (int) request.getAttribute("totalPages");

    if (currentPage > 1) {
%>
<td><a href="IndyWinners?page=<%= currentPage - 1 %>">Previous</a></td>
<%
    }
%>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <%-- Show Page numbers --%>
        <%
            for (int i = 1; i <= noOfPages; i++) {
                if (i == currentPage) {
        %>
        <td><%= i %></td>
        <%
        } else {
        %>
        <td><a href="IndyWinners?page=<%= i %>"><%= i %></a></td>
        <%
                }
            }
        %>
    </tr>
</table>

<%-- Show next link/button if not on the last page --%>
<%
    if (currentPage < noOfPages) {
%>
<td><a href="IndyWinners?page=<%= currentPage + 1 %>">Next</a></td>
<%
    }
%>

</body>

</html>