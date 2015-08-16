package com.xd.lpc_.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by LPC- on 2015/8/8.
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper{

        public static final String CREATE_CITY_INFO = "create table City_Info (" +
            "id integer primary key autoincrement," +
            "nation_name text," +
            "province_name text," +
            "city_name text," +
            "country_name text," +
            "country_pingyin text," +
            "city_id text)";

    private Context mContext;
    public CoolWeatherOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){

        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITY_INFO);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
