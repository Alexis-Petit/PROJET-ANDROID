package iutlan.fr.sports2000;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class EditActivity extends AppCompatActivity
{

    public static final String TAG = "Hello";

    private BDDOpenHelper helper;
    private SQLiteDatabase bdd;
    Sortie sortie;

    // vues de l'interface
    EditText etInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // ouvrir la BDD en modification
        helper = new BDDOpenHelper(this);
        bdd = helper.getWritableDatabase();

        // récupérer l'identifiant de la sortie (ou -1 s'il faut la créer)
        Intent intent = getIntent();
        long idSortie = intent.getLongExtra("idSortie", -1);
        if (idSortie < 0) {
            // créer la sortie
            sortie = new Sortie();
        } else {
            // récupérer la sortie de la BDD
            sortie = TableSorties.getSortie(bdd, idSortie);
        }

        // afficher la sortie sur les Vues de l'interface
        etInfos = (EditText) findViewById(R.id.sortie_infos);
        etInfos.setText(sortie.getInfos());
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // fermer la BDD
        helper.close();
    }

    // appelée quand on clique sur une sortie
    public void onValider(View btn)
    {
        // relire les valeurs des vues
        sortie.setDate("28/02/2017");   // à lire dans les vues de l'interface, voir infos ci-dessous
        sortie.setLieu("Perros");
        sortie.setInfos(etInfos.getText().toString());
        sortie.setNotes("tout va bien");

        if (sortie.getId() < 0) {
            TableSorties.insert(bdd, sortie);
        } else {
            TableSorties.update(bdd, sortie);
        }

        // sortir de l'activité en indiquant une réussite
        setResult(RESULT_OK);
        finish();
    }
}