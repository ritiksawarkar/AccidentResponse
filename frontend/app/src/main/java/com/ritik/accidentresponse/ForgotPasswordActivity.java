package com.ritik.accidentresponse;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputLayout layoutEmail;
    private TextInputEditText editEmail;
    private MaterialButton btnSend, btnSuccessBack;
    private View btnBack, txtBackToLogin, layoutForm, layoutSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        layoutEmail = findViewById(R.id.layout_email);
        editEmail = findViewById(R.id.edit_email);
        btnSend = findViewById(R.id.btn_send);
        btnBack = findViewById(R.id.btn_back);
        txtBackToLogin = findViewById(R.id.txt_back_to_login);
        layoutForm = findViewById(R.id.layout_form);
        layoutSuccess = findViewById(R.id.layout_success);
        btnSuccessBack = findViewById(R.id.btn_success_back);

        btnBack.setOnClickListener(v -> finish());
        txtBackToLogin.setOnClickListener(v -> finish());
        btnSuccessBack.setOnClickListener(v -> finish());

        btnSend.setOnClickListener(v -> handleResetRequest());
    }

    private void handleResetRequest() {
        layoutEmail.setError(null);
        String email = editEmail.getText() != null ? editEmail.getText().toString().trim() : "";

        if (TextUtils.isEmpty(email)) {
            layoutEmail.setError(getString(R.string.error_email_empty));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.setError(getString(R.string.error_email_invalid));
            return;
        }

        // Show Success State
        layoutForm.setVisibility(View.GONE);
        layoutSuccess.setVisibility(View.VISIBLE);
    }
}
