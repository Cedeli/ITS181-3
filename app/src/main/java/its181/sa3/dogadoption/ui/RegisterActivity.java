package its181.sa3.dogadoption.ui;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import its181.sa3.dogadoption.ui.user.DogListUserActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout confirmPasswordInputLayout;
    private Button registerButton;
    private ImageButton backButton;
    private TextView errorTextView;
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
        setContentView(R.layout.activity_register);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);
        errorTextView = findViewById(R.id.errorTextView);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        RetrofitService retrofitService = new RetrofitService();
        userApiService = retrofitService.getRetrofit().create(UserApiService.class);
        registerButton.setOnClickListener(v -> registerUser());
        backButton.setOnClickListener(v -> finish());
    }
    private void registerUser() {
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
        confirmPasswordInputLayout.setError(null);
        errorTextView.setVisibility(View.GONE);
        String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(confirmPasswordEditText.getText()).toString().trim();
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
        if (password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters");
            return;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordInputLayout.setError("Passwords do not match");
            return;
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole("USER");
        Call<User> call = userApiService.registerUser(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User registeredUser = response.body();
                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_LOGGED_IN, true);
                    editor.putString(KEY_USER_EMAIL, registeredUser.getEmail());
                    editor.putString(KEY_USER_ROLE, registeredUser.getRole());
                    editor.putLong(KEY_USER_ID, registeredUser.getId());
                    editor.apply();
                    Intent intent = new Intent(RegisterActivity.this, DogListUserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    errorTextView.setText("Registration failed. Email may already be in use.");
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                errorTextView.setText("Network error: " + t.getMessage());
                errorTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}