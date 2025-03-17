package its181.sa3.dogadoption.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.Dog;
import its181.sa3.dogadoption.data.remote.DogApiService;
import its181.sa3.dogadoption.data.remote.RetrofitService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list_user);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

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

        Call<List<Dog>> call = dogApiService.getAllDogs(false);
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
}