package com.example.vijaysingh.quotefuel;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Main extends ActionBarActivity {
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //font path
        String fontpath="Oswald-Light_0.ttf";

        //Text View label
        TextView ourText=(TextView) findViewById(R.id.textView);

        //Loading Font Face
        Typeface tf=Typeface.createFromAsset(getAssets(),fontpath);

        //Applying font
        ourText.setTypeface(tf);

        StrictMode.enableDefaults();
        resultView=(TextView) findViewById(R.id.textView);

        Button insert=(Button) findViewById(R.id.button);

        insert.setOnClickListener(new View.OnClickListener(){
                                      @Override
                                  public void onClick(View view){

                                          getData();
                                      }
                                  }


        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData() {

        String result = "";
        InputStream isr = null;

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://transcendyourlife.tk");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }
        catch(Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
            resultView.setText("Couldn't connect to database");
        }

        //convert response to string

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();

            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }

        //parse json data
        try {
            resultView.setText(result);
        }
        catch(Exception e) {
            Log.e("log_tag", "Couldn't set text, damnit.");
        }

    }

}
