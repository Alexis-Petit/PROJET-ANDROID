package fr.iutlan.sportable;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by Corentin on 30/03/2017.
 */

public class NouveauSport extends AppCompatActivity implements View.OnClickListener{

    private static final int LOADER_LISTE_SORTIES = 1;

    private BDDOpenHelper helper;
    private SQLiteDatabase bdd;
    private SimpleCursorAdapter adapter_sorties;

    private TextInputLayout til_sport_nom;
    private TextInputLayout til_sport_description;
    private TextInputLayout til_sport_couleur;
    private ImageButton ib_validationNouveauSport;
    private ImageButton ib_cancelNouveauSport;
    //FIXME id temporaire --> trouver le moyen de l'incrémenter
    private final Long ID = Long.valueOf(1);

    private Sport sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_sport);
        til_sport_nom = (TextInputLayout)findViewById(R.id.textInputLayout_sport_nom);
        til_sport_description = (TextInputLayout)findViewById(R.id.textInputLayout_sport_description);
        til_sport_couleur = (TextInputLayout)findViewById(R.id.textInputLayout_sport_couleur);

        ib_validationNouveauSport = (ImageButton) findViewById(R.id.ib_validationNouveauSport);
        ib_validationNouveauSport.setOnClickListener(this);

        ib_cancelNouveauSport = (ImageButton) findViewById(R.id.ib_cancelNouveauSport);
        ib_cancelNouveauSport.setOnClickListener(this);

        // ouvrir la BDD en modification
        helper = new BDDOpenHelper(this);
        bdd = helper.getWritableDatabase();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // fermer la BDD
        helper.close();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ib_validationNouveauSport:
                String nom = til_sport_nom.getEditText().getText().toString();
                String description = til_sport_description.getEditText().getText().toString();
                String couleur = til_sport_couleur.getEditText().getText().toString();
                if(nom.matches("")||description.matches("")||couleur.matches("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Certains champs sont vides", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    this.sport = new Sport(
                            ID,
                            nom,
                            description,
                            couleur
                    );
                    TableSport.insert(bdd, sport);

                    /*Toast toast = Toast.makeText(getApplicationContext(), "appuiyage", Toast.LENGTH_SHORT);
                    toast.show();*/

                    finish();
                }

                break;
            case R.id.ib_cancelNouveauSport:
                finish();
        }
    }
}
