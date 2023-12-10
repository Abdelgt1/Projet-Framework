<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mvc2.ListeAnomalies, mvc2.Anomalie" %>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@ page import="mvc2.AccesBd, mvc2.User" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>

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

        .user-info {
            text-align: right;
            margin-right: 20px;
        }

        #dashboard-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        .create-btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            display: inline-block;
            text-decoration: none;
            font-size: 16px;
            margin-bottom: 10px;
            margin-top: 10px;
            width: 100%;
        }

        .create-btn:hover {
            background-color: #45a049;
        }

        .anomalie {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
            width: 30%;
            box-sizing: border-box;
        }

        .anomalie p {
            margin-bottom: 5px;
        }

        .actions {
            display: flex;
            justify-content: space-between;
        }

        .logout-btn {
            background-color: #f44336;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }

        .modify-btn,
        .delete-btn,
        .validate-btn {
            background-color: #008CBA;
            color: white;
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .modify-btn:hover,
        .delete-btn:hover,
        .validate-btn:hover {
            background-color: #007BB5;
        }
    </style>
</head>
<body>
    <h1>Bienvenue sur le Dashboard</h1>

    <div class="user-info">
        <% 
            User currentUser = (User) request.getSession().getAttribute("user");
            if (currentUser != null) {
                out.println("<p>" + currentUser.getNom() + " " + currentUser.getPrenom() + "</p>");
                out.println("<p>" + currentUser.getRole() + "</p>");
        %>
                <button class="logout-btn" onclick="window.location.href='logout'">Déconnexion</button>
        <% } else { %>
                <p><a href="login.jsp">Se connecter</a></p>
        <% } %>
    </div>

    <div id="dashboard-container">
        <% if (currentUser != null && "client".equals(currentUser.getRole())) { %>
            <form id="search-form" method="get" action="dashboard.jsp">
                <label for="searchTerm">Rechercher :</label>
                <input type="text" id="searchTerm" name="searchTerm" placeholder="Entrez le problème">
                <input type="submit" id="search-btn" value="Rechercher">
            </form>
            <a href="dashboard.jsp">Toutes les anomalies</a>
            <button class="create-btn" onclick="window.location.href='creerAnomalie.jsp'">Créer une nouvelle requête d'anomalie</button>
        <% } %>

        <% 
            AccesBd accesBd = new AccesBd(request, response);
            ListeAnomalies listeAnomalies = null;
            String searchTerm = request.getParameter("searchTerm");

            if (currentUser != null) {
                if ("client".equals(currentUser.getRole())) {
                    if (searchTerm != null && !searchTerm.isEmpty()) {
                        listeAnomalies = accesBd.searchRequestAnomalyByProblem(currentUser, searchTerm);
                    } else {
                        listeAnomalies = accesBd.getListeAnomalies(currentUser);
                    }
                } else if ("technicien".equals(currentUser.getRole())) {
                    listeAnomalies = accesBd.getListeAnomalies();
                }
            }
            
            List<Anomalie> anomalies = (listeAnomalies != null) ? listeAnomalies.getAnomalies() : new ArrayList<Anomalie>();

            for (Anomalie anomalie : anomalies) {
        %>
                <div class="anomalie">
                    <p>Nom: <%= anomalie.getNom() %></p>
                    <p>Prénom: <%= anomalie.getPrenom() %></p>
                    <p>Téléphone: <%= anomalie.getTelephone() %></p>
                    <p>Email: <%= anomalie.getEmail() %></p>
                    <p>Logiciel: <%= anomalie.getLogiciel() %></p>
                    <p>Système d'exploitation: <%= anomalie.getSystemeExploitation() %></p>
                    <p>Problème: <%= anomalie.getProbleme() %></p>
                    <p>Status: <%= anomalie.getStatus() ? "Résolu" : "En cours de traitement" %></p>

                    <div class="actions">
                        <% if (currentUser != null && "client".equals(currentUser.getRole())) { %>
                            <button class="modify-btn" onclick="window.location.href='modifierAnomalie?id=<%= anomalie.getId() %>'">Modifier</button>
                            <button class="delete-btn" onclick="window.location.href='supprimerAnomalie?id=<%= anomalie.getId() %>'">Supprimer</button>
                        <% } else if (currentUser != null && "technicien".equals(currentUser.getRole())) { %>
                            <form action="validerAnomalies" method="post">
                                <input type="hidden" name="anomalieId" value="<%= anomalie.getId() %>">
                                <button class="validate-btn" type="submit">Valider</button>
                            </form>
                        <% } %>
                    </div>
                </div>
        <%
            }
        %>
    </div>

</body>
</html>
