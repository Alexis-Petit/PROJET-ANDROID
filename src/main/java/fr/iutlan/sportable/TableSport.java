package fr.iutlan.sportable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by allenomi on 06/03/17.
 */
public class TableSport {
    private static final int num_champ_nom = 1;
    private static final int num_champ_description = 2;
    private static final int num_champ_couleur = 4;

    private static final String NOM = "Sport";

    public static void create(SQLiteDatabase bdd)
    {
        bdd.execSQL( "CREATE TABLE "+NOM+" ("              +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"    +
                "nom TEXT NOT NULL,"                        +
                "description TEXT NOT NULL,"                +
                "couleur TEXT NOT NULL)");

        // mise au point
        bdd.execSQL("INSERT INTO Sport VALUES (1, 'Velo', 'un truc à deux roues','Jaune')");
        bdd.execSQL("INSERT INTO Sport VALUES (2, 'Planche à voile','Attention à ne pas senvoler', 'Bleu')");
    }

    public static void drop(SQLiteDatabase bdd)
    {
        bdd.execSQL("DROP TABLE IF EXISTS CASCADE "+NOM);
    }

    public static Cursor getAll(SQLiteDatabase bdd)
    {
        return bdd.rawQuery("SELECT * FROM " + NOM, null);
    }

    public static Sport getSport(SQLiteDatabase bdd, long id)
    {
        Cursor cursor = bdd.rawQuery(
                "SELECT * FROM " + NOM + " WHERE _id=?",
                new String[]{Long.toString(id)});
        if (cursor == null) return null;
        try {
            if (cursor.moveToFirst() && !cursor.isNull(0)) {
                Sport sport = new Sport();
                sport.setId(id);
                sport.setNom(cursor.getString(num_champ_nom));
                sport.setDescription(cursor.getString(num_champ_description));
                return sport;
            } else return null;
        } finally {
            cursor.close();
        }
    }

    public static long insert(SQLiteDatabase bdd, Sport sport)
    {
        ContentValues valeurs = new ContentValues();
        valeurs.put("nom", sport.getNom());
        valeurs.put("description", sport.getDescription());
        valeurs.put("couleur", sport.getCouleur());


        long id = bdd.insert(NOM, null, valeurs);
        return id;
    }

    public static void update(SQLiteDatabase bdd, Sport sport)
    {
        ContentValues valeurs = new ContentValues();
        valeurs.put("nom", sport.getNom());
        valeurs.put("description", sport.getDescription());
        valeurs.put("couleur", sport.getCouleur());

        bdd.update(NOM, valeurs, "_id=?", new String[] { Long.toString(sport.getId())});
    }

    public static void delete(SQLiteDatabase bdd, Sport sport)
    {
        long id = bdd.delete(NOM, "_id = ?" ,new String[] {String.valueOf(sport.getId())});
    }
}
