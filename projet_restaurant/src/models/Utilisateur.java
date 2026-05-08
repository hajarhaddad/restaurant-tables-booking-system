package models;

public class Utilisateur {
    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String roleUtilisateur;

    public Utilisateur(int idUtilisateur, String nom, String prenom,
                       String email, String roleUtilisateur) {
        this.idUtilisateur   = idUtilisateur;
        this.nom             = nom;
        this.prenom          = prenom;
        this.email           = email;
        this.roleUtilisateur = roleUtilisateur;
    }

    public int    getIdUtilisateur()   { return idUtilisateur; }
    public String getNom()             { return nom; }
    public String getPrenom()          { return prenom; }
    public String getEmail()           { return email; }
    public String getRoleUtilisateur() { return roleUtilisateur; }

    @Override
    public String toString() {
        return prenom + " " + nom + " [" + roleUtilisateur + "]";
    }
}
