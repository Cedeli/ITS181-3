package its181.sa3.dogadoption.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import its181.sa3.dogadoption.R;
import its181.sa3.dogadoption.ui.user.DogListUserActivity;

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

        boolean isRegistered = register(email, password);

        if (isRegistered) {
            Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, DogListUserActivity.class);
            startActivity(intent);
            finish();
        } else {
            errorTextView.setText("Registration failed. Email may already be in use.");
            errorTextView.setVisibility(View.VISIBLE);
        }
    }

    private boolean register(String email, String password) {
        // TO DO: To be implemented by Gian
        return true;
    }
}