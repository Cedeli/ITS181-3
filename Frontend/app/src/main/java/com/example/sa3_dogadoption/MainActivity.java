package com.example.sa3_dogadoption;

import static com.example.sa3_dogadoption.R.*;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.sa3_dogadoption.model.Dog;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<Dog> dogList;
    DogAdapter dogAdapter;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

       ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
           Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
           return insets;
        });

        dogList.add(new Dog("Duglas", "Happy dog", "hq720"));
        dogList.add(new Dog("Buddy", "Playful pup", "hqdefault"));
        dogList.add(new Dog("Max", "Loyal companion", "maxresdefault"));
        dogList.add(new Dog("Charlie", "Gentle giant", "hq720"));
        dogList.add(new Dog("Rocky", "Energetic friend", "hqdefault"));

        listView = findViewById(R.id.listDog);
        dogAdapter = new DogAdapter(dogList);
        listView.setAdapter(dogAdapter);


//    private void initializeComponents(){
//        EditText input_username = findViewById(R.id.email);
//        EditText input_email = findViewById(R.id.username);
//        Button btn_Add = findViewById(R.id.btn_Add);
//
//        RetrofitService retrofitService = new RetrofitService();
//        UserAPI userAPI = retrofitService.getRetrofit().create(UserAPI.class);
//
//        btn_Add.setOnClickListener(view ->{
//            String username = String.valueOf(input_username.getText());
//            String email = String.valueOf(input_email.getText());
//
//            User user = new User();
//            user.setUsername(username);
//            user.setEmail(email);
//
//            userAPI.save(user)
//                    .enqueue(new Callback<User>() {
//                        @Override
//                        public void onResponse(Call<User> call, Response<User> response) {
//                            Toast.makeText(MainActivity.this,"Save Successful!", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<User> call, Throwable t) {
//                            Toast.makeText(MainActivity.this, "Save Failed!", Toast.LENGTH_SHORT).show();
//                            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error Occured",t);
//                        }
//                    });
//        });
//    }
    }

    class DogAdapter extends ArrayAdapter<Dog> {
        public DogAdapter(List<Dog> dogs) {
            super(MainActivity.this, R.layout.card_item, dogs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Recycle convertView if possible
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.card_item, parent, false);
            }

            TextView dogName = view.findViewById(R.id.dogName);
            TextView imageDescription = view.findViewById(R.id.imageDescription);
            ImageView imageView = view.findViewById(R.id.imageView);

            // Get the current dog based on position
            Dog dog = getItem(position);

            dogName.setText(dog.getName());
            imageDescription.setText(dog.getDescription());
            // Set the image from the resources using getIdentifier()
            Resources resources = getResources();
            int resourceId = resources.getIdentifier(dog.getImagesrc(), "drawable", getPackageName());
            imageView.setImageResource(resourceId);

            return view;
        }
    }
}