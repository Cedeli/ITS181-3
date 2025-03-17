package its181.sa3.dogadoption.ui.user;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.Dog;

public class DogDetailActivity extends AppCompatActivity {
    private TextView nameTextView, breedTextView, ageTextView, descriptionTextView, statusTextView;
    private ImageView dogImageView;
    private Button adoptButton;
    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_detail);

        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        nameTextView = findViewById(R.id.detailDogName);
        breedTextView = findViewById(R.id.detailDogBreed);
        ageTextView = findViewById(R.id.detailDogAge);
        descriptionTextView = findViewById(R.id.detailDogDescription);
        statusTextView = findViewById(R.id.statusText);
        dogImageView = findViewById(R.id.detailDogImage);
        adoptButton = findViewById(R.id.detailAdoptButton);

        int dogId = getIntent().getIntExtra("DOG_ID", -1);
        if (dogId != -1) {
            loadDogData(dogId);
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDogData(int dogId) {
        List<Dog> dogList = new ArrayList<>();
        String testImageUrl = "https://preview.redd.it/bwof59fjb2s91.jpg?width=906&format=pjpg&auto=webp&s=33281994eca39e7cc34d733c45a7ca9629207b99";
        dogList.add(new Dog(1L, "Max", "Golden Retriever", "3 years",
                "Friendly and energetic dog who loves to play fetch and socialize with other dogs.",
                false, testImageUrl));

        for (Dog d : dogList) {
            if (d.getId() == dogId) {
                dog = d;
                displayDogData();
                break;
            }
        }
    }

    private void displayDogData() {
        if (dog == null) return;

        Objects.requireNonNull(getSupportActionBar()).setTitle(dog.getName());

        nameTextView.setText(dog.getName());
        breedTextView.setText(dog.getBreed());
        ageTextView.setText(dog.getAge());
        descriptionTextView.setText(dog.getDescription());

        Glide.with(this)
                .load(dog.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_launcher_background)
                .into(dogImageView);

        updateAdoptionStatus();
    }

    private void updateAdoptionStatus() {
        if (dog.getAdopted()) {
            statusTextView.setText(R.string.adopted);
            statusTextView.setBackgroundResource(R.drawable.status_background_adopted);
            adoptButton.setVisibility(View.GONE);
        } else {
            statusTextView.setText(R.string.available);
            statusTextView.setBackgroundResource(R.drawable.status_background_available);
            adoptButton.setVisibility(View.VISIBLE);
            adoptButton.setOnClickListener(v -> adoptDog());
        }
    }

    private void adoptDog() {
        dog.setAdopted(true);
        updateAdoptionStatus();

        Toast.makeText(this,
                "You've requested to adopt " + dog.getName() + "! Our team will contact you soon.",
                Toast.LENGTH_LONG).show();
    }
}