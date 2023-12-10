<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mvc2.Anomalie" %>
<%@ page import="mvc2.User" %>
<%@ page import="mvc2.AccesBd" %>

<%
    Anomalie anomalie = (Anomalie) request.getAttribute("anomalie");
    User currentUser = (User) request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier l'anomalie</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h1 {
            text-align: center;
        }

        form {
            max-width: 400px;
            margin: 0 auto;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #008CBA;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #007BB5;
        }

        p {
            text-align: center;
            color: red;
        }
    </style>
</head>
<body>
    <h1>Modifier l'anomalie</h1>

    <% if (anomalie != null) { %>
        <form action="modifierAnomalie" method="post">
            <input type="hidden" name="id" value="<%= anomalie.getId() %>">

            <label for="nom">Nom :</label>
            <input type="text" name="nom" value="<%= anomalie.getNom() %>">

            <label for="prenom">Prénom :</label>
            <input type="text" name="prenom" value="<%= anomalie.getPrenom() %>">

            <label for="telephone">Téléphone :</label>
            <input type="text" name="telephone" value="<%= anomalie.getTelephone() %>">

            <label for="email">Email :</label>
            <input type="text" id="email" name="email" value="<%= anomalie.getEmail() %>">

            <label for="logiciel">Logiciel :</label>
            <input type="text" id="logiciel" name="logiciel" value="<%= anomalie.getLogiciel() %>">

            <label for="systemeExploitation">Système d'exploitation :</label>
            <input type="text" id="systemeExploitation" name="systemeExploitation" value="<%= anomalie.getSystemeExploitation() %>">

            <label for="probleme">Problème :</label>
            <textarea id="probleme" name="probleme"><%= anomalie.getProbleme() %></textarea>

            <input type="submit" value="Enregistrer les modifications">
        </form>
    <% } else { %>
        <p>Anomalie non trouvée.</p>
    <% } %>
</body>
</html>
