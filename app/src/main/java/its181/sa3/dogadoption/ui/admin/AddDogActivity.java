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

public class AddDogActivity extends AppCompatActivity {
    private EditText nameEditText, breedEditText, ageEditText, descriptionEditText, imageUrlEditText;
    private CheckBox adoptedCheckBox;
    private Button saveButton;
    private ProgressBar progressBar;
    private DogApiService dogApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        Toolbar toolbar = findViewById(R.id.addDogToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New Dog");

        nameEditText = findViewById(R.id.editDogName);
        breedEditText = findViewById(R.id.editDogBreed);
        ageEditText = findViewById(R.id.editDogAge);
        descriptionEditText = findViewById(R.id.editDogDescription);
        imageUrlEditText = findViewById(R.id.editDogImageUrl);
        adoptedCheckBox = findViewById(R.id.checkboxAdopted);
        saveButton = findViewById(R.id.buttonSaveDog);
        progressBar = findViewById(R.id.progressBar);

        RetrofitService retrofitService = new RetrofitService();
        dogApiService = retrofitService.getRetrofit().create(DogApiService.class);

        saveButton.setOnClickListener(v -> saveDog());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDog() {
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
            imageUrlEditText.setError("Image URL is required");
            return;
        }

        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "https://preview.redd.it/bwof59fjb2s91.jpg?width=906&format=pjpg&auto=webp&s=33281994eca39e7cc34d733c45a7ca9629207b99";
        }

        Dog newDog = new Dog(null, name, breed, age, description, isAdopted, imageUrl);

        progressBar.setVisibility(View.VISIBLE);
        saveButton.setEnabled(false);

        Call<Dog> call = dogApiService.addDog(newDog);
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(@NonNull Call<Dog> call, @NonNull Response<Dog> response) {
                progressBar.setVisibility(View.GONE);
                saveButton.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AddDogActivity.this, "Dog added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddDogActivity.this, "Failed to add dog", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Dog> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                saveButton.setEnabled(true);
                Toast.makeText(AddDogActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}