package com.ritik.accidentresponse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView txtProfileName, txtProfileEmail, txtProfilePhone;
    private View itemName, itemEmail, itemPhone, itemRole;
    private View itemVStatus, itemSafety, itemDevice;
    private MaterialButton btnEditProfile, btnLogout;
    private SwitchMaterial switchNotifications, switchAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        setupData();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        txtProfileName = findViewById(R.id.txt_profile_name);
        txtProfileEmail = findViewById(R.id.txt_profile_email);
        txtProfilePhone = findViewById(R.id.txt_profile_phone);

        itemName = findViewById(R.id.item_name);
        itemEmail = findViewById(R.id.item_email);
        itemPhone = findViewById(R.id.item_phone);
        itemRole = findViewById(R.id.item_role);

        itemVStatus = findViewById(R.id.item_v_status);
        itemSafety = findViewById(R.id.item_safety);
        itemDevice = findViewById(R.id.item_device);

        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnLogout = findViewById(R.id.btn_logout);

        switchNotifications = findViewById(R.id.switch_notifications);
        switchAlerts = findViewById(R.id.switch_alerts);
    }

    private void setupData() {
        // Account Info
        setInfoItem(itemName, getString(R.string.profile_label_name), getString(R.string.profile_name_default));
        setInfoItem(itemEmail, getString(R.string.profile_label_email), getString(R.string.profile_email_default));
        setInfoItem(itemPhone, getString(R.string.profile_label_phone), getString(R.string.profile_phone_default));
        setInfoItem(itemRole, getString(R.string.profile_label_role), getString(R.string.profile_value_role));

        // Vehicle Info
        setInfoItem(itemVStatus, getString(R.string.profile_label_status), getString(R.string.profile_value_connected));
        setInfoItem(itemSafety, getString(R.string.profile_label_safety), getString(R.string.profile_value_active));
        setInfoItem(itemDevice, getString(R.string.profile_label_device), getString(R.string.profile_value_device));
    }

    private void setInfoItem(View view, String label, String value) {
        TextView tvLabel = view.findViewById(R.id.tv_label);
        TextView tvValue = view.findViewById(R.id.tv_value);
        tvLabel.setText(label);
        tvValue.setText(value);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnEditProfile.setOnClickListener(v -> showEditProfileDialog());

        btnLogout.setOnClickListener(v -> showLogoutConfirmation());

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String status = isChecked ? "Enabled" : "Disabled";
            Toast.makeText(this, "Notifications " + status, Toast.LENGTH_SHORT).show();
        });

        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String status = isChecked ? "Enabled" : "Disabled";
            Toast.makeText(this, "Emergency Alerts " + status, Toast.LENGTH_SHORT).show();
        });
    }

    private void showEditProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_contact_form, null); // Reusing existing dialog layout pattern
        builder.setView(dialogView);

        TextView tvTitle = dialogView.findViewById(R.id.tv_dialog_title);
        TextInputLayout tilName = dialogView.findViewById(R.id.til_name);
        TextInputLayout tilPhone = dialogView.findViewById(R.id.til_phone);
        TextInputLayout tilRel = dialogView.findViewById(R.id.til_relationship);
        TextInputEditText etName = dialogView.findViewById(R.id.et_name);
        TextInputEditText etPhone = dialogView.findViewById(R.id.et_phone);
        View cbPrimary = dialogView.findViewById(R.id.cb_primary);

        tvTitle.setText(R.string.profile_dialog_edit_title);
        tilRel.setVisibility(View.GONE);
        cbPrimary.setVisibility(View.GONE);

        etName.setText(txtProfileName.getText());
        etPhone.setText(txtProfilePhone.getText());

        builder.setPositiveButton(R.string.contacts_btn_save, null);
        builder.setNegativeButton(R.string.contacts_btn_cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            boolean isValid = true;
            if (name.isEmpty()) {
                tilName.setError("Name is required");
                isValid = false;
            } else {
                tilName.setError(null);
            }

            if (phone.isEmpty()) {
                tilPhone.setError("Phone is required");
                isValid = false;
            } else {
                tilPhone.setError(null);
            }

            if (isValid) {
                txtProfileName.setText(name);
                txtProfilePhone.setText(phone);
                setInfoItem(itemName, getString(R.string.profile_label_name), name);
                setInfoItem(itemPhone, getString(R.string.profile_label_phone), phone);
                dialog.dismiss();
            }
        });
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout_confirm_title)
                .setMessage(R.string.logout_confirm_msg)
                .setPositiveButton(R.string.profile_btn_logout_clean, (dialog, which) -> {
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.contacts_btn_cancel, null)
                .show();
    }
}
