package com.example.sa3_dogadoption;

import static java.lang.Integer.parseInt;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sa3_dogadoption.adapter.DogAdapter;
import com.example.sa3_dogadoption.model.Dog;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DogAdapter dogAdapter;
    private List<Dog> dogList;

    private Button registerButton;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.listDog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Make use of the back end to propagate dogs
        // Propagate cards in RecyclerView
        dogList = new ArrayList<>();
        dogAdapter = new DogAdapter(this, dogList);

        dogList.add(new Dog("Duglas", "Happy Dog", "hq720"));
        dogList.add(new Dog("Bob", "Sad Dog", "dog"));
        dogList.add(new Dog("Max", "Angry Dog", "dug"));

        dogAdapter = new DogAdapter(this, dogList);
        recyclerView.setAdapter(dogAdapter);

        this.registerButton = findViewById(R.id.registerButton);
        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }


}