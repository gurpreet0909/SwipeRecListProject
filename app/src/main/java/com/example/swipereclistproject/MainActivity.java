package com.example.swipereclistproject;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecItemTouchHelper.RecyclerItemTouchHelperListener {

    public static int progress_bar_type;
    private ArrayList<Item> arrayList;
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private CountryListAdapter countryListAdapter;
    private static final String url = "https://restcountries.eu/rest/v2/all";
    Bitmap mIcon11 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.my_item));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        new BackgroundLoader().execute(url);




        ItemTouchHelper.SimpleCallback simpleCallback = new RecItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);


    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

         if(viewHolder instanceof CountryListAdapter.MyViewHolder){

             String name = arrayList.get(viewHolder.getAdapterPosition()).getCountryName();
             final Item itemDeleted = arrayList.get(viewHolder.getAdapterPosition());
             final int indexDeleted = viewHolder.getAdapterPosition();

             countryListAdapter.removeItem(viewHolder.getAdapterPosition());

             Snackbar snackbar = Snackbar.make(coordinatorLayout,name+" removed from list ",Snackbar.LENGTH_LONG);
             snackbar.setAction("UNDO", new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     countryListAdapter.restoreItem(itemDeleted,indexDeleted);
                 }
             });
             snackbar.setActionTextColor(Color.YELLOW);
           snackbar.show();

         }


    }

    private class BackgroundLoader extends AsyncTask<String, Integer, ArrayList<Item>> {

        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method

            progress_bar_type = 0;
            progressDialog.setMessage("Downloading Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected ArrayList<Item> doInBackground(String... url) {

            try {

                URL connection = new URL(url[0]);
                URLConnection tc = connection.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        tc.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    JSONArray ja = new JSONArray(line);

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject obj = (JSONObject) ja.get(i);


                         Item item = new Item();
                         item.setCountryName(obj.getString("name"));
                         item.setImageURL(obj.getString("flag"));

                      //   Log.d("Image URL ",imageUrl);




                        // Currencies is json array
                        JSONArray cArry = obj.getJSONArray("currencies");
                        ArrayList<String> curr = new ArrayList<String>();
                        for (int j = 0; j < cArry.length(); j++) {

                            JSONObject jobject = cArry.getJSONObject(j);
                            curr.add(jobject.getString("name"));
                        }
                        item.setCurrenciesUsed(curr);

                        // Languages is an array
                        JSONArray lArry = obj.getJSONArray("languages");
                        ArrayList<String> lang = new ArrayList<>();
                        for (int l = 0; l < lArry.length(); l++) {

                            JSONObject jsonObject = lArry.getJSONObject(l);
                            lang.add(jsonObject.getString("name"));
                        }
                        item.setLanguagesSpoken(lang);


                        // adding movie to movies array
                        arrayList.add(item);

                    }


                    while (progress_bar_type < 100) {
                        try {

                            progress_bar_type++;

                            /** Invokes the callback method onProgressUpdate */
                            publishProgress(progress_bar_type);

                            /** Sleeps this thread for 100ms */
                            Thread.sleep(10);

                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }
                    }
                }
                in.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arrayList;

        }


        @Override
        protected void onPostExecute(ArrayList<Item> model) {
            super.onPostExecute(model);
            Log.d("countrymodals", String.valueOf(model.size()));

            countryListAdapter = new CountryListAdapter(MainActivity.this, model);
           // countryListAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(countryListAdapter);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Download complete", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(progress_bar_type);
        }

    }


}
