package dao;

import database.DBConnection;
import models.Menu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    // Affiche le menu complet (plats disponibles)
    public void afficherMenu() {
        String query = "SELECT * FROM Menu WHERE disponible = 1 ORDER BY prix";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n========== NOTRE CARTE ==========");
            System.out.printf("  %-25s | %-35s | %s%n", "Plat", "Description", "Prix");
            System.out.println("  " + "-".repeat(75));
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("  %-25s | %-35s | %.2f MAD%n",
                    rs.getString("nom_plat"),
                    rs.getString("description_plat"),
                    rs.getDouble("prix"));
            }
            if (!found) System.out.println("  Le menu est vide pour le moment.");
            System.out.println("==================================\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retourne tous les plats (pour la gestion admin)
    public List<Menu> getTousLesPlats() {
        List<Menu> plats = new ArrayList<>();
        String query = "SELECT * FROM Menu ORDER BY id_menu";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                plats.add(new Menu(
                    rs.getInt("id_menu"),
                    rs.getString("nom_plat"),
                    rs.getString("description_plat"),
                    rs.getDouble("prix"),
                    rs.getBoolean("disponible")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }

    // Ajouter un plat (ADMIN)
    public boolean ajouterPlat(String nomPlat, String description, double prix) {
        String query = "INSERT INTO Menu (nom_plat, description_plat, prix, disponible) VALUES (?, ?, ?, 1)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nomPlat);
            stmt.setString(2, description);
            stmt.setDouble(3, prix);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Changer la disponibilité d'un plat (ADMIN)
    public boolean toggleDisponibilite(int idMenu) {
        String query = "UPDATE Menu SET disponible = 1 - disponible WHERE id_menu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idMenu);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
