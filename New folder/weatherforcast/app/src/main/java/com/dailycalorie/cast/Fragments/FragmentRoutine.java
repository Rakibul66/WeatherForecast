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

import com.dailycalorie.cast.Activity.Weather.WeatherActivity;
import com.dailycalorie.cast.Forecast.ForecastActivity;
import com.dailycalorie.cast.R;


public class FragmentRoutine extends Fragment {

    CardView weather,forecast; //define variable

    View view; //create view

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);

     weather=view.findViewById(R.id.weather); //define id
     forecast=view.findViewById(R.id.Fav);
    //call setonclick listener for going weather activity
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
            }
        });
//call setonclick listener for going forecast activity
        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForecastActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
