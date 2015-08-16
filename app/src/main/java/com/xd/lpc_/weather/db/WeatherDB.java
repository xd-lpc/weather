package com.xd.lpc_.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xd.lpc_.weather.model.CityInfo;

import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * Created by LPC- on 2015/8/12.
 */
public class WeatherDB {

    //数据库名称
    public static final String DB_NAME = "CityInfo.db";

    //数据库版本
    public final int VERSION = 1;

    public SQLiteDatabase db;

    private static WeatherDB weatherDB;

    private WeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    //获取weatherDB的实例?
    public  synchronized static WeatherDB getInstance(Context context){
        if(weatherDB ==null){
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }


    //数据保存到数据库
    public void saveCityInfo(CityInfo cityInfo){
        if(cityInfo!=null)
        {
            ContentValues values = new ContentValues();
            values.put("nation_name",cityInfo.getNationName());
            values.put("province_name",cityInfo.getProviceName());
            values.put("city_name", cityInfo.getCityName());
            values.put("country_name",cityInfo.getCountryName());
            values.put("country_pingyin",cityInfo.getCountryPingyin());
            values.put("city_id",cityInfo.getCityId());
            db.insert("City_Info",null,values);
        }
    }


    //返回省列表
    public LinkedHashSet<String> loadProvince(){
        LinkedHashSet<String> setProvince = new LinkedHashSet<String>();
        String[] col = {"province_name"};
        Cursor cursor = db.query("City_Info",col,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                String p = cursor.getString(0);
                setProvince.add(p);
            }while(cursor.moveToNext());

        }

        if(cursor!=null){
            cursor.close();
        }

        return setProvince;
    }



    //返回某省下城市列表
    public LinkedHashSet<String> loadCity(String provinceName){
        LinkedHashSet<String> citySet = new LinkedHashSet<String>();
        String[] col = {"city_name"};
        Cursor cursor = db.query("City_Info",col,"province_name=?",new String[]{provinceName},null,null,null);

        if(cursor.moveToFirst()){
            do{
                String p = cursor.getString(0);
                citySet.add(p);
            }while(cursor.moveToNext());

        }
        if(cursor!=null){
            cursor.close();
        }
        return citySet;
    }

    //返回某市下县列表
    public LinkedHashSet<String> loadCountry(String cityName){
        LinkedHashSet<String> countrySet = new LinkedHashSet<String>();
        String[] col = {"country_name"};
        Cursor cursor = db.query("City_Info",col,"city_name=?",new String[]{cityName},null,null,null);

        if(cursor.moveToFirst()){
            do{
                String p = cursor.getString(0);
                countrySet.add(p);
            }while(cursor.moveToNext());

        }
        if(cursor!=null){
            cursor.close();
        }
        return countrySet;
    }




}
