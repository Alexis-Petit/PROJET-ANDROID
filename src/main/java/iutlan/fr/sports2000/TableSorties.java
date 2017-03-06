package iutlan.fr.sports2000;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by allenomi on 27/02/17.
 */
public class TableSorties
{
    private static final int num_champ_date = 1;
    private static final int num_champ_lieu = 2;
    private static final int num_champ_infos = 3;
    private static final int num_champ_notes = 4;

    private static final String NOM = "Sorties";


    public static void create(SQLiteDatabase bdd)
    {
        bdd.execSQL( "CREATE TABLE "+NOM+" ("              +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"    +
                "date TEXT NOT NULL,"                        +
                "lieu TEXT NOT NULL,"                        +
                "infos TEXT NOT NULL,"                        +
                "notes TEXT NOT NULL)");

        // mise au point
        bdd.execSQL("INSERT INTO Sorties VALUES (1, '27/02/2017', 'Lannion', 'esssai', 'très bien')");
        bdd.execSQL("INSERT INTO Sorties VALUES (2, '27/02/2017', 'Lannion', 'essai2', 'trop bien')");
    }

    public static void drop(SQLiteDatabase bdd)
    {
        bdd.execSQL("DROP TABLE IF EXISTS CASCADE "+NOM);
    }

    public static Cursor getAll(SQLiteDatabase bdd)
    {
        return bdd.rawQuery("SELECT * FROM " + NOM, null);
    }

    public static Sortie getSortie(SQLiteDatabase bdd, long id)
    {
        Cursor cursor = bdd.rawQuery(
                "SELECT * FROM " + NOM + " WHERE _id=?",
                new String[]{Long.toString(id)});
        if (cursor == null) return null;
        try {
            if (cursor.moveToFirst() && !cursor.isNull(0)) {
                Sortie sortie = new Sortie();
                sortie.setId(id);
                sortie.setDate(cursor.getString(num_champ_date));
                sortie.setLieu(cursor.getString(num_champ_lieu));
                sortie.setInfos(cursor.getString(num_champ_infos));
                sortie.setNotes(cursor.getString(num_champ_notes));
                return sortie;
            } else return null;
        } finally {
            cursor.close();
        }
    }

    /* inutile
    public static String getDate(SQLiteDatabase bdd, long id)
    {
        Cursor cursor = bdd.rawQuery(
                "SELECT date FROM " + NOM + " WHERE _id=?",
                new String[] {Long.toString(id)});
        if (cursor == null) return null;
        try {
            if (cursor.moveToFirst() && !cursor.isNull(0)) {
                return cursor.getString(0);
            } else
                return null;
        } finally {
            cursor.close();
        }
    }
    */

    public static long insert(SQLiteDatabase bdd, Sortie sortie)
    {
        ContentValues valeurs = new ContentValues();
        valeurs.put("date", sortie.getDate());
        valeurs.put("lieu", sortie.getLieu());
        valeurs.put("infos", sortie.getInfos());
        valeurs.put("notes", sortie.getNotes());

        long id = bdd.insert(NOM, null, valeurs);
        return id;
    }

    public static void update(SQLiteDatabase bdd, Sortie sortie)
    {
        ContentValues valeurs = new ContentValues();
        valeurs.put("date", sortie.getDate());
        valeurs.put("lieu", sortie.getLieu());
        valeurs.put("infos", sortie.getInfos());
        valeurs.put("notes", sortie.getNotes());

        bdd.update(NOM, valeurs, "_id=?", new String[] { Long.toString(sortie.getId())});
    }
}
