# Système de Gestion Restaurant - Table Booking System
## Java + SQL Server Management Studio (Terminal)

---

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

---

## Prérequis

- Java JDK 11 ou supérieur
- Apache Maven
- SQL Server (SSMS) avec la base GestionRestaurant créée et peuplée

---

## Configuration de la connexion

Dans `src/database/DBConnection.java`, vérifiez :
```java
private static final String URL =
    "jdbc:sqlserver://localhost:1433;databaseName=GestionRestaurant;"
    + "encrypt=true;trustServerCertificate=true;";
private static final String USER = "sa";
private static final String PASSWORD = "Abdou123"; // Changez si besoin
```

---

## Compilation et exécution (Maven)

```bash
# Compiler
mvn compile

# Lancer
mvn exec:java -Dexec.mainClass="main.Main"

# Ou créer un JAR puis lancer
mvn package
java -jar target/gestion-restaurant-1.0.jar
```

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
