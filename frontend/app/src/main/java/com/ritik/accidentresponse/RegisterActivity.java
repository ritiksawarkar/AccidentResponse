package com.ritik.accidentresponse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout layoutName;
    private TextInputLayout layoutEmail;
    private TextInputLayout layoutPhone;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutConfirmPassword;

    private TextInputEditText editName;
    private TextInputEditText editEmail;
    private TextInputEditText editPhone;
    private TextInputEditText editPassword;
    private TextInputEditText editConfirmPassword;

    private CheckBox checkTerms;
    private MaterialButton btnCreateAccount;
    private TextView txtSignIn;

    private SupabaseAuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Supabase Auth Service
        authService = new SupabaseAuthService(this);

        // Initialize views
        layoutName = findViewById(R.id.layout_name);
        layoutEmail = findViewById(R.id.layout_email);
        layoutPhone = findViewById(R.id.layout_phone);
        layoutPassword = findViewById(R.id.layout_password);
        layoutConfirmPassword = findViewById(R.id.layout_confirm_password);

        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
        editPassword = findViewById(R.id.edit_password);
        editConfirmPassword = findViewById(R.id.edit_confirm_password);

        checkTerms = findViewById(R.id.check_terms);
        btnCreateAccount = findViewById(R.id.btn_create_account);
        txtSignIn = findViewById(R.id.txt_sign_in);

        // Go back to Login
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Register button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistration();
            }
        });
    }

    private void handleRegistration() {

        // Reset errors
        layoutName.setError(null);
        layoutEmail.setError(null);
        layoutPhone.setError(null);
        layoutPassword.setError(null);
        layoutConfirmPassword.setError(null);

        String name = getText(editName);
        String email = getText(editEmail);
        String phone = getText(editPhone);
        String password = getText(editPassword);
        String confirmPassword = getText(editConfirmPassword);

        boolean isValid = true;

        // Name validation
        if (TextUtils.isEmpty(name)) {
            layoutName.setError(
                    getString(R.string.error_name_empty)
            );
            isValid = false;
        }

        // Email validation
        if (TextUtils.isEmpty(email)
                || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            layoutEmail.setError(
                    getString(R.string.error_email_invalid)
            );
            isValid = false;
        }

        // Phone validation
        if (TextUtils.isEmpty(phone)
                || phone.length() < 10) {

            layoutPhone.setError(
                    getString(R.string.error_phone_invalid)
            );
            isValid = false;
        }

        // Password validation
        if (TextUtils.isEmpty(password)
                || password.length() < 6) {

            layoutPassword.setError(
                    getString(R.string.error_password_too_short)
            );
            isValid = false;
        }

        // Confirm password validation
        if (!password.equals(confirmPassword)) {

            layoutConfirmPassword.setError(
                    getString(R.string.error_passwords_dont_match)
            );
            isValid = false;
        }

        // Terms validation
        if (!checkTerms.isChecked()) {

            Toast.makeText(
                    this,
                    R.string.error_terms_required,
                    Toast.LENGTH_SHORT
            ).show();

            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Disable button while request is running
        btnCreateAccount.setEnabled(false);
        btnCreateAccount.setText("Creating Account...");

        // Real Supabase registration
        authService.signUp(
                email,
                password,
                name,
                new SupabaseAuthService.AuthCallback() {

                    @Override
                    public void onSuccess(JSONObject response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                btnCreateAccount.setEnabled(true);
                                btnCreateAccount.setText("Create Account");

                                handleRegistrationSuccess(response);
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                btnCreateAccount.setEnabled(true);
                                btnCreateAccount.setText("Create Account");

                                Toast.makeText(
                                        RegisterActivity.this,
                                        message,
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        });
                    }
                }
        );
    }

    private void handleRegistrationSuccess(JSONObject response) {

        boolean hasSession =
                response.has("access_token");

        if (hasSession) {

            Toast.makeText(
                    this,
                    "Account created successfully.",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    RegisterActivity.this,
                    DashboardActivity.class
            );

            startActivity(intent);
            finish();

        } else {

            /*
             * Supabase may require email verification.
             * In that case there is no access token yet.
             */

            Toast.makeText(
                    this,
                    "Account created. Please verify your email before logging in.",
                    Toast.LENGTH_LONG
            ).show();

            finish();
        }
    }

    private String getText(TextInputEditText editText) {

        if (editText.getText() == null) {
            return "";
        }

        return editText.getText()
                .toString()
                .trim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (authService != null) {
            authService.shutdown();
        }
    }
}