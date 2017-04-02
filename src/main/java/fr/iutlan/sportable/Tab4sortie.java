package fr.iutlan.sportable;

/**
 * Created by Alexis on 23/02/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class Tab4sortie extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    // identifiant du chargeur (utile s'il y en a plusieurs)
    private static final int LOADER_LISTE_SORTIES = 1;

    private BDDOpenHelper helper;
    private SQLiteDatabase bdd;
    private android.widget.SimpleCursorAdapter adapter_sorties;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.tab4sortie, container, false);


        // ouvrir la BDD en consultation
        helper = new BDDOpenHelper(getActivity());
        bdd = helper.getReadableDatabase();

        /// associer la liste des sorties à la BDD

        // créer un adaptateur
        adapter_sorties = new android.widget.SimpleCursorAdapter(
                getActivity(),
                // layout des éléments de la liste
                android.R.layout.simple_list_item_2,
                // le curseur sera chargé par le loader
                null,
                // noms des colonnes à afficher
                new String[]{"date","infos"},
                // identifiants des TextView qui affichent les colonnes
                new int[]{android.R.id.text1, android.R.id.text2},
                0); // options, toujours mettre 0
        return mview;
    }

    @Override
    public void onStart() {
        super.onStart();
        // associer cet adaptateur au listview de l'interface
        ListView lv = (ListView) getView().findViewById(R.id.liste_sorties);
        lv.setAdapter(adapter_sorties);

        // ajouter un écouteur pour les clics sur la liste
        lv.setOnItemClickListener(this);

        // crée et démarre un chargeur pour cette liste
        getActivity().getSupportLoaderManager().initLoader(LOADER_LISTE_SORTIES, null, this);
    }


    // appelée quand on clique sur une sortie
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        // lancer l'activité d'édition sur cette sortie
        Intent intent = new Intent(getActivity()    , EditSortieActivity.class);
        intent.putExtra("idSortie", l);
        startActivityForResult(intent, 1);
        //getActivity().finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new SortiesCursorLoader(getActivity().getApplicationContext(), bdd);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        adapter_sorties.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        adapter_sorties.changeCursor(null);
    }


    static private class SortiesCursorLoader extends CursorLoader
    {
        private SQLiteDatabase bdd;

        public SortiesCursorLoader(Context context, SQLiteDatabase bdd) {
            super(context);
            this.bdd = bdd;
        }

        @Override
        protected Cursor onLoadInBackground() {
            return TableSorties.getAll(bdd);
        }

    }

    // en cas de retour d'une autre activité (EditActivity ou autre)
    //FIXME changement de 'protected' vers 'public'
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // uti a fait back
        if (resultCode == Activity.RESULT_CANCELED) return;

        // mettre à jour la liste
        getActivity().getSupportLoaderManager().restartLoader(LOADER_LISTE_SORTIES, null, this);
    }


}




