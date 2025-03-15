package com.example.sa3_dogadoption;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sa3_dogadoption.model.User;
import com.example.sa3_dogadoption.retrofit.RetrofitService;
import com.example.sa3_dogadoption.retrofit.UserAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeComponents();
    }

    private void initializeComponents(){
        EditText input_username = findViewById(R.id.email);
        EditText input_email = findViewById(R.id.username);
        Button btn_Add = findViewById(R.id.btn_Add);

        RetrofitService retrofitService = new RetrofitService();
        UserAPI userAPI = retrofitService.getRetrofit().create(UserAPI.class);

        btn_Add.setOnClickListener(view ->{
            String username = String.valueOf(input_username.getText());
            String email = String.valueOf(input_email.getText());

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);

            userAPI.save(user)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Toast.makeText(MainActivity.this,"Save Successful!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Save Failed!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error Occured",t);
                        }
                    });
        });
    }
}