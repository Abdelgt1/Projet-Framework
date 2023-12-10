package mvc2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AccesBd {

    private Connection cnx = null;
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    public AccesBd(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/support_technique";
            cnx = DriverManager.getConnection(url, "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            displayException(e);
        }
    }

    public void insertUser(User user) throws SQLException {
        String query = "INSERT INTO utilisateurs (nom, prenom, email, telephone, mot_de_passe, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, user.getNom());
            statement.setString(2, user.getPrenom());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getTelephone());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public User authenticateUser(String email, String password) throws SQLException {
        String query = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id_utilisateur"));
                    user.setNom(resultSet.getString("nom"));
                    user.setPrenom(resultSet.getString("prenom"));
                    user.setEmail(resultSet.getString("email"));
                    user.setTelephone(resultSet.getString("telephone"));
                    user.setPassword(resultSet.getString("mot_de_passe"));
                    user.setRole(resultSet.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public ListeAnomalies getListeAnomalies() {
        ListeAnomalies listeAnomalies = new ListeAnomalies();

        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM anomalies");
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Executing query to retrieve all anomalies...");

            while (resultSet.next()) {
                Anomalie anomalie = new Anomalie();
                anomalie.setId(resultSet.getInt("id"));
                anomalie.setNom(resultSet.getString("nom"));
                anomalie.setPrenom(resultSet.getString("prenom"));
                anomalie.setTelephone(resultSet.getString("telephone"));
                anomalie.setEmail(resultSet.getString("email"));
                anomalie.setLogiciel(resultSet.getString("logiciel"));
                anomalie.setSystemeExploitation(resultSet.getString("systeme_exploitation"));
                anomalie.setProbleme(resultSet.getString("probleme"));
                anomalie.setStatus(resultSet.getBoolean("status"));

                listeAnomalies.addAnomalie(anomalie);
            }

            resultSet.close();
            statement.close();

            System.out.println("Number of anomalies retrieved: " + listeAnomalies.getAnomalies().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listeAnomalies;
    }

    public ListeAnomalies getListeAnomalies(User currentUser) {
        ListeAnomalies listeAnomalies = new ListeAnomalies();

        try {
            System.out.println("User role: " + currentUser.getRole());
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM anomalies WHERE user_id = ?");
            statement.setInt(1, currentUser.getId());
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Executing query to retrieve user-specific anomalies...");

            while (resultSet.next()) {
                Anomalie anomalie = new Anomalie();
                anomalie.setId(resultSet.getInt("id"));
                anomalie.setNom(resultSet.getString("nom"));
                anomalie.setPrenom(resultSet.getString("prenom"));
                anomalie.setTelephone(resultSet.getString("telephone"));
                anomalie.setEmail(resultSet.getString("email"));
                anomalie.setLogiciel(resultSet.getString("logiciel"));
                anomalie.setSystemeExploitation(resultSet.getString("systeme_exploitation"));
                anomalie.setProbleme(resultSet.getString("probleme"));
                anomalie.setStatus(resultSet.getBoolean("status"));

                anomalie.setUserId(currentUser.getId());

                listeAnomalies.addAnomalie(anomalie);
            }

            resultSet.close();
            statement.close();

            System.out.println("Anomalies retrieved for user " + currentUser.getId() + ": " + listeAnomalies.getAnomalies().size());
        } catch (SQLException e) {
            e.printStackTrace();
            return listeAnomalies;
        }

        return listeAnomalies;
    }

    public void insertAnomalie(Anomalie anomalie, User currentUser) {
        try {
            String sql = "INSERT INTO anomalies (nom, prenom, telephone, email, logiciel, systeme_exploitation, probleme, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = cnx.prepareStatement(sql);

            statement.setString(1, anomalie.getNom());
            statement.setString(2, anomalie.getPrenom());
            statement.setString(3, anomalie.getTelephone());
            statement.setString(4, currentUser.getEmail());
            statement.setString(5, anomalie.getLogiciel());
            statement.setString(6, anomalie.getSystemeExploitation());
            statement.setString(7, anomalie.getProbleme());
            statement.setBoolean(8, anomalie.getStatus());
            statement.setInt(9, anomalie.getUserId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Anomalie insérée avec succès dans la base de données.");
            } else {
                System.out.println("Échec de l'insertion de l'anomalie dans la base de données.");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAnomalie(Anomalie anomalie) {
        try {
            PreparedStatement statement = cnx.prepareStatement(
                    "UPDATE anomalies SET nom = ?, prenom = ?, telephone = ?, email = ?, logiciel = ?, systeme_exploitation = ?, probleme = ?, status = ? WHERE id = ?");

            statement.setString(1, anomalie.getNom());
            statement.setString(2, anomalie.getPrenom());
            statement.setString(3, anomalie.getTelephone());
            statement.setString(4, anomalie.getEmail());
            statement.setString(5, anomalie.getLogiciel());
            statement.setString(6, anomalie.getSystemeExploitation());
            statement.setString(7, anomalie.getProbleme());
            statement.setBoolean(8, anomalie.getStatus());
            statement.setInt(9, anomalie.getId());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Anomalie getAnomalieById(int id) {
        try {
            String query = "SELECT * FROM anomalies WHERE id = ?";
            try (PreparedStatement statement = cnx.prepareStatement(query)) {
                statement.setInt(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Anomalie anomalie = new Anomalie();
                        anomalie.setId(resultSet.getInt("id"));
                        anomalie.setNom(resultSet.getString("nom"));
                        anomalie.setPrenom(resultSet.getString("prenom"));
                        anomalie.setTelephone(resultSet.getString("telephone"));
                        anomalie.setEmail(resultSet.getString("email"));
                        anomalie.setLogiciel(resultSet.getString("logiciel"));
                        anomalie.setSystemeExploitation(resultSet.getString("systeme_exploitation"));
                        anomalie.setProbleme(resultSet.getString("probleme"));
                        anomalie.setStatus(resultSet.getBoolean("status"));

                        return anomalie;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void removeAnomalie(int id) {
        try {
            PreparedStatement statement = cnx.prepareStatement("DELETE FROM anomalies WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void valideAnomalies(List<Integer> anomaliesIds) {
        try {
            PreparedStatement statement = cnx.prepareStatement("UPDATE anomalies SET status = true WHERE id = ?");

            for (int id : anomaliesIds) {
                statement.setInt(1, id);
                statement.addBatch();
            }

            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ListeAnomalies searchRequestAnomalyByProblem(User currentUser, String searchTerm) {
        ListeAnomalies listeAnomalies = new ListeAnomalies();

        try {
            PreparedStatement statement = cnx.prepareStatement(
                "SELECT * FROM anomalies WHERE user_id = ? AND LOWER(probleme) LIKE LOWER(?)"
            );
            statement.setInt(1, currentUser.getId());
            statement.setString(2, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Executing query to retrieve user-specific anomalies with search term...");

            while (resultSet.next()) {
                Anomalie anomalie = new Anomalie();
                anomalie.setId(resultSet.getInt("id"));
                anomalie.setNom(resultSet.getString("nom"));
                anomalie.setPrenom(resultSet.getString("prenom"));
                anomalie.setTelephone(resultSet.getString("telephone"));
                anomalie.setEmail(resultSet.getString("email"));
                anomalie.setLogiciel(resultSet.getString("logiciel"));
                anomalie.setSystemeExploitation(resultSet.getString("systeme_exploitation"));
                anomalie.setProbleme(resultSet.getString("probleme"));
                anomalie.setStatus(resultSet.getBoolean("status"));

                anomalie.setUserId(currentUser.getId());

                listeAnomalies.addAnomalie(anomalie);
            }

            resultSet.close();
            statement.close();

            System.out.println("Anomalies retrieved for user " + currentUser.getId() + " with search term: " + searchTerm + ": " + listeAnomalies.getAnomalies().size());
        } catch (SQLException e) {
            e.printStackTrace();
            return listeAnomalies;
        }

        return listeAnomalies;
    }

    private void displayException(Exception e) {
        RequestDispatcher disp = request.getRequestDispatcher("exception.jsp");

        try {
            request.setAttribute("exception", e);
            disp.forward(request, response);
        } catch (ServletException | IOException e1) {
            e1.printStackTrace();
        }
    }
}
