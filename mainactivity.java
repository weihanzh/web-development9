package com.example.weihan.forecast;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    String editstreet;
    String editcity;
    String spinnerstate;
    String radiodegree;
    RadioGroup radioGroup;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText street1 = (EditText) findViewById(R.id.editText);
                editstreet = street1.getText().toString();
                EditText city1 = (EditText) findViewById(R.id.editText2);
                editcity = city1.getText().toString();
                spinnerstate = spinner.getSelectedItem().toString();
                radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                radiodegree = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                if (radiodegree.equals("Fahrenheit")) {
                    radiodegree = "us";
                } else if (radiodegree.equals("Celsius")) {
                    radiodegree = "si";
                }
                if (editstreet.trim().length() != 0 && editcity.trim().length() != 0 && !spinnerstate.equals("Select")) {
                    String weatherurl = "http://awsweihanzhang-env.elasticbeanstalk.com/?";
                    weatherurl = weatherurl + "street=" + editstreet + "&city=" + editcity + "&state=" + spinnerstate + "&degree=" + radiodegree;
                    weatherurl = weatherurl.replace(" ", "+");
                    new MyAsyncTask().execute(weatherurl);
                } else {
                    if (editstreet.trim().length() == 0){
                        TextView error1 = (TextView) findViewById(R.id.errormessage);
                        error1.setText("Please enter a Street Address");
                    } else if (editcity.trim().length() == 0){
                        TextView error2 = (TextView) findViewById(R.id.errormessage);
                        error2.setText("Please enter a City");
                    } else if(spinnerstate.equals("Select")){
                        TextView error3 = (TextView) findViewById(R.id.errormessage);
                        error3.setText("Please select a State");
                    }
                }
            }
        });
        adapter = ArrayAdapter.createFromResource(this, R.array.statelab, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        Button clearbut = (Button) findViewById(R.id.button2);
        clearbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cle = (TextView) findViewById(R.id.errormessage);
                cle.setText("");
                TextView clestreet = (TextView) findViewById(R.id.editText);
                clestreet.setText("");
                TextView clecity = (TextView) findViewById(R.id.editText2);
                clecity.setText("");
                radioGroup.check(R.id.radioButton);
                spinner.setSelection(0,true);
            }
        });
        ImageView forecastlink =(ImageView)findViewById(R.id.imageView);
        forecastlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent link = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forecast.io"));
                startActivity(link);
            }
        });
        Button aboutbutton = (Button) findViewById(R.id.button3);
        aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutinfor = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutinfor);
            }
        });
    }

    class MyAsyncTask extends AsyncTask <String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            String ss = null;
            String weather = (String) params[0];
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(weather);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bReader;
                bReader = new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuilder res = new StringBuilder();
                while ((line = bReader.readLine()) != null) {
                    res.append(line);
                }
                return (res.toString());



            } catch (MalformedURLException e) {

                e.printStackTrace();

            }catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }
@Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Bundle bun = new Bundle();
            bun.putString("jsonurl", result);
            bun.putString("street", editstreet);
            bun.putString("city", editcity);
            bun.putString("state", spinnerstate);
            bun.putString("degree", radiodegree);
            Intent intentressult = new Intent(MainActivity.this, ResultActivity.class);
            intentressult.putExtras(bun);
           startActivity(intentressult);

        }
    }
}
