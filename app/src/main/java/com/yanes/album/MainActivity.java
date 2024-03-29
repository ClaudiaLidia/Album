package com.yanes.album;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_CODE_ADD = 100;
    public static String c = "";
    public static String Activity_KEY = "activity";
    public static ArrayList<String> check = new ArrayList<>();
    public static ArrayList<View> position = new ArrayList<>();
    public static ArrayList<Integer> po = new ArrayList<>();
    android.support.v7.widget.Toolbar toolbar;
    public static int total = 0;
    String result;
    InputStream isr;
    public static String numbercoins;
    public static String username;
    TextView textView1;
    TextView textView;
    NavigationView n;

    @Override
    protected void onResume() {
        getData updateTask = new getData();
        updateTask.execute();
        textView =(TextView) findViewById(R.id.coin);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        n = findViewById(R.id.nav_view);
        n.setItemIconTintList(null);
        n.setNavigationItemSelectedListener(this);



        Log.i("este1","111");


        toolbar = findViewById(R.id.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Button album = findViewById(R.id.album);
        album.setOnClickListener(this);
        Button about_us = findViewById(R.id.about_us);
        about_us.setOnClickListener(this);
        Button resource = findViewById(R.id.resources);
        resource.setOnClickListener(this);
        Button game = findViewById(R.id.game);
        game.setOnClickListener(this);


        Log.i("este2","111");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.HomeItem) {
                    c = "MainActivity";
                } else if (item.getItemId() == R.id.GameItem) {
                    c = "Game";
                } else if (item.getItemId() == R.id.AlbumItem) {
                    c = "States";
                }

                start_activity_menu();

                return true;
            }

        });

    }



    public void start_activity_menu() {
        Intent intent = null;
        if (c.equals("MainActivity")) {

        } else if (c.equals("Game")) {
            intent = new Intent(this, Game.class);
            startActivity(intent);
        } else if (c.equals("States")) {
            intent = new Intent(this, States.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.album) {
            Intent intent = new Intent(this, States.class);
            startActivity(intent);

        } else if (view.getId() == R.id.about_us) {
            Intent intent = new Intent(this, Aboutus.class);
            startActivity(intent);


        } else if (view.getId() == R.id.resources) {
            Intent intent = new Intent(this, Resources.class);
            startActivity(intent);


        } else if (view.getId() == R.id.game) {
            Intent intent = new Intent(this, Game.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);


        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Log.i("este3","111");
        int id = item.getItemId();


        if (id == R.id.nav_album) {
            Intent intent = new Intent(this, States.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_classifications) {

        } else if (id == R.id.nav_resources) {
            Intent intent = new Intent(this, Resources.class);
            startActivity(intent);

        } else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(this, Aboutus.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_ADD) {

            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled ", Toast.LENGTH_SHORT).show();
                return;
            } else if (resultCode == RESULT_OK) {

                int h = 0;

                String high_score = data.getStringExtra(Activity_KEY);
                Log.i("high score", "high score" + high_score);
                h = Integer.parseInt(high_score);
                total += h;


            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    public class getData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {


                String state_url = "https://lidiayanesgarcia.000webhostapp.com/php4.php";
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(state_url); //YOUR PHP SCRIPT ADDRESS
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    isr = entity.getContent();



                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection " + e.toString());

                }




                //convert response to string
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    isr.close();

                    result = sb.toString();
                } catch (Exception e) {
                    Log.e("log_tag", "Error  converting result " + e.toString());
                }


                try {


                    JSONArray jArray = new JSONArray(result);


                    for (int i = 0; i < jArray.length(); i++) {
                        final JSONObject json = jArray.getJSONObject(i);


                        if(json.getString("username").equals(login_page.username)) {


                            runOnUiThread(new Runnable()
                            {

                                public void run()
                                {

                                    try {
                                        Log.i("nombre", " i"+ json.getString("name"));
                                        textView1=(TextView) n.findViewById(R.id.name_header);
                                        textView1.setText(json.getString("name"));
                                        username=json.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }




                                }
                            });
                           textView.setText(json.getString("coins"));
                            numbercoins= json.getString("coins");
Log.i("hhhhhh","jjj");

                        }



                    }

                } catch (Exception e) {
                    // TODO: handle exception

                    Log.e("log_tag", "Error Parsing Data " + e.toString());
                }
                return "Executed";
            }

        }


}
