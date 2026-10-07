package com.ritik.accidentresponse;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LiveLocationActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialButton btnRefresh;
    private TextView txtLatitude, txtLongitude, txtLastUpdated, txtLocationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_location);

        initViews();
        displayMockLocation();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        btnRefresh = findViewById(R.id.btn_refresh_location);
        txtLatitude = findViewById(R.id.txt_latitude);
        txtLongitude = findViewById(R.id.txt_longitude);
        txtLastUpdated = findViewById(R.id.txt_last_updated);
        txtLocationStatus = findViewById(R.id.txt_location_status);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        
        btnRefresh.setOnClickListener(v -> showGpsIntegrationMessage());
    }

    private void displayMockLocation() {
        txtLatitude.setText(R.string.location_mock_lat);
        txtLongitude.setText(R.string.location_mock_long);
        txtLastUpdated.setText(R.string.location_mock_updated);
        txtLocationStatus.setText(R.string.location_mock_status);
    }

    private void showGpsIntegrationMessage() {
        Toast.makeText(this, R.string.location_refresh_msg, Toast.LENGTH_LONG).show();
    }
}
