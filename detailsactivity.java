package com.example.weihan.forecast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import javax.security.auth.login.LoginException;

public class DetailsActivity extends AppCompatActivity{
    String jsondata;
    ToggleButton next24;
    ToggleButton next7;
    TableLayout table24;
    TableLayout table7;
    Long hourtime;
    Long hourtime48;
    String summaryhour;
    String summaryhour48;
    int temphour;
    int temphour48;
    TextView theader;
    String dailysummary;
    int mintemp;
    int maxtemp;
    TableRow table24row;
    TableLayout pluslayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bun = intent.getExtras();
        final String street = bun.getString("street");
        final String city = bun.getString("city");
        final String state = bun.getString("state");
        final String degree = bun.getString("degree");
        jsondata = bun.getString("jsonurl");
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        next24 = (ToggleButton) findViewById(R.id.toggleButton);
        next7 = (ToggleButton) findViewById(R.id.toggleButton2);
        table24 = (TableLayout) findViewById(R.id.next24layout);
        table7 = (TableLayout) findViewById(R.id.next7layout);
        table24row = (TableRow) findViewById(R.id.tableRow24);
        pluslayout = (TableLayout) findViewById(R.id.next24pluslayout);
        next24.setBackgroundColor(Color.BLUE);
        next7.setBackgroundColor(Color.rgb(218,218,218));
        TextView head = (TextView) findViewById(R.id.header);
        head.setText("More Details for " + city + ", " + state);
        SimpleDateFormat sdf = new SimpleDateFormat("KK:mm a");
        if (degree.equals("us")){
            theader = (TextView) findViewById(R.id.temp);
            theader.setText("Temp(°„F)");
        } else if (degree.equals("si")) {
            Log.i("cdcedfc","cdscdf");
            /*theader.setText("Temp(°„C)");*/
        }
        try {
            JSONObject jobj = new JSONObject(jsondata);
            JSONObject hourly = jobj.getJSONObject("hourly");
            for (int i = 0; i < 24; i++){
                hourtime = hourly.getJSONArray("data").getJSONObject(i).getLong("time")*1000;
                String hourID = "time"+ (i + 1);
                int resID = getResources().getIdentifier(hourID, "id", "com.example.weihan.forecast");
                TextView hours = (TextView) findViewById(resID);
                summaryhour = hourly.getJSONArray("data").getJSONObject(i).getString("icon");
                temphour = hourly.getJSONArray("data").getJSONObject(i).getInt("temperature");
                String t1 = String.valueOf(temphour);
                String tempID = "temp" + (i + 1);
                int tempratureID = getResources().getIdentifier(tempID, "id", "com.example.weihan.forecast");
                TextView temphourtext = (TextView) findViewById(tempratureID);
                temphourtext.setText(t1);
                String sumID = "summary" + (i + 1);
                int summaryID = getResources().getIdentifier(sumID, "id", "com.example.weihan.forecast");
                ImageView summarys = (ImageView) findViewById(summaryID);
                hours.setText(sdf.format(hourtime));
                if(summaryhour.equals("clear-day")){
                    summarys.setImageResource(R.drawable.clear);
                }
                else if(summaryhour.equals("clear-night")){
                    summarys.setImageResource(R.drawable.clear_night);
                }
                else if(summaryhour.equals("rain")){
                    summarys.setImageResource(R.drawable.rain);
                }
                else if (summaryhour.equals("snow")){
                    summarys.setImageResource(R.drawable.snow);
                }
                else if(summaryhour.equals("sleet")){
                    summarys.setImageResource(R.drawable.sleet);
                }
                else if(summaryhour.equals("wind")){
                    summarys.setImageResource(R.drawable.wind);
                }
                else if(summaryhour.equals("fog")){
                    summarys.setImageResource(R.drawable.fog);
                }
                else if(summaryhour.equals("cloudy")){
                    summarys.setImageResource(R.drawable.cloudy);
                }
                else if(summaryhour.equals("partly-cloudy-day")){
                    summarys.setImageResource(R.drawable.cloud_day);
                }
                else if(summaryhour.equals("partly-cloudy-night")){
                    summarys.setImageResource(R.drawable.cloud_night);
                }
            }
            for (int i = 24; i < 48; i++){
                hourtime48 = hourly.getJSONArray("data").getJSONObject(i).getLong("time")*1000;
                String hourID48 = "time"+ (i + 1);
                int resID48 = getResources().getIdentifier(hourID48, "id", "com.example.weihan.forecast");
                TextView hours48 = (TextView) findViewById(resID48);
                summaryhour48 = hourly.getJSONArray("data").getJSONObject(i).getString("icon");
                temphour48 = hourly.getJSONArray("data").getJSONObject(i).getInt("temperature");
                String t2 = String.valueOf(temphour48);
                String tempID48 = "temp" + (i + 1);
                int tempratureID48 = getResources().getIdentifier(tempID48, "id", "com.example.weihan.forecast");
                TextView temphourtext48 = (TextView) findViewById(tempratureID48);
                temphourtext48.setText(t2);
                String sumID48 = "summary" + (i + 1);
                int summaryID48 = getResources().getIdentifier(sumID48, "id", "com.example.weihan.forecast");
                ImageView summarys48 = (ImageView) findViewById(summaryID48);
                hours48.setText(sdf.format(hourtime48));
                if(summaryhour48.equals("clear-day")){
                    summarys48.setImageResource(R.drawable.clear);
                }
                else if(summaryhour48.equals("clear-night")){
                    summarys48.setImageResource(R.drawable.clear_night);
                }
                else if(summaryhour48.equals("rain")){
                    summarys48.setImageResource(R.drawable.rain);
                }
                else if (summaryhour48.equals("snow")){
                    summarys48.setImageResource(R.drawable.snow);
                }
                else if(summaryhour48.equals("sleet")){
                    summarys48.setImageResource(R.drawable.sleet);
                }
                else if(summaryhour48.equals("wind")){
                    summarys48.setImageResource(R.drawable.wind);
                }
                else if(summaryhour48.equals("fog")){
                    summarys48.setImageResource(R.drawable.fog);
                }
                else if(summaryhour48.equals("cloudy")){
                    summarys48.setImageResource(R.drawable.cloudy);
                }
                else if(summaryhour48.equals("partly-cloudy-day")){
                    summarys48.setImageResource(R.drawable.cloud_day);
                }
                else if(summaryhour48.equals("partly-cloudy-night")){
                    summarys48.setImageResource(R.drawable.cloud_night);
                }
            }
            JSONObject daily = jobj.getJSONObject("daily");
            for (int i = 1; i <= 7; i++){
                dailysummary = daily.getJSONArray("data").getJSONObject(i).getString("icon");
                String imID = "im" + i;
                int imageID = getResources().getIdentifier(imID, "id", "com.example.weihan.forecast");
                ImageView imagesum = (ImageView) findViewById(imageID);
                if(dailysummary.equals("clear-day")){
                    imagesum.setImageResource(R.drawable.clear);
                }
                else if(dailysummary.equals("clear-night")){
                    imagesum.setImageResource(R.drawable.clear_night);
                }
                else if(summaryhour.equals("rain")){
                    imagesum.setImageResource(R.drawable.rain);
                }
                else if (dailysummary.equals("snow")){
                    imagesum.setImageResource(R.drawable.snow);
                }
                else if(dailysummary.equals("sleet")){
                    imagesum.setImageResource(R.drawable.sleet);
                }
                else if(dailysummary.equals("wind")){
                    imagesum.setImageResource(R.drawable.wind);
                }
                else if(dailysummary.equals("fog")){
                    imagesum.setImageResource(R.drawable.fog);
                }
                else if(dailysummary.equals("cloudy")){
                    imagesum.setImageResource(R.drawable.cloudy);
                }
                else if(dailysummary.equals("partly-cloudy-day")){
                    imagesum.setImageResource(R.drawable.cloud_day);
                }
                else if(dailysummary.equals("partly-cloudy-night")){
                    imagesum.setImageResource(R.drawable.cloud_night);
                }
                if(degree.equals("us")){
                    mintemp = daily.getJSONArray("data").getJSONObject(i).getInt("temperatureMin");
                    String minusID = "min"+ i;
                    int mintempusID = getResources().getIdentifier(minusID, "id", "com.example.weihan.forecast");
                    TextView mintempustext = (TextView) findViewById(mintempusID);
                    mintempustext.setText("Min: " + mintemp + "°„F |");
                    maxtemp = daily.getJSONArray("data").getJSONObject(i).getInt("temperatureMax");
                    String maxusID = "max"+ i;
                    int maxtempusID = getResources().getIdentifier(maxusID, "id", "com.example.weihan.forecast");
                    TextView maxtempustext = (TextView) findViewById(maxtempusID);
                    maxtempustext.setText(" Max: " + maxtemp +"°„F");
                } else if(degree.equals("si")){
                    mintemp = daily.getJSONArray("data").getJSONObject(i).getInt("temperatureMin");
                    String minsiID = "min"+ i;
                    int mintempsiID = getResources().getIdentifier(minsiID, "id", "com.example.weihan.forecast");
                    TextView mintempsitext = (TextView) findViewById(mintempsiID);
                    mintempsitext.setText("Min: " + mintemp + "°„C |");
                    maxtemp = daily.getJSONArray("data").getJSONObject(i).getInt("temperatureMax");
                    String maxsiID = "max"+ i;
                    int maxtempsiID = getResources().getIdentifier(maxsiID, "id", "com.example.weihan.forecast");
                    TextView maxtempsitext = (TextView) findViewById(maxtempsiID);
                    maxtempsitext.setText(" Max: " + maxtemp +"°„C");
                }
            }

        } catch (JSONException e) {
            Log.i("11","22");
            e.printStackTrace();
        }

}
    public void btnClicked(View v){
                table24.setVisibility(View.VISIBLE);
                table7.setVisibility(View.GONE);
                table24row.setVisibility(View.VISIBLE);
                next24.setBackgroundColor(Color.BLUE);
                next7.setBackgroundColor(Color.rgb(218,218,218));
    }
    public void btn7Clicked(View v){
                table7.setVisibility(View.VISIBLE);
                table24.setVisibility(View.GONE);
                pluslayout.setVisibility(View.GONE);
                table24row.setVisibility(View.INVISIBLE);
                next7.setBackgroundColor(Color.BLUE);
                next24.setBackgroundColor(Color.rgb(218, 218, 218));
    }
    public void plusClicked(View v){
                table24row.setVisibility(View.GONE);
                pluslayout.setVisibility(View.VISIBLE);
    }

}
