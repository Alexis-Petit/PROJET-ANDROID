package fr.iutlan.sportable;

/**
 * Created by Alexis on 23/02/2017.
 */

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Tab1accueil extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1accueil, container, false);
        initUI(rootView);
        return rootView;
    }

    public void initUI(View v) {
        Button button_nouvelleSortie;
        button_nouvelleSortie = (Button)v.findViewById(R.id.button_nouvelleSortie);
        button_nouvelleSortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "nouvelle sortie !", Snackbar.LENGTH_LONG);
                // /Toast.makeText(getContext(), "button pushed", Toast.LENGTH_SHORT).show();
                snackbar.show();
                Intent intent = new Intent(Tab1accueil.this.getActivity(), NouvelleSortie.class);
                startActivity(intent);
            }
        });
    }


}
