package models;

public class TableResto {
    private int    idTable;
    private int    numeroTable;
    private int    capacite;
    private String statut;

    public TableResto(int idTable, int numeroTable, int capacite, String statut) {
        this.idTable     = idTable;
        this.numeroTable = numeroTable;
        this.capacite    = capacite;
        this.statut      = statut;
    }

    public int    getIdTable()     { return idTable; }
    public int    getNumeroTable() { return numeroTable; }
    public int    getCapacite()    { return capacite; }
    public String getStatut()      { return statut; }

    @Override
    public String toString() {
        return "Table N°" + numeroTable + " | Capacite: " + capacite +
               " personnes | Statut: " + statut;
    }
}
