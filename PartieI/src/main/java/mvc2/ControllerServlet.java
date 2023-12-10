package mvc2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("command");

        if ("login".equals(command)) {
            login(request, response);
        } 
        else if ("logout".equals(command)) {
            logout(request, response);
        } else if ("signup".equals(command)) {
            signup(request, response);
        } else if ("creerAnomalie".equals(command)) {
            creerAnomalie(request, response);
        } else if ("modifierAnomalie".equals(command)) {
            modifierAnomalie(request, response);
        } else if ("supprimerAnomalie".equals(command)) {
        	supprimerAnomalie(request, response);
        }else if ("validerAnomalies".equals(command)) {
        	validerAnomalies(request, response);
        } else {
            showErrorPage(request, response, "Commande inconnue");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("logout.jsp");
        dispatcher.forward(request, response);
    }


    private void signup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        dispatcher.forward(request, response);
    }

    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        
        request.setAttribute("errorMessage", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        dispatcher.forward(request, response);
    }
    private void creerAnomalie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("creerAnomalie.jsp");
        dispatcher.forward(request, response);
    }
    private void modifierAnomalie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("modifierAnomalie.jsp");
        dispatcher.forward(request, response);
    }
    private void supprimerAnomalie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("supprimerAnomalie.jsp");
        dispatcher.forward(request, response);
    }
    
    private void validerAnomalies(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("validerAnomalies.jsp");
        dispatcher.forward(request, response);
    }

}
