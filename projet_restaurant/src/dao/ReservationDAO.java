package dao;

import database.DBConnection;
import models.Reservation;
import java.sql.*;

public class ReservationDAO {

    // Ajouter une nouvelle réservation
    public boolean ajouterReservation(Reservation res) {
        String query = "INSERT INTO Reservations " +
                       "(id_client, id_table, date_res, heure_res, nb_personnes, statut_res) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, res.getIdClient());
            stmt.setInt(2, res.getIdTable());
            stmt.setDate(3, res.getDateRes());
            stmt.setTime(4, res.getHeureRes());
            stmt.setInt(5, res.getNbPersonnes());
            stmt.setString(6, res.getStatutRes());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la reservation : " + e.getMessage());
            return false;
        }
    }

    // Afficher les réservations d'un client précis
    public void afficherReservationsClient(int idClient) {
        String query = "SELECT * FROM Vue_Details_Reservations WHERE id_reservation IN " +
                       "(SELECT id_reservation FROM Reservations WHERE id_client = ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n=== MES RESERVATIONS ===");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("  ID: %d | Table N°%d | Le %s a %s | %d pers. | Statut: %s%n",
                    rs.getInt("id_reservation"),
                    rs.getInt("numero_table"),
                    rs.getDate("date_res"),
                    rs.getTime("heure_res"),
                    rs.getInt("nb_personnes"),
                    rs.getString("statut_res"));
            }
            if (!found) System.out.println("  Vous n'avez aucune reservation.");
            System.out.println("========================\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Afficher toutes les réservations (vue ADMIN)
    public void afficherToutesLesReservations() {
        String query = "SELECT * FROM Vue_Details_Reservations ORDER BY date_res, heure_res";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n=== TOUTES LES RESERVATIONS ===");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("  ID: %d | %s %s | Table N°%d | Le %s a %s | %d pers. | Statut: %s%n",
                    rs.getInt("id_reservation"),
                    rs.getString("Prenom_Client"),
                    rs.getString("Nom_Client"),
                    rs.getInt("numero_table"),
                    rs.getDate("date_res"),
                    rs.getTime("heure_res"),
                    rs.getInt("nb_personnes"),
                    rs.getString("statut_res"));
            }
            if (!found) System.out.println("  Aucune reservation enregistree.");
            System.out.println("================================\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Annuler une réservation (par le client, uniquement les siennes)
    public boolean annulerReservation(int idReservation, int idClient) {
        String query = "UPDATE Reservations SET statut_res = 'Annulee' " +
                       "WHERE id_reservation = ? AND id_client = ? AND statut_res != 'Annulee'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idReservation);
            stmt.setInt(2, idClient);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // Libérer la table associée
                libererTable(idReservation);
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Confirmer une réservation (ADMIN uniquement)
    public boolean confirmerReservation(int idReservation) {
        String query = "UPDATE Reservations SET statut_res = 'Confirmee' " +
                       "WHERE id_reservation = ? AND statut_res = 'En attente'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idReservation);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // Marquer la table comme Reservee
                reserverTable(idReservation);
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Annuler une réservation par un admin (sans vérifier le client)
    public boolean annulerReservationAdmin(int idReservation) {
        String query = "UPDATE Reservations SET statut_res = 'Annulee' " +
                       "WHERE id_reservation = ? AND statut_res != 'Annulee'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idReservation);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                libererTable(idReservation);
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Marque la table liée à une réservation comme "Reservee"
    private void reserverTable(int idReservation) {
        String query = "UPDATE Tables_Resto SET statut = 'Reservee' " +
                       "WHERE id_table = (SELECT id_table FROM Reservations WHERE id_reservation = ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idReservation);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Libère la table liée à une réservation annulée
    private void libererTable(int idReservation) {
        String query = "UPDATE Tables_Resto SET statut = 'Libre' " +
                       "WHERE id_table = (SELECT id_table FROM Reservations WHERE id_reservation = ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idReservation);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
