package fr.iutlan.sportable;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

public class EditSortieActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final String TAG = "Hello";

    private BDDOpenHelper helper;
    private SQLiteDatabase bdd;
    Sortie sortie;

    // vues de l'interface
    EditText etInfos;
    EditText etLieu;
    EditText etDate;
    RatingBar rbNote;

    ImageButton ib_supprimerEditSortie;
    ImageButton ib_cancelEditSortie;
    ImageButton ib_validationEditSortie;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sortie);

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
        etLieu = (EditText) findViewById(R.id.sortie_lieu);
        etLieu.setText(sortie.getLieu());
        etDate = (EditText) findViewById(R.id.sortie_date);
        etDate.setText(sortie.getDate());
        rbNote = (RatingBar) findViewById(R.id.sortie_note);
        String note = sortie.getNotes();
        rbNote.setRating(Float.parseFloat(note));

        ib_validationEditSortie = (ImageButton)findViewById(R.id.ib_validationEditSortie);
        ib_cancelEditSortie = (ImageButton)findViewById(R.id.ib_cancelEditSortie);
        ib_supprimerEditSortie = (ImageButton)findViewById(R.id.ib_supprimerEditSortie);

        ib_validationEditSortie.setOnClickListener(this);
        ib_cancelEditSortie.setOnClickListener(this);
        ib_supprimerEditSortie.setOnClickListener(this);
    }



    // appelée quand on clique sur une sortie
    public void onValider()
    {
        // relire les valeurs des vues
        sortie.setDate(etDate.getText().toString());   // à lire dans les vues de l'interface, voir infos ci-dessous
        sortie.setLieu(etLieu.getText().toString());
        sortie.setInfos(etInfos.getText().toString());
        sortie.setNotes(String.valueOf(rbNote.getRating()));

        if (sortie.getId() < 0) {
            TableSorties.insert(bdd, sortie);
        } else {
            TableSorties.update(bdd, sortie);
        }

        // sortir de l'activité en indiquant une réussite
        setResult(RESULT_OK);
        finish();
    }

    /**
     * appelée pour créer le menu de l'activité
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nouvelle_sortie, menu);
        return true;
    }

    /**
     * appelée quand on clique sur un élément de menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.ib_validationEditSortie:
                // l'utilisateur a choisi le menu valider : retourner l'item et son identifiant à l'activité
                onValider();
                finish();
                return true;

            case R.id.ib_cancelEditSortie:
                // l'utilisateur a choisi le menu annuler : terminer l'activité sans modifier la liste
                setResult(RESULT_CANCELED);
                finish();
                return true;

            case R.id.ib_supprimerEditSortie:
                //l'utilisateur a choisi le menu supprimer
                TableSorties.delete(bdd, sortie);
                setResult(RESULT_OK);
                finish();
                return true;

            default:
                return true;
        }
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy();

        // fermer la BDD
        helper.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_validationEditSortie:
                // l'utilisateur a choisi le menu valider : retourner l'item et son identifiant à l'activité
                onValider();
                finish();
                break;
            case R.id.ib_cancelEditSortie:
                // l'utilisateur a choisi le menu annuler : terminer l'activité sans modifier la liste
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.ib_supprimerEditSortie:
                //l'utilisateur a choisi le menu supprimer
                TableSorties.delete(bdd, sortie);
                setResult(RESULT_OK);
                finish();
                break;

            default:
                break;
        }
    }
}