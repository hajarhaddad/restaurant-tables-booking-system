package dao;

import database.DBConnection;
import models.TableResto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    // Affiche les tables libres dans le terminal
    public void afficherTablesLibres() {
        String query = "SELECT * FROM Tables_Resto WHERE statut = 'Libre' ORDER BY numero_table";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- Tables Disponibles ---");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("  ID: %d | Table N°%d | Capacite: %d personnes%n",
                    rs.getInt("id_table"),
                    rs.getInt("numero_table"),
                    rs.getInt("capacite"));
            }
            if (!found) System.out.println("  Aucune table disponible pour le moment.");
            System.out.println("--------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retourne la liste des tables libres (pour validation)
    public List<TableResto> getTablesLibres() {
        List<TableResto> tables = new ArrayList<>();
        String query = "SELECT * FROM Tables_Resto WHERE statut = 'Libre'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                tables.add(new TableResto(
                    rs.getInt("id_table"),
                    rs.getInt("numero_table"),
                    rs.getInt("capacite"),
                    rs.getString("statut")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    // Vérifie si une table existe et est libre
    public boolean tableDisponible(int idTable) {
        String query = "SELECT statut FROM Tables_Resto WHERE id_table = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idTable);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Libre".equals(rs.getString("statut"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Met à jour le statut d'une table
    public boolean changerStatut(int idTable, String nouveauStatut) {
        String query = "UPDATE Tables_Resto SET statut = ? WHERE id_table = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nouveauStatut);
            stmt.setInt(2, idTable);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Affiche toutes les tables avec leur statut (vue ADMIN)
    public void afficherToutesLesTables() {
        String query = "SELECT * FROM Tables_Resto ORDER BY numero_table";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- Etat de toutes les tables ---");
            while (rs.next()) {
                System.out.printf("  Table N°%d | Capacite: %d pers. | Statut: %s%n",
                    rs.getInt("numero_table"),
                    rs.getInt("capacite"),
                    rs.getString("statut"));
            }
            System.out.println("---------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
