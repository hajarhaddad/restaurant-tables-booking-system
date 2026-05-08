package models;

public class Menu {
    private int     idMenu;
    private String  nomPlat;
    private String  descriptionPlat;
    private double  prix;
    private boolean disponible;

    public Menu(int idMenu, String nomPlat, String descriptionPlat,
                double prix, boolean disponible) {
        this.idMenu          = idMenu;
        this.nomPlat         = nomPlat;
        this.descriptionPlat = descriptionPlat;
        this.prix            = prix;
        this.disponible      = disponible;
    }

    public int     getIdMenu()          { return idMenu; }
    public String  getNomPlat()         { return nomPlat; }
    public String  getDescriptionPlat() { return descriptionPlat; }
    public double  getPrix()            { return prix; }
    public boolean isDisponible()       { return disponible; }

    @Override
    public String toString() {
        return String.format("%-25s | %-35s | %.2f MAD | %s",
            nomPlat, descriptionPlat, prix,
            disponible ? "Disponible" : "Indisponible");
    }
}
