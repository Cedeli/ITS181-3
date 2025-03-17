package its181.sa3.dogadoption.ui.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.Dog;
import its181.sa3.dogadoption.data.remote.DogApiService;
import its181.sa3.dogadoption.data.remote.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDogActivity extends AppCompatActivity {
    private EditText nameEditText, breedEditText, ageEditText, descriptionEditText, imageUrlEditText;
    private CheckBox adoptedCheckBox;
    private Button updateButton;
    private ProgressBar progressBar;
    private DogApiService dogApiService;
    private long dogId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dog);

        Toolbar toolbar = findViewById(R.id.updateDogToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Dog");

        nameEditText = findViewById(R.id.editDogName);
        breedEditText = findViewById(R.id.editDogBreed);
        ageEditText = findViewById(R.id.editDogAge);
        descriptionEditText = findViewById(R.id.editDogDescription);
        imageUrlEditText = findViewById(R.id.editDogImageUrl);
        adoptedCheckBox = findViewById(R.id.checkboxAdopted);
        updateButton = findViewById(R.id.buttonUpdateDog);
        progressBar = findViewById(R.id.progressBar);

        RetrofitService retrofitService = new RetrofitService();
        dogApiService = retrofitService.getRetrofit().create(DogApiService.class);

        if (getIntent().hasExtra("DOG_ID")) {
            dogId = getIntent().getLongExtra("DOG_ID", -1);

            if (dogId != -1) {
                nameEditText.setText(getIntent().getStringExtra("DOG_NAME"));
                breedEditText.setText(getIntent().getStringExtra("DOG_BREED"));
                ageEditText.setText(getIntent().getStringExtra("DOG_AGE"));
                descriptionEditText.setText(getIntent().getStringExtra("DOG_DESCRIPTION"));
                imageUrlEditText.setText(getIntent().getStringExtra("DOG_IMAGE_URL"));
                adoptedCheckBox.setChecked(getIntent().getBooleanExtra("DOG_ADOPTED", false));
            } else {
                Toast.makeText(this, "Invalid dog ID", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "No dog data provided", Toast.LENGTH_SHORT).show();
            finish();
        }

        updateButton.setOnClickListener(v -> updateDog());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDog() {
        String name = nameEditText.getText().toString().trim();
        String breed = breedEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String imageUrl = imageUrlEditText.getText().toString().trim();
        boolean isAdopted = adoptedCheckBox.isChecked();

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            return;
        }

        if (TextUtils.isEmpty(breed)) {
            breedEditText.setError("Breed is required");
            return;
        }

        if (TextUtils.isEmpty(age)) {
            ageEditText.setError("Age is required");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionEditText.setError("Description is required");
            return;
        }

        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "https://preview.redd.it/bwof59fjb2s91.jpg?width=906&format=pjpg&auto=webp&s=33281994eca39e7cc34d733c45a7ca9629207b99";
        }

        Dog updatedDog = new Dog(dogId, name, breed, age, description, isAdopted, imageUrl);

        progressBar.setVisibility(View.VISIBLE);
        updateButton.setEnabled(false);

        Call<Dog> call = dogApiService.updateDog(dogId, updatedDog);
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(@NonNull Call<Dog> call, @NonNull Response<Dog> response) {
                progressBar.setVisibility(View.GONE);
                updateButton.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(UpdateDogActivity.this, "Dog updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateDogActivity.this, "Failed to update dog", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Dog> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                updateButton.setEnabled(true);
                Toast.makeText(UpdateDogActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}