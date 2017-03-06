package iutlan.fr.sports2000;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


 public class BDDOpenHelper extends SQLiteOpenHelper
 {
     // nom du fichier contenant la base de données
     private static final String DB_NAME = "bddSortie.db";

     // version du schéma de la base de données
     private static final int DB_VERSION = 1;

     // constructeur du helper = ouvre et crée/màj la base
     public BDDOpenHelper(Context context)
     {
         super(context, DB_NAME, null, DB_VERSION);
     }

    @Override
    public void onCreate(SQLiteDatabase bdd)
    {
        TableSorties.create(bdd);
        //TableMachin.create(bdd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bdd, int i, int i1)
    {
        //TableMachin.drop(bdd);
        TableSorties.drop(bdd);

        onCreate(bdd);
    }
}
