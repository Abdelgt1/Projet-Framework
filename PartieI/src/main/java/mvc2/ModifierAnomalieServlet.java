package mvc2;

import java.io.IOException;
import java.util.Enumeration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/modifierAnomalie")
public class ModifierAnomalieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        printRequestInfo(request);
        String anomalieIdString = request.getParameter("id");

        if (anomalieIdString != null && !anomalieIdString.isEmpty()) {
            try {
                int anomalieId = Integer.parseInt(anomalieIdString);
                AccesBd accesBd = new AccesBd(request, response);
                Anomalie anomalie = accesBd.getAnomalieById(anomalieId);

                if (anomalie != null) {
                    request.setAttribute("anomalie", anomalie);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("modifierAnomalie.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("errorMessage", "Impossible de charger l'anomalie pour la modification.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idString = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String logiciel = request.getParameter("logiciel");
        String systemeExploitation = request.getParameter("systemeExploitation");
        String probleme = request.getParameter("probleme");

        try {
            int anomalieId = Integer.parseInt(idString);
            AccesBd accesBd = new AccesBd(request, response);
            Anomalie anomalie = accesBd.getAnomalieById(anomalieId);

            if (anomalie != null) {
                anomalie.setNom(nom);
                anomalie.setPrenom(prenom);
                anomalie.setTelephone(telephone);
                anomalie.setEmail(email);
                anomalie.setLogiciel(logiciel);
                anomalie.setSystemeExploitation(systemeExploitation);
                anomalie.setProbleme(probleme);

                accesBd.updateAnomalie(anomalie);
                response.sendRedirect("modificationReussie.jsp");
                return;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        request.setAttribute("errorMessage", "Impossible de mettre à jour l'anomalie.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        dispatcher.forward(request, response);
    }

    private void printRequestInfo(HttpServletRequest request) {
        System.out.println("=== Informations de la Requête ===");
        System.out.println("Méthode de la requête : " + request.getMethod());
        System.out.println("URL de la requête : " + request.getRequestURL().toString());

        System.out.println("Paramètres de la requête :");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }

        System.out.println("En-têtes de la requête :");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }

        System.out.println("=== Fin des Informations de la Requête ===");
    }
}
