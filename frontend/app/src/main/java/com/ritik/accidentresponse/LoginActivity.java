package com.ritik.accidentresponse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout layoutEmail;
    private TextInputLayout layoutPassword;
    private TextInputEditText editEmail;
    private TextInputEditText editPassword;
    private MaterialButton btnSignIn;
    private View txtCreateAccount;
    private View txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        layoutEmail = findViewById(R.id.layout_email);
        layoutPassword = findViewById(R.id.layout_password);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        txtCreateAccount = findViewById(R.id.txt_create_account);
        txtForgotPassword = findViewById(R.id.txt_forgot_password);

        // Set up Sign In validation logic
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoginValidation();
            }
        });

        // Placeholder click listeners for non-functional links in this stage
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleLoginValidation() {
        // Reset errors
        layoutEmail.setError(null);
        layoutPassword.setError(null);

        String email = editEmail.getText() != null ? editEmail.getText().toString().trim() : "";
        String password = editPassword.getText() != null ? editPassword.getText().toString().trim() : "";

        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            layoutEmail.setError(getString(R.string.error_email_empty));
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            layoutPassword.setError(getString(R.string.error_password_empty));
            isValid = false;
        }

        if (isValid) {
            // Local mock validation success - navigate to Dashboard
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
