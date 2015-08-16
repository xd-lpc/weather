package com.xd.lpc_.weather.Test;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xd.lpc_.weather.R;
import com.xd.lpc_.weather.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class TestVolley extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_volley);

        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.thinkpage.cn/v2/weather/all.json?city=CHSN000000&language=zh-chs&unit=c&aqi=city&key=1FOC5NEVY0";
        //String url = "http://www.baidu.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("aaaaa", s);
                Toast.makeText(TestVolley.this,s, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("aaaaa",volleyError.toString());

                Toast.makeText(TestVolley.this, volleyError.toString(), Toast.LENGTH_LONG).show();
        }
        });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Toast.makeText(TestVolley.this,jsonObject.toString(), Toast.LENGTH_LONG).show();

                //Log.e("aaaaa", jsonObject.toString());

                try {
                    //String status = jsonObject.getString("weather");
                    //JSONObject w = jsonObject.getJSONObject("weather");
                    JSONObject w = jsonObject.getJSONArray("weather").getJSONObject(0);
                    //int l = w.length();

                    JSONObject now = w.getJSONObject("today");

                    Log.e("aaaaa", now.toString()+"----");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });


        mQueue.add(jsonObjectRequest);


        Intent intent = new Intent(TestVolley.this, MainActivity.class);
        startActivity(intent);

        }




        }
