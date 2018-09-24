package com.example.swipereclistproject;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ahmadrosid.svgloader.SvgLoader;

import java.util.ArrayList;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Item> itemList;

    public  static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView country_names;
        TextView language_spoken;
        TextView currencies_used;
        RelativeLayout viewForeground,viewBackgraound;
        ImageView countryimageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            country_names = itemView.findViewById(R.id.country_name);
            language_spoken = itemView.findViewById(R.id.country_language);
            currencies_used = itemView.findViewById(R.id.country_currency);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewBackgraound= itemView.findViewById(R.id.view_background);
            countryimageView = itemView.findViewById(R.id.imageView);
        }
    }

    public CountryListAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;

    }

    @Override
    public CountryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(parent.getContext())
                                       .inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Item item = itemList.get(position);
        Log.d("itemlist", item.toString());
        holder.country_names.setText(item.getCountryName());
        holder.currencies_used.setText(item.getCurrenciesUsed().get(0));
        holder.language_spoken.setText(item.getLanguagesSpoken().get(0));
        Log.d("Image URL ",item.getImageURL());

       //  holder.countryimageView.setImageDrawable(item.getBitmap());
        SvgLoader.pluck()
                .with((Activity) context)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(item.getImageURL(), holder.countryimageView);


    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void removeItem(int position) {
        itemList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Item item, int position) {
        itemList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    }
