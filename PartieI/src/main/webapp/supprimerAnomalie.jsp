<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Suppression d'anomalie</title>
</head>
<body>
    <h1>Suppression d'anomalie</h1>

  
    <%
        String successMessage = request.getParameter("successMessage");
        if (successMessage != null && !successMessage.isEmpty()) {
    %>
            <p><%= successMessage %></p>
    <%
        }
    %>

    <p>Retour au <a href="dashboard.jsp">Dashboard</a></p>
</body>
</html>
