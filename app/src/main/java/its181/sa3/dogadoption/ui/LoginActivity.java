package its181.sa3.dogadoption.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.ui.admin.DogListAdminActivity;
import its181.sa3.dogadoption.ui.user.DogListUserActivity;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextView errorTextView;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        errorTextView = findViewById(R.id.errorTextView);

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

        boolean isAuthenticated = authenticate(email, password);

        if (isAuthenticated) {
            String userRole = getUserRole(email);

            Intent intent;
            if ("ADMIN".equals(userRole)) {
                intent = new Intent(LoginActivity.this, DogListAdminActivity.class);
            } else {
                intent = new Intent(LoginActivity.this, DogListUserActivity.class);
            }
            startActivity(intent);
            finish();
        } else {
            errorTextView.setText("Invalid email or password");
            errorTextView.setVisibility(View.VISIBLE);
        }
    }

    private boolean authenticate(String email, String password) {
        return true;
    }

    private String getUserRole(String email) {
        return "ADMIN";
    }
}