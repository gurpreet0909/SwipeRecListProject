package com.example.swipereclistproject;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gurpreetsingh on 2018-08-23.
 */

public class Item {

    String countryName;
    ArrayList<String> languagesSpoken;
    ArrayList<String> currenciesUsed;
    public Drawable drawable;
    String imageURL;

    public Item(){

    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL)  {

        this.imageURL = imageURL;
     //   new SendHttpRequestTask().execute(imageURL);
        }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<String> getLanguagesSpoken() {
        return languagesSpoken;
    }

    public void setLanguagesSpoken(ArrayList<String> languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    public ArrayList<String> getCurrenciesUsed() {
        return currenciesUsed;
    }

    public void setCurrenciesUsed(ArrayList<String> currenciesUsed) {
        this.currenciesUsed = currenciesUsed;
    }

    public Drawable getBitmap()  {
         return drawable;
    }

    public void setBitmap(Drawable drawable) {
        this.drawable = drawable;
    }


   /* private class SendHttpRequestTask extends AsyncTask<String, Void, Drawable> {
        @Override
        protected Drawable doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                SVG svg = SVGParser.getSVGFromInputStream(input);
                Drawable drawable = svg.createPictureDrawable();

            //  input.close();
                return drawable;
            }catch (Exception e){
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            setBitmap(result);
        }
    }
*/
}
