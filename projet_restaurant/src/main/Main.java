package main;

import dao.MenuDAO;
import dao.ReservationDAO;
import dao.TableDAO;
import dao.UtilisateurDAO;
import models.Reservation;
import models.Utilisateur;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

public class Main {

    static Scanner scanner       = new Scanner(System.in);
    static UtilisateurDAO userDAO = new UtilisateurDAO();
    static TableDAO        tableDAO = new TableDAO();
    static ReservationDAO  resDAO   = new ReservationDAO();
    static MenuDAO         menuDAO  = new MenuDAO();

    public static void main(String[] args) {
        afficherBanniere();

        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal();
            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1:
                    seConnecter();
                    break;
                case 2:
                    sInscrire();
                    break;
                case 3:
                    System.out.println("\n  Au revoir !\n");
                    continuer = false;
                    break;
                default:
                    System.out.println("  Choix invalide. Reessayez.");
            }
        }
        scanner.close();
    }

    // ─── MENU PRINCIPAL (non connecte) ──────────────────────────────────────

    static void afficherMenuPrincipal() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║    RESTAURANT - ACCUEIL      ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1. Se connecter             ║");
        System.out.println("║  2. S'inscrire               ║");
        System.out.println("║  3. Quitter                  ║");
        System.out.println("╚══════════════════════════════╝");
    }

    // ─── CONNEXION ───────────────────────────────────────────────────────────

    static void seConnecter() {
        System.out.println("\n--- Connexion ---");
        System.out.print("  Email      : ");
        String email = scanner.nextLine().trim();
        System.out.print("  Mot de passe : ");
        String mdp = scanner.nextLine().trim();

        Utilisateur user = userDAO.authentifier(email, mdp);
        if (user == null) {
            System.out.println("  Email ou mot de passe incorrect.\n");
            return;
        }

        System.out.println("\n  Bienvenue, " + user.getPrenom() + " " + user.getNom() + " !");

        if ("ADMIN".equals(user.getRoleUtilisateur())) {
            menuAdmin(user);
        } else {
            menuClient(user);
        }
    }

    // ─── INSCRIPTION ─────────────────────────────────────────────────────────

    static void sInscrire() {
        System.out.println("\n--- Inscription ---");
        System.out.print("  Nom       : ");
        String nom    = scanner.nextLine().trim();
        System.out.print("  Prenom    : ");
        String prenom = scanner.nextLine().trim();
        System.out.print("  Email     : ");
        String email  = scanner.nextLine().trim();
        System.out.print("  Mot de passe : ");
        String mdp    = scanner.nextLine().trim();

        if (userDAO.inscrire(nom, prenom, email, mdp)) {
            System.out.println("  Compte cree avec succes ! Vous pouvez maintenant vous connecter.\n");
        } else {
            System.out.println("  Echec de l'inscription.\n");
        }
    }

    // ─── MENU CLIENT ─────────────────────────────────────────────────────────

    static void menuClient(Utilisateur user) {
        boolean connecte = true;
        while (connecte) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║       ESPACE CLIENT          ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Voir les tables libres   ║");
            System.out.println("║  2. Faire une reservation    ║");
            System.out.println("║  3. Mes reservations         ║");
            System.out.println("║  4. Annuler une reservation  ║");
            System.out.println("║  5. Voir le menu / la carte  ║");
            System.out.println("║  6. Se deconnecter           ║");
            System.out.println("╚══════════════════════════════╝");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1:
                    tableDAO.afficherTablesLibres();
                    break;
                case 2:
                    faireReservation(user);
                    break;
                case 3:
                    resDAO.afficherReservationsClient(user.getIdUtilisateur());
                    break;
                case 4:
                    annulerReservationClient(user);
                    break;
                case 5:
                    menuDAO.afficherMenu();
                    break;
                case 6:
                    System.out.println("  Deconnexion.\n");
                    connecte = false;
                    break;
                default:
                    System.out.println("  Choix invalide.");
            }
        }
    }

    // ─── MENU ADMIN ──────────────────────────────────────────────────────────

    static void menuAdmin(Utilisateur utilisateur) {
        boolean connecte = true;
        while (connecte) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║         ESPACE ADMIN             ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Voir toutes les reservations ║");
            System.out.println("║  2. Confirmer une reservation    ║");
            System.out.println("║  3. Annuler une reservation      ║");
            System.out.println("║  4. Etat de toutes les tables    ║");
            System.out.println("║  5. Voir / gerer le menu         ║");
            System.out.println("║  6. Ajouter un plat au menu      ║");
            System.out.println("║  7. Se deconnecter               ║");
            System.out.println("╚══════════════════════════════════╝");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1:
                    resDAO.afficherToutesLesReservations();
                    break;
                case 2:
                    confirmerReservationAdmin();
                    break;
                case 3:
                    annulerReservationAdmin();
                    break;
                case 4:
                    tableDAO.afficherToutesLesTables();
                    break;
                case 5:
                    afficherMenuAdmin();
                    break;
                case 6:
                    ajouterPlatAdmin();
                    break;
                case 7:
                    System.out.println("  Deconnexion.\n");
                    connecte = false;
                    break;
                default:
                    System.out.println("  Choix invalide.");
            }
        }
    }

    // ─── ACTIONS CLIENT ──────────────────────────────────────────────────────

    static void faireReservation(Utilisateur user) {
        tableDAO.afficherTablesLibres();

        int idTable = lireEntier("  ID de la table choisie : ");
        if (!tableDAO.tableDisponible(idTable)) {
            System.out.println("  Cette table n'est pas disponible.");
            return;
        }

        System.out.print("  Date de reservation (YYYY-MM-DD) : ");
        String dateStr = scanner.nextLine().trim();
        System.out.print("  Heure (HH:MM)                    : ");
        String heureStr = scanner.nextLine().trim();
        int nbPersonnes = lireEntier("  Nombre de personnes             : ");

        try {
            Date dateRes  = Date.valueOf(dateStr);
            Time heureRes = Time.valueOf(heureStr + ":00");

            Reservation res = new Reservation(
                user.getIdUtilisateur(), idTable,
                dateRes, heureRes, nbPersonnes, "En attente"
            );

            if (resDAO.ajouterReservation(res)) {
                System.out.println("\n  Reservation effectuee avec succes ! (En attente de confirmation)");
            } else {
                System.out.println("  Echec de la reservation.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("  Format de date ou d'heure invalide. Utilisez YYYY-MM-DD et HH:MM.");
        }
    }

    static void annulerReservationClient(Utilisateur user) {
        resDAO.afficherReservationsClient(user.getIdUtilisateur());
        int idRes = lireEntier("  ID de la reservation a annuler (0 pour annuler) : ");
        if (idRes == 0) return;

        if (resDAO.annulerReservation(idRes, user.getIdUtilisateur())) {
            System.out.println("  Reservation annulee avec succes.");
        } else {
            System.out.println("  Impossible d'annuler : reservation introuvable ou deja annulee.");
        }
    }

    // ─── ACTIONS ADMIN ───────────────────────────────────────────────────────

    static void confirmerReservationAdmin() {
        resDAO.afficherToutesLesReservations();
        int idRes = lireEntier("  ID de la reservation a confirmer (0 pour annuler) : ");
        if (idRes == 0) return;

        if (resDAO.confirmerReservation(idRes)) {
            System.out.println("  Reservation confirmee avec succes.");
        } else {
            System.out.println("  Impossible de confirmer : reservation introuvable ou deja traitee.");
        }
    }

    static void annulerReservationAdmin() {
        resDAO.afficherToutesLesReservations();
        int idRes = lireEntier("  ID de la reservation a annuler (0 pour annuler l'operation) : ");
        if (idRes == 0) return;

        if (resDAO.annulerReservationAdmin(idRes)) {
            System.out.println("  Reservation annulee par l'admin.");
        } else {
            System.out.println("  Impossible d'annuler : reservation introuvable ou deja annulee.");
        }
    }

    static void afficherMenuAdmin() {
        menuDAO.afficherMenu();
        System.out.println("\n--- Tous les plats (admin) ---");
        menuDAO.getTousLesPlats().forEach(p -> System.out.printf(
            "  ID: %d | %-25s | %.2f MAD | %s%n",
            p.getIdMenu(), p.getNomPlat(), p.getPrix(),
            p.isDisponible() ? "Disponible" : "INDISPONIBLE"
        ));

        int idPlat = lireEntier("\n  ID du plat pour changer sa disponibilite (0 = rien) : ");
        if (idPlat != 0) {
            if (menuDAO.toggleDisponibilite(idPlat)) {
                System.out.println("  Disponibilite mise a jour.");
            } else {
                System.out.println("  Plat introuvable.");
            }
        }
    }

    static void ajouterPlatAdmin() {
        System.out.println("\n--- Ajouter un plat ---");
        System.out.print("  Nom du plat   : ");
        String nom   = scanner.nextLine().trim();
        System.out.print("  Description   : ");
        String desc  = scanner.nextLine().trim();
        double prix  = lireDouble("  Prix (MAD)    : ");

        if (menuDAO.ajouterPlat(nom, desc, prix)) {
            System.out.println("  Plat ajoute avec succes !");
        } else {
            System.out.println("  Echec de l'ajout.");
        }
    }

    // ─── UTILITAIRES ─────────────────────────────────────────────────────────

    static int lireEntier(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Saisie invalide, entrez un entier.");
            }
        }
    }

    static double lireDouble(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("  Saisie invalide, entrez un nombre.");
            }
        }
    }

    static void afficherBanniere() {
        System.out.println();
        System.out.println("  ╔════════════════════════════════════════╗");
        System.out.println("  ║     SYSTEME DE GESTION RESTAURANT      ║");
        System.out.println("  ║         Table Booking System           ║");
        System.out.println("  ╚════════════════════════════════════════╝");
        System.out.println();
    }
}
