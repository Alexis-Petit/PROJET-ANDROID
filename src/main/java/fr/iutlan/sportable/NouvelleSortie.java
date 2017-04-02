package fr.iutlan.sportable;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class NouvelleSortie extends AppCompatActivity implements View.OnClickListener
{

    public static final String TAG = "Hello";

    // identifiant du chargeur (utile s'il y en a plusieurs)
    private static final int LOADER_LISTE_SORTIES = 1;

    private BDDOpenHelper helper;
    private SQLiteDatabase bdd;
    private SimpleCursorAdapter adapter_sorties;

    private TextInputLayout til_date;
    private TextInputLayout til_lieu;
    private TextInputLayout til_infos;
    private RatingBar rb_note;
    private ImageButton ib_validationNouvelleSortie;
    private ImageButton ib_cancelNouvelleSortie;
    //FIXME id temporaire --> trouver le moyen de l'incrémenter
    private final Long ID = Long.valueOf(1);

    private Sortie sortie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_sortie);
        til_date = (TextInputLayout)findViewById(R.id.textInputLayout_date);
        til_lieu = (TextInputLayout)findViewById(R.id.textInputLayout_lieu);
        til_infos = (TextInputLayout)findViewById(R.id.textInputLayout_infos);

        rb_note = (RatingBar)findViewById(R.id.rb_note);


        ib_validationNouvelleSortie = (ImageButton) findViewById(R.id.ib_validationNouvelleSortie);
        ib_validationNouvelleSortie.setOnClickListener(this);

        ib_cancelNouvelleSortie = (ImageButton) findViewById(R.id.ib_cancelNouvelleSortie);
        ib_cancelNouvelleSortie.setOnClickListener(this);





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
            case R.id.ib_validationNouvelleSortie:
                String date = til_date.getEditText().getText().toString();
                String lieu = til_lieu.getEditText().getText().toString();
                String infos = til_infos.getEditText().getText().toString();
                Float tmpnote = rb_note.getRating();
                String note = String.valueOf(tmpnote);
                if(date.matches("")||lieu.matches("")||infos.matches("")||note.matches("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Certains champs sont vides", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    this.sortie = new Sortie(
                            ID,
                            date,
                            lieu,
                            infos,
                            note);
                    TableSorties.insert(bdd, sortie);

                    /*Toast toast = Toast.makeText(getApplicationContext(), "appuiyage", Toast.LENGTH_SHORT);
                    toast.show();*/

                    finish();
                }

                break;
            case R.id.ib_cancelNouvelleSortie:
                finish();
        }
    }
}