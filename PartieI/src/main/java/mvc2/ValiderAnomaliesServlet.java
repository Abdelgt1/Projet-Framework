package mvc2;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/validerAnomalies")
public class ValiderAnomaliesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String[] anomalyIdsArray = request.getParameterValues("anomalieId");

        if (anomalyIdsArray != null && anomalyIdsArray.length > 0) {
            try {
               
                List<Integer> anomalyIds = 
                        List.of(anomalyIdsArray).stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                
                AccesBd accesBd = new AccesBd(request, response);
                accesBd.valideAnomalies(anomalyIds);

                
                response.sendRedirect("dashboard.jsp?successMessage=Anomalies validées avec succès");
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        
        response.sendRedirect("error.jsp");
    }
}
