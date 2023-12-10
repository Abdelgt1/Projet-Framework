package mvc2;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/supprimerAnomalie")
public class SupprimerAnomalieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String anomalieIdString = request.getParameter("id");
        
        if (anomalieIdString != null && !anomalieIdString.isEmpty()) {
            try {
                int anomalieId = Integer.parseInt(anomalieIdString);

                
                AccesBd accesBd = new AccesBd(request, response);
                accesBd.removeAnomalie(anomalieId);

               
                response.sendRedirect("dashboard.jsp?successMessage=Anomalie supprimée avec succès");
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

       
        response.sendRedirect("dashboard.jsp");
    }
}
