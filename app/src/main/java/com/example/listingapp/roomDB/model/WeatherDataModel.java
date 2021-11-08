package com.example.listingapp.roomDB.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
        import org.json.JSONObject;

        @Entity(tableName = "weatherdetails")
public class WeatherDataModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "temperature")
    private String Temperature;
    @ColumnInfo(name = "icon")
    private String icon;
    @ColumnInfo(name = "city")
    private String city ;
    @ColumnInfo(name = "weathertype")
    private String WeatherType;
    @ColumnInfo(name = "condition")
    private int Condition;
    @ColumnInfo(name = "humidity")
    private String humidity;
    @ColumnInfo(name = "speed")
    private String speed;
    @ColumnInfo(name = "description")
    private String description;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setTemperature(String temperature) {
                Temperature = temperature;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setWeatherType(String weatherType) {
                WeatherType = weatherType;
            }

            public int getCondition() {
                return Condition;
            }

            public void setCondition(int condition) {
                Condition = condition;
            }



    public String getTemperature() {
//        return Temperature+"°C";
        return Temperature;
    }

    public String geticon() {
        return icon;
    }

    public String getcity() {
        return city;
    }

            public String getSpeed() {
                return speed;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setSpeed(String speed) {
                this.speed = speed;
            }

            public String getWeatherType() {
        return WeatherType;
    }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }
        }