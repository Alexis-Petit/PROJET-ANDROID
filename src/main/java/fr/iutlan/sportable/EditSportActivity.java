package fr.iutlan.sportable;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

/**
 * Created by Corentin on 02/04/2017.
 */

public class EditSportActivity extends AppCompatActivity implements View.OnClickListener{

    private BDDOpenHelper helper;
    private SQLiteDatabase bdd;
    Sport sport;

    // vues de l'interface
    EditText etSportNom;
    EditText etSportDesc;
    EditText etSportCouleur;

    ImageButton ib_supprimerEditSport;
    ImageButton ib_cancelEditSport;
    ImageButton ib_validationEditSport;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sport);

        // ouvrir la BDD en modification
        helper = new BDDOpenHelper(this);
        bdd = helper.getWritableDatabase();

        // récupérer l'identifiant de la sortie (ou -1 s'il faut la créer)
        Intent intent = getIntent();
        long idSport = intent.getLongExtra("idSport", -1);
        if (idSport < 0) {
            // créer la sortie
            sport = new Sport();
        } else {
            // récupérer la sortie de la BDD
            sport = TableSport.getSport(bdd, idSport);
        }

        // afficher la sortie sur les Vues de l'interface
        etSportNom = (EditText) findViewById(R.id.sport_edit_nom);
        etSportNom.setText(sport.getNom());
        etSportDesc = (EditText) findViewById(R.id.sport_edit_desc);
        etSportDesc.setText(sport.getDescription());
        etSportCouleur = (EditText) findViewById(R.id.sport_edit_couleur);
        etSportCouleur.setText(sport.getCouleur());

        ib_validationEditSport = (ImageButton)findViewById(R.id.ib_validationEditSport);
        ib_cancelEditSport = (ImageButton)findViewById(R.id.ib_cancelEditSport);
        ib_supprimerEditSport = (ImageButton)findViewById(R.id.ib_supprimerEditSport);

        ib_validationEditSport.setOnClickListener(this);
        ib_cancelEditSport.setOnClickListener(this);
        ib_supprimerEditSport.setOnClickListener(this);
    }



    // appelée quand on clique sur une sortie
    public void onValider()
    {
        // relire les valeurs des vues
        sport.setNom(etSportNom.getText().toString());   // à lire dans les vues de l'interface, voir infos ci-dessous
        sport.setDescription(etSportDesc.getText().toString());
        sport.setCouleur(etSportCouleur.getText().toString());

        if (sport.getId() < 0) {
            TableSport.insert(bdd, sport);
        } else {
            TableSport.update(bdd, sport);
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

            case R.id.ib_validationEditSport:
                // l'utilisateur a choisi le menu valider : retourner l'item et son identifiant à l'activité
                onValider();
                finish();
                return true;

            case R.id.ib_cancelEditSport:
                // l'utilisateur a choisi le menu annuler : terminer l'activité sans modifier la liste
                setResult(RESULT_CANCELED);
                finish();
                return true;

            case R.id.ib_supprimerEditSortie:
                //l'utilisateur a choisi le menu supprimer
                TableSport.delete(bdd, sport);
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
            case R.id.ib_validationEditSport:
                // l'utilisateur a choisi le menu valider : retourner l'item et son identifiant à l'activité
                onValider();
                finish();
                break;
            case R.id.ib_cancelEditSport:
                // l'utilisateur a choisi le menu annuler : terminer l'activité sans modifier la liste
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.ib_supprimerEditSport:
                //l'utilisateur a choisi le menu supprimer
                TableSport.delete(bdd, sport);
                setResult(RESULT_OK);
                finish();
                break;

            default:
                break;
        }
    }

}
