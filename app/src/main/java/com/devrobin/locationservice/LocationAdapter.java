package com.devrobin.locationservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devrobin.locationservice.MVVM.LocationData;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private Context context;
    private List<LocationData> locationList = new ArrayList<>();

    public void setLocations(List<LocationData> locationList){
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_items, parent,false);

        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {

        LocationData locationData = locationList.get(0);


        holder.placeName.setText(locationData.getPlaceName());

        //Create String for Lat Long

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


    public class LocationViewHolder extends RecyclerView.ViewHolder{

        TextView placeName, latLong, time;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            placeName = itemView.findViewById(R.id.tvPlaceName);
            time = itemView.findViewById(R.id.tvTime);

        }
    }

}
