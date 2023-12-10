package mvc2;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/creerAnomalie")
public class CreerAnomalieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String logiciel = request.getParameter("logiciel");
        String systemeExploitation = request.getParameter("systemeExploitation");
        String probleme = request.getParameter("probleme");

        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser != null) {
            Anomalie nouvelleAnomalie = new Anomalie();
            nouvelleAnomalie.setNom(nom);
            nouvelleAnomalie.setPrenom(prenom);
            nouvelleAnomalie.setTelephone(telephone);
            nouvelleAnomalie.setLogiciel(logiciel);
            nouvelleAnomalie.setSystemeExploitation(systemeExploitation);
            nouvelleAnomalie.setProbleme(probleme);
            nouvelleAnomalie.setStatus(false);  
            nouvelleAnomalie.setUserId(currentUser.getId());

            AccesBd accesBd = new AccesBd(request, response);
            accesBd.insertAnomalie(nouvelleAnomalie, currentUser);
            
            System.out.println("Current User: " + currentUser);
            System.out.println("New Anomaly: " + nouvelleAnomalie);


            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
