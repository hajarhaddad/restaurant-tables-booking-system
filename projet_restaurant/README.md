# Système de Gestion Restaurant - Table Booking System
## Java + SQL Server Management Studio (Terminal)

<img width="572" height="304" alt="image" src="https://github.com/user-attachments/assets/d91ba953-2e28-4ead-8317-5cf7422ac4fb" />


## Structure du projet

```
projet_restaurant/
├── pom.xml
└── src/
    ├── database/
    │   └── DBConnection.java       ← Connexion JDBC
    ├── models/
    │   ├── Utilisateur.java
    │   ├── TableResto.java
    │   ├── Reservation.java
    │   └── Menu.java
    ├── dao/
    │   ├── UtilisateurDAO.java     ← Authentification + inscription
    │   ├── TableDAO.java           ← Gestion des tables
    │   ├── ReservationDAO.java     ← CRUD réservations
    │   └── MenuDAO.java            ← Gestion de la carte
    └── main/
        └── Main.java               ← Point d'entrée + menus terminal
```

🍽️ Projet de Gestion de Restaurant (Java & SQL Server)
Ce projet est une application en ligne de commande (terminal) permettant de gérer les tables, les réservations, le menu et les utilisateurs d'un restaurant.
Il combine la Conception Orientée Objet (Java) et une base de données relationnelle (SQL Server) via l'API JDBC.
L'architecture du projet est soigneusement découpée en plusieurs couches (packages) pour séparer les responsabilités.

🏗️ Architecture du Projet
1. Couche database/ (Connexion)
Cette couche contient la configuration vitale pour lier l'application à la base de données.
DBConnection.java : C'est le fichier le plus fondamental. Son seul rôle est d'établir la connexion entre Java et SQL Server. Il contient l'URL du serveur (localhost:1433), le nom de la base de données (GestionRestaurant), le login et le mot de passe. Tous les autres fichiers passent par lui pour parler à la base de données via la méthode DBConnection.getConnection().

3. Couche models/ (Les Entités)
Ce sont des classes simples (POJO) qui représentent les données de la base sous forme d'objets Java. Elles ne contiennent aucune logique SQL.
Utilisateur.java : Représente une ligne de la table Utilisateurs. Il stocke l'id, le nom, le prénom, l'email et le rôle (CLIENT ou ADMIN).
TableResto.java : Représente une ligne de Tables_Resto. Il stocke l'id de la table, son numéro, sa capacité et son statut (Libre / Occupée / Réservée).
Reservation.java : Représente une ligne de Reservations. Il stocke l'id du client, la table réservée, la date et l'heure, le nombre de personnes, et le statut de la réservation (En attente / Confirmée / Annulée).
Menu.java : Représente un plat de la table Menu. Il stocke le nom du plat, sa description, son prix et sa disponibilité.

5. Couche dao/ (Data Access Object)
Ces fichiers contiennent toutes les requêtes SQL (CRUD). Chaque classe DAO correspond à la gestion d'une table spécifique.
UtilisateurDAO.java : Gère les utilisateurs.
authentifier() : Vérifie l'email et le mot de passe et retourne un objet Utilisateur.
inscrire() : Insère un nouveau client dans la base.
TableDAO.java : Gère les tables du restaurant.
afficherTablesLibres() : Affiche dans le terminal uniquement les tables disponibles.
tableDisponible() : Vérifie si une table peut être réservée.
afficherToutesLesTables() : Liste toutes les tables (pour l'administrateur).
ReservationDAO.java : Le DAO le plus complet pour la logique métier.
ajouterReservation() : Insère une nouvelle réservation.
afficherReservationsClient() : Liste les réservations liées à un client spécifique.
confirmerReservation() / annulerReservation() : Modifient le statut d'une réservation.
libererTable() / reserverTable() (méthodes privées) : Mettent à jour automatiquement le statut des tables associées.
MenuDAO.java : Gère la carte du restaurant.
afficherMenu() : Affiche la liste des plats aux clients.
ajouterPlat() : Permet à l'admin d'ajouter de nouveaux plats.
toggleDisponibilite() : Active ou désactive un plat du menu.

7. Couche main/ (L'interface Utilisateur)
Main.java : C'est le chef d'orchestre de l'application. Il ne fait aucune requête SQL lui-même. Son rôle est de gérer l'interaction avec l'utilisateur dans le terminal via la classe Scanner.
Il contient la logique d'affichage avec trois vues principales :
Menu d'accueil : Connexion ou Inscription.
Menu CLIENT : Réserver, voir le menu, voir ses réservations.
Menu ADMIN : Gérer les tables, les menus et les réservations de tous les clients.

🔄 Résumé du flux d'exécution
Voici comment les données transitent dans l'application lorsque vous effectuez une action :
L'application démarre et Main s'exécute.
Le programme affiche un menu interactif dans le terminal.
L'utilisateur tape un choix au clavier (via Scanner).
Main analyse le choix et fait appel à la méthode correspondante dans le bon DAO.
Le DAO fait appel à DBConnection pour obtenir une connexion active.
Le DAO exécute sa requête SQL en base de données.
Le DAO récupère le résultat, le transforme potentiellement en objet Model, et renvoie l'information ou l'affiche directement dans le terminal.

---

## Comptes de test

| Rôle  | Email              | Mot de passe |
|-------|--------------------|--------------|
| ADMIN | admin@resto.com    | admin123     |
| CLIENT| houda@mail.com     | client123    |

---

## Fonctionnalités

### Espace CLIENT
- Voir les tables disponibles
- Faire une réservation (date, heure, nb personnes)
- Voir mes réservations
- Annuler une réservation
- Consulter la carte du restaurant

### Espace ADMIN
- Voir toutes les réservations (via la vue SQL Vue_Details_Reservations)
- Confirmer une réservation (passe la table en "Reservee")
- Annuler une réservation
- Voir l'état de toutes les tables
- Gérer le menu (activer/désactiver un plat)
- Ajouter un nouveau plat
