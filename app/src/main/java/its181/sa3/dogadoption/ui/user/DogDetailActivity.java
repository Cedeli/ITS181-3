package its181.sa3.dogadoption.ui.user;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.Objects;
import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.Dog;
import its181.sa3.dogadoption.data.remote.DogApiService;
import its181.sa3.dogadoption.data.remote.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DogDetailActivity extends AppCompatActivity {
    private TextView nameTextView, breedTextView, ageTextView, descriptionTextView, statusTextView;
    private ImageView dogImageView;
    private Button adoptButton;
    private DogApiService dogApiService;
    private ProgressBar progressBar;
    private long dogId;
    private Dog dog;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
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
        progressBar = findViewById(R.id.progressBar);
        dogId = getIntent().getLongExtra("DOG_ID", -1L);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        RetrofitService retrofitService = new RetrofitService();
        dogApiService = retrofitService.getRetrofit().create(DogApiService.class);
        if (dogId != -1L) {
            loadDogData(dogId);
        } else {
            Toast.makeText(this, "Invalid Dog ID", Toast.LENGTH_SHORT).show();
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
    private void loadDogData(long dogId) {
        progressBar.setVisibility(View.VISIBLE);
        Call<Dog> call = dogApiService.getDogById(dogId);
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(@NonNull Call<Dog> call, @NonNull Response<Dog> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    dog = response.body();
                    if (dog != null) {
                        displayDogData();
                    } else {
                        Toast.makeText(DogDetailActivity.this, "Dog not found", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(DogDetailActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Dog> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DogDetailActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
        progressBar.setVisibility(View.VISIBLE);
        adoptButton.setEnabled(false);
        long userId = sharedPreferences.getLong(KEY_USER_ID, -1);
        if (userId == -1) {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            adoptButton.setEnabled(true);
            return;
        }
        Call<Dog> call = dogApiService.adoptDog(dogId, userId);
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(@NonNull Call<Dog> call, @NonNull Response<Dog> response) {
                progressBar.setVisibility(View.GONE);
                adoptButton.setEnabled(true);
                if (response.isSuccessful()) {
                    dog = response.body();
                    if (dog != null) {
                        updateAdoptionStatus();
                        Toast.makeText(DogDetailActivity.this,
                                "You've requested to adopt " + dog.getName() +
                                        "! The veterinary office will contact you soon.",
                                Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(DogDetailActivity.this, "Adoption request successful, " +
                                "but could not retrieve updated dog.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DogDetailActivity.this, "Adoption request failed: " +
                            response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Dog> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                adoptButton.setEnabled(true);
                Toast.makeText(DogDetailActivity.this, "Network Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}