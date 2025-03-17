package its181.sa3.dogadoption.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.data.model.User;
import its181.sa3.dogadoption.data.remote.RetrofitService;
import its181.sa3.dogadoption.data.remote.UserApiService;
import its181.sa3.dogadoption.ui.admin.DogListAdminActivity;
import its181.sa3.dogadoption.ui.user.DogListUserActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextView errorTextView;
    private Button loginButton;
    private Button registerButton;
    private UserApiService userApiService;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ROLE = "userRole";
    private static final String KEY_USER_ID = "userId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (isLoggedIn()) {
            redirectToEntrypoint();
            finish();
            return;
        }

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        errorTextView = findViewById(R.id.errorTextView);

        RetrofitService retrofitService = new RetrofitService();
        userApiService = retrofitService.getRetrofit().create(UserApiService.class);

        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
        errorTextView.setVisibility(View.GONE);

        String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailInputLayout.setError("Email is required");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Invalid email format");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordInputLayout.setError("Password is required");
            return;
        }

        Call<User> call = userApiService.getUserByEmail(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    if (password.equals(user.getPassword())) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(KEY_LOGGED_IN, true);
                        editor.putString(KEY_USER_EMAIL, user.getEmail());
                        editor.putString(KEY_USER_ROLE, user.getRole());
                        editor.putLong(KEY_USER_ID, user.getId());
                        editor.apply();

                        redirectToEntrypoint();

                    } else {
                        errorTextView.setText("Invalid email or password");
                        errorTextView.setVisibility(View.VISIBLE);
                    }
                } else {
                    errorTextView.setText("User not found or server error");
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorTextView.setText("Network error: " + t.getMessage());
                errorTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    private void redirectToEntrypoint() {
        String userRole = sharedPreferences.getString(KEY_USER_ROLE, "USER"); // Default to USER
        Intent intent;
        if ("ADMIN".equals(userRole)) {
            intent = new Intent(LoginActivity.this, DogListAdminActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, DogListUserActivity.class);
        }
        startActivity(intent);
        finish();
    }

    public static void clearUserSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}