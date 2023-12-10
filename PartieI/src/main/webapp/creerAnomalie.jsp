<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mvc2.User" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer une nouvelle requête d'anomalie</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h1 {
            text-align: center;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        input, textarea {
            margin-bottom: 10px;
            padding: 5px;
            width: 300px;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        p {
            text-align: center;
        }

        a {
            color: #007BB5;
        }
    </style>
</head>
<body>
    <h1>Créer une nouvelle requête d'anomalie</h1>

    <% User currentUser = (User) request.getSession().getAttribute("user");
       if (currentUser != null) { %>

        <form action="creerAnomalie" method="post">
            Nom: <input type="text" name="nom" required><br>
            Prénom: <input type="text" name="prenom" required><br>
            Téléphone: <input type="text" name="telephone" required><br>
            Logiciel: <input type="text" name="logiciel" required><br>
            Système d'exploitation: <input type="text" name="systemeExploitation" required><br>
            Problème: <textarea name="probleme" required></textarea><br>

            <input type="submit" value="Créer la requête d'anomalie">
        </form>

    <% } else { %>

        <p>Vous devez être connecté pour créer une requête d'anomalie.</p>
        <p><a href="login.jsp">Connectez-vous</a></p>

    <% } %>

</body>
</html>
