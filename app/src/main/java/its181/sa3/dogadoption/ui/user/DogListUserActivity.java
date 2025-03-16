package its181.sa3.dogadoption.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.ui.adapter.DogListAdapter;
import its181.sa3.dogadoption.data.model.Dog;

public class DogListUserActivity extends AppCompatActivity implements DogListAdapter.OnDetailsClickListener {
    private RecyclerView recyclerView;
    private DogListAdapter adapter;
    private List<Dog> dogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list_user);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initDogData();

        adapter = new DogListAdapter(dogList, this);
        recyclerView.setAdapter(adapter);
    }

    private void initDogData() {
        dogList = new ArrayList<>();

        dogList.add(new Dog(1, "Max", "Golden Retriever", "3 years", "Dog Description", false, R.drawable.ic_launcher_foreground));
        dogList.add(new Dog(2, "Bella", "Labrador", "2 years", "Dog Description", false, R.drawable.ic_launcher_foreground));
        dogList.add(new Dog(3, "Charlie", "German Shepherd", "4 years", "Dog Description", true, R.drawable.ic_launcher_foreground));
        dogList.add(new Dog(4, "Luna", "Husky", "1 year", "Dog Description", false, R.drawable.ic_launcher_foreground));
        dogList.add(new Dog(5, "Cooper", "Beagle", "5 years", "Dog Description", false, R.drawable.ic_launcher_foreground));
        dogList.add(new Dog(6, "Lucy", "Poodle", "2 years", "Dog Description", true, R.drawable.ic_launcher_foreground));
        dogList.add(new Dog(7, "Buddy", "Bulldog", "3 years", "Dog Description", false, R.drawable.ic_launcher_foreground));
        dogList.add(new Dog(8, "Daisy", "Dachshund", "4 years", "Dog Description", false, R.drawable.ic_launcher_foreground));
    }

    @Override
    public void onDetailsClick(int position) {
        Dog dog = dogList.get(position);

        Intent intent = new Intent(this, DogDetailActivity.class);
        intent.putExtra("DOG_ID", dog.getId());
        startActivity(intent);
    }
}