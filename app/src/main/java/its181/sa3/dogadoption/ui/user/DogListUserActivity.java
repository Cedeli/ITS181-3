package its181.sa3.dogadoption.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.Dog;
import its181.sa3.dogadoption.data.remote.DogApiService;
import its181.sa3.dogadoption.data.remote.RetrofitService;
import its181.sa3.dogadoption.ui.LoginActivity;
import its181.sa3.dogadoption.ui.adapter.DogListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogListUserActivity extends AppCompatActivity implements DogListAdapter.OnDetailsClickListener {
    private RecyclerView recyclerView;
    private DogListAdapter adapter;
    private List<Dog> dogList;
    private DogApiService dogApiService;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView noDogsTextView; // Added
    private Toolbar userToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list_user);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        noDogsTextView = findViewById(R.id.noDogsTextView);
        userToolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(userToolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dogList = new ArrayList<>();
        adapter = new DogListAdapter(dogList, this);
        recyclerView.setAdapter(adapter);

        RetrofitService retrofitService = new RetrofitService();
        dogApiService = retrofitService.getRetrofit().create(DogApiService.class);

        loadDogs();
        swipeRefreshLayout.setOnRefreshListener(this::loadDogs);
    }

    private void loadDogs() {
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        // Set to false if adopted dogs need to be hidden.
        // I was thinking keeping it displayed to show that it has been updated easily.
        Call<List<Dog>> call = dogApiService.getAllDogs(true);
        call.enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(@NonNull Call<List<Dog>> call, @NonNull Response<List<Dog>> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    dogList.clear();
                    if (response.body() != null) {
                        dogList.addAll(response.body());
                    }
                    adapter.notifyDataSetChanged();

                    if (dogList.isEmpty()) {
                        noDogsTextView.setVisibility(View.VISIBLE);
                    } else {
                        noDogsTextView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(DogListUserActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Dog>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(DogListUserActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDetailsClick(int position) {
        Dog dog = dogList.get(position);
        Intent intent = new Intent(this, DogDetailActivity.class);
        intent.putExtra("DOG_ID", dog.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDogs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dog_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}