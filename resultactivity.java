package com.example.weihan.forecast;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ResultActivity extends AppCompatActivity {
    String icon;
    int temperature;
    int lowtemp;
    int hightemp;
    Double precipIntensity;
    int precipProbability;
    String windspeed;
    String dewpoint;
    String visibility = null;
    int humidity;
    Long sunrisetime;
    Long sunsettime;
    String jsondata;
    String intensity;
    String tempunit;
    String windunit;
    String dewunit;
    String visibunit;
    String currentsum;
    String temp;
    String imagehttp;
    String newTimeZoneID;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        Intent intent = getIntent();
        Bundle bun = intent.getExtras();
        final String street = bun.getString("street");
        final String city = bun.getString("city");
        final String state = bun.getString("state");
        final String degree = bun.getString("degree");
        jsondata = bun.getString("jsonurl");
        setContentView(R.layout.content_result);
        Button detailbutton = (Button) findViewById(R.id.moredetail);
        detailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("jsonurl", jsondata);
                b.putString("street", street);
                b.putString("city", city);
                b.putString("state", state);
                b.putString("degree", degree);
                Intent intentresult = new Intent(ResultActivity.this, DetailsActivity.class);
                intentresult.putExtras(b);
                startActivity(intentresult);
            }
        });
        TextView head = (TextView)findViewById(R.id.weathercondition);
        TextView tempra = (TextView) findViewById(R.id.temperature);
        TextView lowhightemp = (TextView) findViewById(R.id.lowhigh);
        TextView preci = (TextView) findViewById(R.id.intensity);
        TextView rain = (TextView) findViewById(R.id.rain);
        TextView wind = (TextView) findViewById(R.id.wind);
        TextView dew = (TextView) findViewById(R.id.dew);
        TextView humi = (TextView) findViewById(R.id.humi);
        TextView visi = (TextView) findViewById(R.id.visib);
        TextView sunrise = (TextView) findViewById(R.id.rise);
        TextView sunset = (TextView) findViewById(R.id.set);
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        TextView unithead = (TextView) findViewById(R.id.unit);
        String weathercond = null;

        try {
            JSONObject jobj = new JSONObject(jsondata);
            JSONObject current = jobj.getJSONObject("currently");
            icon = current.getString("icon");
            currentsum = current.getString("summary");
            temperature = current.getInt("temperature");
            JSONObject daily = jobj.getJSONObject("daily");
            JSONObject data0 = daily.getJSONArray("data").getJSONObject(0);
            lowtemp = data0.getInt("temperatureMin");
            hightemp = data0.getInt("temperatureMax");
            precipIntensity = current.getDouble("precipIntensity");
            precipProbability = (int)(current.getDouble("precipProbability") * 100);
            windspeed = current.getString("windSpeed");
            dewpoint = current.getString("dewPoint");
            humidity = (int)(current.getDouble("humidity"));
            visibility = current.getString("visibility");
            newTimeZoneID = jobj.getString("timezone");
            sunrisetime = data0.getLong("sunriseTime");
            sunsettime = data0.getLong("sunsetTime");
            weathercond = currentsum + " in " + city + ", " +  state;
            temp = String.valueOf(temperature);
            String lhtemp = "L:" + lowtemp + "бу"+" | " + "H:" + hightemp+"бу";
            if(precipIntensity>=0 && precipIntensity<0.002){
                intensity="None";
            }
            else if(precipIntensity>=0.002 && precipIntensity<0.017){
                intensity="Very Light";
            }
            else if(precipIntensity>=0.017 && precipIntensity<0.1){
                intensity="Light";
            }
            else if(precipIntensity>=0.1 && precipIntensity<0.4){
                intensity="Moderate";
            }
            else if(precipIntensity>=0.4){
                intensity="Heavy";
            }
            if (degree.equals("us")){
                tempunit = "F";
                windunit = "mph";
                dewunit = "F";
                visibunit = "mi";
            } else if (degree.equals("si")) {
                tempunit = "C";
                windunit = "m/s";
                dewunit = "C";
                visibunit = "km";
            }
            if (degree.equals("us")){
                unithead.setText("бу" + tempunit);
            } else if (degree.equals("si")) {
                unithead.setText("бу" + tempunit);
            }
            if(icon.equals("clear-day")){
                image.setImageResource(R.drawable.clear);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/clear.png";
            }
            else if(icon.equals("clear-night")){
                image.setImageResource(R.drawable.clear_night);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/clear_night.png";
            }
            else if(icon.equals("rain")){
                image.setImageResource(R.drawable.rain);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/rain.png";
            }
            else if (icon.equals("snow")){
                image.setImageResource(R.drawable.snow);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/snow.png";
            }
            else if(icon.equals("sleet")){
                image.setImageResource(R.drawable.sleet);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/sleet.png";
            }
            else if(icon.equals("wind")){
                image.setImageResource(R.drawable.wind);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/wind.png";
            }
            else if(icon.equals("fog")){
                image.setImageResource(R.drawable.fog);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/fog.png";
            }
            else if(icon.equals("cloudy")){
                image.setImageResource(R.drawable.cloudy);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/cloudy.png";
            }
            else if(icon.equals("partly-cloudy-day")){
                image.setImageResource(R.drawable.cloud_day);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_day.png";
            }
            else if(icon.equals("partly-cloudy-night")){
                image.setImageResource(R.drawable.cloud_night);
                imagehttp = "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_night.png";
            }
            Long date = sunrisetime * 1000;
            Long date1 = sunsettime * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat("KK:mm a");
            sdf.setTimeZone(TimeZone.getTimeZone(newTimeZoneID));
            String sunrisetime = sdf.format(date);
            String sunsettime = sdf.format(date1);
            head.setText(weathercond);
            tempra.setText(temp);
            lowhightemp.setText(lhtemp);
            preci.setText(intensity);
            rain.setText(precipProbability + " %");
            wind.setText( windspeed + " " + windunit);
            dew.setText(dewpoint + " бу" + dewunit);
            humi.setText(humidity + " %");
            visi.setText(visibility + " " + visibunit);
            sunrise.setText(sunrisetime);
            sunset.setText(sunsettime);
        } catch (JSONException e) {
            Log.i("xxxxxxxxxxx","xxxxxx");
            e.printStackTrace();
        }
        ImageView fbbutton = (ImageView) findViewById(R.id.fb);
        fbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current Weather in " + city + ", " + state)
                            .setContentDescription(currentsum + ", " + temp + "бу" + tempunit)
                            .setContentUrl(Uri.parse("http://forecast.io"))
                            .setImageUrl(Uri.parse(imagehttp))
                            .build();
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Toast toast = Toast.makeText(ResultActivity.this, "Facebook Post Successful", Toast.LENGTH_LONG);
                            toast.show();
                        }

                        @Override
                        public void onCancel() {
                            Toast toast = Toast.makeText(ResultActivity.this, "Post Cancelled", Toast.LENGTH_LONG);
                            toast.show();
                        }

                        @Override
                        public void onError(FacebookException e) {
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    });
                    shareDialog.show(linkContent);
                    }
                }

    });

}
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
