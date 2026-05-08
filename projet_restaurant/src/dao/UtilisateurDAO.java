package dao;

import database.DBConnection;
import models.Utilisateur;
import java.sql.*;

public class UtilisateurDAO {

    // Authentification par email + mot de passe
    public Utilisateur authentifier(String email, String motDePasse) {
        String query = "SELECT * FROM Utilisateurs WHERE email = ? AND mot_de_passe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Utilisateur(
                    rs.getInt("id_utilisateur"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("role_utilisateur")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Inscription d'un nouveau client
    public boolean inscrire(String nom, String prenom, String email, String motDePasse) {
        String query = "INSERT INTO Utilisateurs (nom, prenom, email, mot_de_passe, role_utilisateur) " +
                       "VALUES (?, ?, ?, ?, 'CLIENT')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setString(4, motDePasse);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE") || e.getMessage().contains("unique")) {
                System.out.println("  Cet email est deja utilise.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }
}
