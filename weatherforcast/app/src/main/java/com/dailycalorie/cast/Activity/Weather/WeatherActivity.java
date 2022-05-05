package com.dailycalorie.cast.Activity.Weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dailycalorie.cast.R;
import com.dailycalorie.cast.FavDestination.ObjectSerializer;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {
    EditText etCity, etCountry;
    TextView tvResult;
    private final String url = "https://api.openweathermap.org/data/2.5/weather"; //open weather api
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df = new DecimalFormat("#.##");

    ListView listView;
    static ArrayList<String> cityArrayList;
    static ArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        etCity = findViewById(R.id.etCity);
        // etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult);

        listView = (ListView) findViewById(R.id.cityList);

        cityArrayList = new ArrayList<>();

        //get stored values from shared preferences
        SharedPreferences sp = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        cityArrayList.clear();

        try {
            cityArrayList = (ArrayList<String>) ObjectSerializer.deserialize(sp.getString("c", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        cityAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cityArrayList);
        listView.setAdapter(cityAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                etCity.setText(cityArrayList.get(i).toString(), TextView.BufferType.EDITABLE);
                getWeatherDetails(view);
            }
        });


    }

    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        // String country = etCountry.getText().toString().trim();
        if (city.equals("")) {
            tvResult.setText("City field can not be empty!");
        } else {

            tempUrl = url + "?q=" + city + "&appid=" + appid;

        }

        //request from json object
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");
                    tvResult.setTextColor(Color.rgb(68, 134, 199));
                    output += "Current weather of " + cityName + " (" + countryName + ")"
                            + "\n Temp: " + df.format(temp) + " °C"
                            + "\n Feels Like: " + df.format(feelsLike) + " °C"
                            + "\n Humidity: " + humidity + "%"
                            + "\n Description: " + description
                            + "\n Wind Speed: " + wind + "m/s (meters per second)"
                            + "\n Cloudiness: " + clouds + "%"
                            + "\n Pressure: " + pressure + " hPa";
                    tvResult.setText(output);

                    if (!cityArrayList.contains(cityName)) {
                        cityArrayList.add(cityName);
                        cityAdapter.notifyDataSetChanged();

                        //store in sharedPreferences
                        SharedPreferences sp = WeatherActivity.this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();

                        try {

                            ed.putString("c", ObjectSerializer.serialize(cityArrayList
                            ));
                            ed.commit();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
