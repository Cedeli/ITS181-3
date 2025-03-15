package com.example.sa3_dogadoption.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sa3_dogadoption.R;
import com.example.sa3_dogadoption.model.Dog;

import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {

    private Context context;
    private List<Dog> dogList;

    public DogAdapter(Context context, List<Dog> dogList) {
        this.context = context;
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Card Population
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        // Fetch dog
        Dog dog = dogList.get(position);

        holder.dogName.setText(dog.getName());
        holder.dogDescription.setText(dog.getDescription());

        // Set image
        Resources res = context.getResources();
        int resourceID = res.getIdentifier(dog.getImagesrc(), "drawable", context.getPackageName());
        holder.dogImageView.setImageResource(resourceID);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }



    // View Holder for Dogs
    public class DogViewHolder extends RecyclerView.ViewHolder {

        ImageView dogImageView;
        TextView dogName;
        TextView dogDescription;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            dogImageView = itemView.findViewById(R.id.imageView);
            dogName = itemView.findViewById(R.id.dogName);
            dogDescription = itemView.findViewById(R.id.dogDescription);
        }
    }
}
