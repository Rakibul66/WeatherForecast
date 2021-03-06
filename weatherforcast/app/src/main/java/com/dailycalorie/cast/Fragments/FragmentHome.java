package com.dailycalorie.cast.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.dailycalorie.cast.Activity.CurrentLoc.CurrentLocActivity;
import com.dailycalorie.cast.FavDestination.FavDestinationActivity;
import com.dailycalorie.cast.R;
import com.dailycalorie.cast.compass.CompassActivity;


public class FragmentHome extends Fragment {

    View view;
    CardView one, two, three, four, five, six;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        one = view.findViewById(R.id.compass); //cal or define id from layout
        two = view.findViewById(R.id.loc);
        three= view.findViewById(R.id.Fav);


        onClickListeners();

        return view;

    }

    //call setonclick listener for going compass,currentloc,favdestination activity
    private void onClickListeners(){
        one.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), CompassActivity.class);
            startActivity(intent);
        });
        two.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), CurrentLocActivity.class);
            startActivity(intent);
        });
        three.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), FavDestinationActivity.class);
            startActivity(intent);
        });
    }
}
