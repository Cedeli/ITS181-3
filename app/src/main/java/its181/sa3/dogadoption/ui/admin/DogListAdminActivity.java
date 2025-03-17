package its181.sa3.dogadoption.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.Dog;
import its181.sa3.dogadoption.data.remote.DogApiService;
import its181.sa3.dogadoption.data.remote.RetrofitService;
import its181.sa3.dogadoption.ui.LoginActivity;
import its181.sa3.dogadoption.ui.adapter.DogListAdminAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogListAdminActivity extends AppCompatActivity implements DogListAdminAdapter.OnDogActionListener {
    private RecyclerView recyclerView;
    private DogListAdminAdapter adapter;
    private List<Dog> dogList;
    private FloatingActionButton addDogFab;
    private DogApiService dogApiService;
    private TextView noDogsTextViewAdmin;
    private Toolbar adminToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list_admin);

        adminToolbar = findViewById(R.id.adminToolbar);
        setSupportActionBar(adminToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Manage Dogs");

        recyclerView = findViewById(R.id.adminRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noDogsTextViewAdmin = findViewById(R.id.noDogsTextViewAdmin);

        addDogFab = findViewById(R.id.addDogFab);
        addDogFab.setOnClickListener(v -> {
            Intent intent = new Intent(DogListAdminActivity.this, AddDogActivity.class);
            startActivity(intent);
        });

        dogList = new ArrayList<>();
        adapter = new DogListAdminAdapter(dogList, this);
        recyclerView.setAdapter(adapter);

        RetrofitService retrofitService = new RetrofitService();
        dogApiService = retrofitService.getRetrofit().create(DogApiService.class);

        loadDogs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDogs();
    }

    private void loadDogs() {
        Call<List<Dog>> call = dogApiService.getAllDogs(null);
        call.enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(@NonNull Call<List<Dog>> call, @NonNull Response<List<Dog>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dogList.clear();
                    dogList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (dogList.isEmpty()) {
                        noDogsTextViewAdmin.setVisibility(View.VISIBLE);
                    } else {
                        noDogsTextViewAdmin.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(DogListAdminActivity.this, "Failed to load dogs: " +
                            (response.errorBody() != null ? response.code() : "Unknown error"), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Dog>> call, @NonNull Throwable t) {
                Toast.makeText(DogListAdminActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onEditClick(int position) {
        Dog dog = dogList.get(position);
        Intent intent = new Intent(this, UpdateDogActivity.class);
        intent.putExtra("DOG_ID", dog.getId());
        intent.putExtra("DOG_NAME", dog.getName());
        intent.putExtra("DOG_BREED", dog.getBreed());
        intent.putExtra("DOG_AGE", dog.getAge());
        intent.putExtra("DOG_DESCRIPTION", dog.getDescription());
        intent.putExtra("DOG_ADOPTED", dog.getAdopted());
        intent.putExtra("DOG_IMAGE_URL", dog.getImageUrl());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        Dog dog = dogList.get(position);
        deleteDog(dog.getId(), position);
    }

    private void deleteDog(long dogId, int position) {
        Call<Void> call = dogApiService.deleteDog(dogId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    dogList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(DogListAdminActivity.this, "Dog deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DogListAdminActivity.this, "Failed to delete dog", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(DogListAdminActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dog_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            LoginActivity.clearUserSession(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}