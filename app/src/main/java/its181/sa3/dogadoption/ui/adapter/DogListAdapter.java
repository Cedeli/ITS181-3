package its181.sa3.dogadoption.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.Dog;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.DogViewHolder> {

    private List<Dog> dogs;
    private OnAdoptClickListener listener;

    public interface OnAdoptClickListener {
        void onAdoptClick(int position);
    }

    public DogListAdapter(List<Dog> dogs, OnAdoptClickListener listener) {
        this.dogs = dogs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dog, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = dogs.get(position);
        holder.bind(dog, position);
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    class DogViewHolder extends RecyclerView.ViewHolder {
        ImageView dogImage;
        TextView dogName, dogBreed, dogAge, adoptedText;
        Button adoptButton;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            dogImage = itemView.findViewById(R.id.dogImage);
            dogName = itemView.findViewById(R.id.dogName);
            dogBreed = itemView.findViewById(R.id.dogBreed);
            dogAge = itemView.findViewById(R.id.dogAge);
            adoptedText = itemView.findViewById(R.id.adoptedText);
            adoptButton = itemView.findViewById(R.id.adoptButton);
        }

        void bind(Dog dog, int position) {
            dogName.setText(dog.getName());
            dogBreed.setText(dog.getBreed());
            dogAge.setText(dog.getAge());
            dogImage.setImageResource(dog.getImageResourceId());

            if (dog.getAdopted()) {
                adoptedText.setVisibility(View.VISIBLE);
                adoptButton.setVisibility(View.GONE);
            } else {
                adoptedText.setVisibility(View.GONE);
                adoptButton.setVisibility(View.VISIBLE);
            }

            adoptButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAdoptClick(position);
                }
            });
        }
    }

    public void updateDog(int position) {
        if (position >= 0 && position < dogs.size()) {
            Dog dog = dogs.get(position);
            dog.setAdopted(true);
            notifyItemChanged(position);
        }
    }
}