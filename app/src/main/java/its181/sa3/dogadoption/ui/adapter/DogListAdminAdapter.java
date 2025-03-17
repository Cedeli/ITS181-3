package its181.sa3.dogadoption.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.Dog;

public class DogListAdminAdapter extends RecyclerView.Adapter<DogListAdminAdapter.DogViewHolder> {
    private List<Dog> dogList;
    private OnDogActionListener listener;

    public interface OnDogActionListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public DogListAdminAdapter(List<Dog> dogList, OnDogActionListener listener) {
        this.dogList = dogList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dog_admin, parent, false);
        return new DogViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = dogList.get(position);
        holder.dogName.setText(dog.getName());
        holder.dogBreed.setText(dog.getBreed());
        holder.dogAge.setText(dog.getAge());

        if (dog.getAdopted()) {
            holder.adoptedText.setVisibility(View.VISIBLE);
        } else {
            holder.adoptedText.setVisibility(View.GONE);
        }

        Glide.with(holder.itemView.getContext())
                .load(dog.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_launcher_background)
                .into(holder.dogImage);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    static class DogViewHolder extends RecyclerView.ViewHolder {
        ImageView dogImage;
        TextView dogName, dogBreed, dogAge, adoptedText;
        Button editButton, deleteButton;

        public DogViewHolder(@NonNull View itemView, OnDogActionListener listener) {
            super(itemView);
            dogImage = itemView.findViewById(R.id.dogImage);
            dogName = itemView.findViewById(R.id.dogName);
            dogBreed = itemView.findViewById(R.id.dogBreed);
            dogAge = itemView.findViewById(R.id.dogAge);
            adoptedText = itemView.findViewById(R.id.adoptedText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(position);
                }
            });

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position);
                }
            });
        }
    }
}