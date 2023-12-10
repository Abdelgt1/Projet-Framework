<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mvc2.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accueil</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h1 {
            text-align: center;
        }

        p {
            margin: 0;
        }

        .welcome-message {
            text-align: center;
            margin-top: 20px;
        }

        .user-actions {
            text-align: center;
            margin-top: 20px;
        }

        .user-actions a {
            text-decoration: none;
            color: #008CBA;
            font-weight: bold;
            margin: 0 10px;
        }

        .logout-btn {
            
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top : 45px;
        }

        .login-btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-bottom : 15px;
        }
    </style>
</head>
<body>
    <h1>Bienvenue sur notre plateforme de maintenance</h1>

    <div class="welcome-message">
        <% User currentUser = (User) request.getSession().getAttribute("user");
           if (currentUser != null) { %>
            <p>Bonjour, <%= currentUser.getNom() %> <%= currentUser.getPrenom() %>.</p>
        <% } else { %>
            <p>Vous devez être connecté pour créer et gérer vos demandes de maintenance.</p>
        <% } %>
    </div>

    <div class="user-actions">
        <% if (currentUser != null) { %>
            <p><a href="dashboard.jsp">Consulter vos demandes de maintenance</a> ou <a href="creerAnomalie.jsp">créer une nouvelle demande</a>.</p>
            <p><a href="logout" class="logout-btn">Déconnexion</a></p>
        <% } else { %>
            <p><a href="login.jsp" class="login-btn">Connectez-vous</a></p>
        <% } %>
    </div>

</body>
</html>
