package models;

import java.time.LocalDateTime;

public class AnnonceSortie {
    private int id;
    private int creatorId;
    private String titre;
    private String description;
    private String ville;
    private String lieu;
    private String typeActivite;
    private LocalDateTime dateSortie;
    private double budgetMax;
    private int nbPlaces;
    private String statut; // OUVERT(E) / CLOTUREE / ANNULEE

    public AnnonceSortie() {}

    // Add
    public AnnonceSortie(int creatorId, String titre, String description, String ville, String lieu,
                         String typeActivite, LocalDateTime dateSortie, double budgetMax, int nbPlaces, String statut) {
        this.creatorId = creatorId;
        this.titre = titre;
        this.description = description;
        this.ville = ville;
        this.lieu = lieu;
        this.typeActivite = typeActivite;
        this.dateSortie = dateSortie;
        this.budgetMax = budgetMax;
        this.nbPlaces = nbPlaces;
        this.statut = statut;
    }

    // Update
    public AnnonceSortie(int id, int creatorId, String titre, String description, String ville, String lieu,
                         String typeActivite, LocalDateTime dateSortie, double budgetMax, int nbPlaces, String statut) {
        this(creatorId, titre, description, ville, lieu, typeActivite, dateSortie, budgetMax, nbPlaces, statut);
        this.id = id;
    }

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCreatorId() { return creatorId; }
    public void setCreatorId(int creatorId) { this.creatorId = creatorId; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getTypeActivite() { return typeActivite; }
    public void setTypeActivite(String typeActivite) { this.typeActivite = typeActivite; }

    public LocalDateTime getDateSortie() { return dateSortie; }
    public void setDateSortie(LocalDateTime dateSortie) { this.dateSortie = dateSortie; }

    public double getBudgetMax() { return budgetMax; }
    public void setBudgetMax(double budgetMax) { this.budgetMax = budgetMax; }

    public int getNbPlaces() { return nbPlaces; }
    public void setNbPlaces(int nbPlaces) { this.nbPlaces = nbPlaces; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}