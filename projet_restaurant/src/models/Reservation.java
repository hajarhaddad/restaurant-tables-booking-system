package models;

import java.sql.Date;
import java.sql.Time;

public class Reservation {
    private int    idReservation;
    private int    idClient;
    private int    idTable;
    private Date   dateRes;
    private Time   heureRes;
    private int    nbPersonnes;
    private String statutRes;

    // Constructeur pour creation (sans id)
    public Reservation(int idClient, int idTable, Date dateRes,
                       Time heureRes, int nbPersonnes, String statutRes) {
        this.idClient   = idClient;
        this.idTable    = idTable;
        this.dateRes    = dateRes;
        this.heureRes   = heureRes;
        this.nbPersonnes = nbPersonnes;
        this.statutRes  = statutRes;
    }

    // Constructeur complet (avec id, pour lecture BD)
    public Reservation(int idReservation, int idClient, int idTable,
                       Date dateRes, Time heureRes, int nbPersonnes, String statutRes) {
        this(idClient, idTable, dateRes, heureRes, nbPersonnes, statutRes);
        this.idReservation = idReservation;
    }

    public int    getIdReservation() { return idReservation; }
    public int    getIdClient()      { return idClient; }
    public int    getIdTable()       { return idTable; }
    public Date   getDateRes()       { return dateRes; }
    public Time   getHeureRes()      { return heureRes; }
    public int    getNbPersonnes()   { return nbPersonnes; }
    public String getStatutRes()     { return statutRes; }
}
