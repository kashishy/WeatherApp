package com.example.weatherapp.Model;

import java.util.List;

public class WeatherResult {

    private Coord coord ;
    private List<Weather> weather ;
    private String base ;
    private Main main ;
    private Wind wind ;
    private Clouds clouds ;
    private int dt ;
    private Sys sys ;
    private int id ;
    private String name ;
    private int cod ;

    public WeatherResult() {
    }

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public int getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
}
