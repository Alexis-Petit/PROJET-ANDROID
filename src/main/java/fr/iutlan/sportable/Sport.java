package fr.iutlan.sportable;

/**
 * Created by allenomi on 06/03/17.
 */
public class Sport {
    private long id;
    private String nom;
    private String description;
    private String couleur;


    public Sport(){
        this.id=-1;
    }

    public Sport(long id, String nom,String description, String couleur){
        this.id=id;
        this.nom=nom;
        this.description=description;
        this.couleur=couleur;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String ToString(){
        return "Sport" + getNom() + ": " + getDescription();
    }
}
