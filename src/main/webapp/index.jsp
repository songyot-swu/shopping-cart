<%@page import="com.project.shopping.cart.connection.DbCon"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Shopping Cart</title>
        <%@include file="includes/head.jsp" %>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>

        <% out.print(DbCon.getConnection());%>

        <%@include file="includes/footer.jsp" %>
    </body>
</html>