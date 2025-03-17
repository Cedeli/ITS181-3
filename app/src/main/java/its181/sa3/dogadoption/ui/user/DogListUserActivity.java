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

        String testImage = "https://preview.redd.it/bwof59fjb2s91.jpg?width=906&format=pjpg&auto=webp&s=33281994eca39e7cc34d733c45a7ca9629207b99";

        dogList.add(new Dog(1L, "Max", "Golden Retriever", "3 years", "Dog Description", false, testImage));
        dogList.add(new Dog(2L, "Bella", "Labrador", "2 years", "Dog Description", false, testImage));
        dogList.add(new Dog(3L, "Charlie", "German Shepherd", "4 years", "Dog Description", true, testImage));
        dogList.add(new Dog(4L, "Luna", "Husky", "1 year", "Dog Description", false, testImage));
        dogList.add(new Dog(5L, "Cooper", "Beagle", "5 years", "Dog Description", false, testImage));
        dogList.add(new Dog(6L, "Lucy", "Poodle", "2 years", "Dog Description", true, testImage));
        dogList.add(new Dog(7L, "Buddy", "Bulldog", "3 years", "Dog Description", false, testImage));
        dogList.add(new Dog(8L, "Daisy", "Dachshund", "4 years", "Dog Description", false, testImage));
    }

    @Override
    public void onDetailsClick(int position) {
        Dog dog = dogList.get(position);

        Intent intent = new Intent(this, DogDetailActivity.class);
        intent.putExtra("DOG_ID", dog.getId());
        startActivity(intent);
    }
}