package com.example.weatherapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.Common.Common;
import com.example.weatherapp.Model.WeatherResult;
import com.example.weatherapp.Retrofit.IOpenWeatherMap;
import com.example.weatherapp.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {

    static TodayWeatherFragment instance;
    ImageView img_weather;
    TextView txt_city_name, txt_wind, txt_humidity, txt_sunrise, txt_sunset, txt_pressure,
            txt_geo_coord, txt_description, txt_temperature, txt_date_time,txt_mintemp,txt_maxtemp,txt_sealevel,txt_discriptioncloud;
    LinearLayout weather_panel;
    ProgressDialog progressDialog;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    public static TodayWeatherFragment getInstance() {

        if (instance == null)
            instance = new TodayWeatherFragment();

        return instance;
    }

    public TodayWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemview = inflater.inflate(R.layout.fragment_today_weather, container, false);
        img_weather = itemview.findViewById(R.id.img_weather);
        txt_city_name = itemview.findViewById(R.id.txt_city_name);
        txt_wind = itemview.findViewById(R.id.txt_windspeed);
        txt_humidity = itemview.findViewById(R.id.txt_humidity);
        txt_sunrise = itemview.findViewById(R.id.txt_sunrise);
        txt_sunset = itemview.findViewById(R.id.txt_sunset);
        txt_pressure = itemview.findViewById(R.id.txt_pressure);
        //txt_geo_coord = itemview.findViewById(R.id.txt_geo_coord);
        weather_panel = itemview.findViewById(R.id.weather_panel);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.create();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Weather information");
        progressDialog.setMessage("Loading please wait");
        progressDialog.show();
        //txt_description = itemview.findViewById(R.id.txt_description);
        txt_temperature = itemview.findViewById(R.id.txt_temperature);
        //txt_date_time = itemview.findViewById(R.id.txt_date_time);
        txt_mintemp = itemview.findViewById(R.id.txt_mintemp);
        txt_maxtemp = itemview.findViewById(R.id.txt_maxtemp);
        txt_sealevel = itemview.findViewById(R.id.txt_sealevel);
        txt_discriptioncloud = itemview.findViewById(R.id.txt_discriptioncloud);
        getWeatherInformation();
        return itemview;
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void getWeatherInformation() {

        compositeDisposable.add(mService.getWeatherBylatlng(String.valueOf(Common.currant_location.getLatitude()),
                String.valueOf(Common.currant_location.getLongitude()),
                Common.API_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                                .append(".png").toString()).into(img_weather);
                        txt_city_name.setText(new StringBuilder(weatherResult.getName().toUpperCase()).append(",").append(weatherResult.getSys().getCountry().toUpperCase()));
                       // txt_description.setText(new StringBuilder("Weather in ").append(weatherResult.getName()).toString());
                        txt_temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
                       // txt_date_time.setText(Common.convertUnixToDate(weatherResult.getDt()));
                        txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hpa").toString());
                        txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" %").toString());
                        txt_sunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                        txt_sunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));
                        txt_mintemp.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp_min())).append("").toString());
                        txt_maxtemp.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp_max())).append("").toString());
                        txt_sealevel.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getSea_level())).toString());
                        txt_wind.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed())));
                        txt_discriptioncloud.setText(new StringBuilder(String.valueOf(weatherResult.getWeather().get(0).getDescription())).toString().toUpperCase());
                       // txt_geo_coord.setText(new StringBuilder(weatherResult.getCoord().toString()).toString());
                        //Display Panel
                        weather_panel.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        );
    }
}
