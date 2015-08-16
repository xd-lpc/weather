package com.xd.lpc_.weather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.xd.lpc_.weather.R;
import com.xd.lpc_.weather.db.CoolWeatherOpenHelper;
import com.xd.lpc_.weather.db.WeatherDB;
import com.xd.lpc_.weather.model.CityInfo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;


public class MainActivity extends Activity {

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTRY = 2;
    //当前选中级别
    private int currentLevel;

    //选中的目标
    private String choiceProvince;
    private String choiceCity;
    private String choiceCountry;


    //省，市县列表
    private LinkedHashSet<String> province;
    private LinkedHashSet<String> city;
    private LinkedHashSet<String> country;


    private Button btnData;
    private ImageView ivWeather;
    private TextView tvTitle;
    private ListView lvChoice;

    private CoolWeatherOpenHelper dbHelper;
    private WeatherDB weatherDB;

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        //将城市数据写入数据库
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = db.query("City_Info", null, null, null, null, null, null);
//        int num = cursor.getCount();
//        if(num==0) {

//        InputStream inputStream = getResources().openRawResource(R.raw.city);
//        InputStreamReader inputStreamReader = null;
//        try {
//
//            inputStreamReader = new InputStreamReader(inputStream, "gbk");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }


//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        StringBuffer sb = new StringBuffer("");
//        String line;
//
//            try {
//                while ((line = bufferedReader.readLine()) != null) {
//                    ContentValues values = new ContentValues();
//
//                    if (line.length() > 1) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(line);
//                            String id = jsonObject.getString("id");
//                            String country_name = jsonObject.getString("name");
//                            String city_name = jsonObject.getString("parent1");
//                            String province_name = jsonObject.getString("parent2");
//                            String nation_name = jsonObject.getString("parent3");
//                            String country_pinying = jsonObject.getString("en");
//                            //Log.e("aaaaa",id+"--"+country_name+"--"+city_name+"--"+province_name+"--"+nation_name);
//
//                            values.put("nation_name",nation_name);
//                            values.put("province_name",province_name);
//                            values.put("city_name", city_name);
//                            values.put("country_name",country_name);
//                            values.put("country_pingyin",country_pinying);
//                            values.put("city_id",id);
//                            db.insert("City_Info", null, values);
//                            values.clear();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        }
//end
    }

    public void init() {
        btnData = (Button) findViewById(R.id.btnData);
        ivWeather = (ImageView) findViewById(R.id.ivWeather);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        lvChoice = (ListView)findViewById(R.id.lvChoice);


        // = new CoolWeatherOpenHelper(this,"CityInfo.db",null,1);
        weatherDB = WeatherDB.getInstance(this);
        loadData();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        lvChoice.setAdapter(adapter);
        lvChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    choiceProvince = (String) province.toArray()[position];
                    queryCity();
                }else if(currentLevel==LEVEL_CITY){
                    choiceCity = (String)city.toArray()[position];
                    queryCountry();
                }

            }
        });

        queryProvince();



//        int num = weatherDB.db.query("City_Info", null, null, null, null, null, null).getCount();
//        //往数据库写入初始城市信息 写入时间太长，建议再开个线程
//        if (num == 0) {
//
//            InputStream inputStream = getResources().openRawResource(R.raw.city);
//            InputStreamReader inputStreamReader = null;
//            try {
//
//                inputStreamReader = new InputStreamReader(inputStream, "gbk");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String line;
//            try {
//                while ((line = bufferedReader.readLine()) != null) {
//
//                    if (line.length() > 1) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(line);
//                            CityInfo cityInfo = new CityInfo();
//
//                            cityInfo.setNationName(jsonObject.getString("parent3"));
//                            cityInfo.setProviceName(jsonObject.getString("parent2"));
//                            cityInfo.setCityName(jsonObject.getString("parent1"));
//                            cityInfo.setCountryName(jsonObject.getString("name"));
//                            cityInfo.setCountryPingyin(jsonObject.getString("en"));
//                            cityInfo.setCityId(jsonObject.getString("id"));
//                            weatherDB.saveCityInfo(cityInfo);
//                            String id = jsonObject.getString("id");
//                            String country_name = jsonObject.getString("name");
//                            String city_name = jsonObject.getString("parent1");
//                            String province_name = jsonObject.getString("parent2");
//                            String nation_name = jsonObject.getString("parent3");
//                            String country_pinying = jsonObject.getString("en");
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbHelper.getWritableDatabase();

                ivWeather.setImageResource(R.drawable.a13);

                int n = weatherDB.db.query("City_Info", null, null, null, null, null, null).getCount();
                Log.d("aaaaa", "----->" + n);

                LinkedHashSet<String> cityName = weatherDB.loadCountry("宜昌");

                Log.e("aaaaa", "----->" + cityName.size());
                Iterator iterator = cityName.iterator();
                while (iterator.hasNext()) {
                    Log.e("aaaaa", iterator.next().toString());
                }

            }
        });

    }

    public void loadData(){

        int num = weatherDB.db.query("City_Info", null, null, null, null, null, null).getCount();
        //往数据库写入初始城市信息 写入时间太长，建议再开个线程
        if (num == 0) {

            InputStream inputStream = getResources().openRawResource(R.raw.city);
            InputStreamReader inputStreamReader = null;
            try {

                inputStreamReader = new InputStreamReader(inputStream, "gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            try {

                while ((line = bufferedReader.readLine()) != null) {

                    if (line.length() > 1) {
                        try {
                            JSONObject jsonObject = new JSONObject(line);
                            CityInfo cityInfo = new CityInfo();

                            cityInfo.setNationName(jsonObject.getString("parent3"));
                            cityInfo.setProviceName(jsonObject.getString("parent2"));
                            cityInfo.setCityName(jsonObject.getString("parent1"));
                            cityInfo.setCountryName(jsonObject.getString("name"));
                            cityInfo.setCountryPingyin(jsonObject.getString("en"));
                            cityInfo.setCityId(jsonObject.getString("id"));
                            weatherDB.saveCityInfo(cityInfo);
//                            String id = jsonObject.getString("id");
//                            String country_name = jsonObject.getString("name");
//                            String city_name = jsonObject.getString("parent1");
//                            String province_name = jsonObject.getString("parent2");
//                            String nation_name = jsonObject.getString("parent3");
//                            String country_pinying = jsonObject.getString("en");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void queryProvince(){
        province = weatherDB.loadProvince();

        if(province.size()>0){
            dataList.clear();
            for(String p:province){
                dataList.add(p);
            }
            adapter.notifyDataSetChanged();
            lvChoice.setSelection(0);
            tvTitle.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        }
    }

    public void queryCity(){
        city = weatherDB.loadCity(choiceProvince);

        if(city.size()>0){
            dataList.clear();
            for(String p:city){
                dataList.add(p);
            }
            adapter.notifyDataSetChanged();
            lvChoice.setSelection(0);
            tvTitle.setText(choiceProvince);
            currentLevel = LEVEL_CITY;
        }
    }


    public void queryCountry(){
        country = weatherDB.loadCountry(choiceCity);

        if(country.size()>0){
            dataList.clear();
            for(String p:country){
                dataList.add(p);
            }
            adapter.notifyDataSetChanged();
            lvChoice.setSelection(0);
            tvTitle.setText(choiceCity);
            currentLevel = LEVEL_COUNTRY;
        }
    }

    @Override
    public void onBackPressed() {
        if(currentLevel == LEVEL_COUNTRY){
            queryCity();
        }else if(currentLevel == LEVEL_CITY){
            queryProvince();
        }else {
            finish();
        }
    }
}
