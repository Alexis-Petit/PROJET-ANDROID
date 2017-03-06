package iutlan.fr.sports2000;

/**
 * Created by allenomi on 30/01/17.
 */
public class Sortie {

    private long id;
    private String date;
    private String lieu;
    private String infos;
    private String notes;

    public Sortie()
    {
        // identifiant invalide => création
        this.id = -1;

        // mettre les valeurs par défaut, ex: date=aujourd'hui
    }

    public Sortie(long id,String date,String lieu,String infos,String notes)
    {
        this.id=id;
        this.date=date;
        this.infos=infos;
        this.lieu=lieu;
        this.notes=notes;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public String getInfos() {
        return infos;
    }

    public String getLieu() {
        return lieu;
    }

    public String getNotes() {
        return notes;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Sortie"+getId()+"le "+getDate()+"à "+getLieu()+"vous avez mis"+getNotes()+".\n Voici les infos suplémentaires"+getInfos();
    }


}

