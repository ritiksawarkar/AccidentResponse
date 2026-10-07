package com.ritik.accidentresponse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Quick Action Buttons
        View btnLiveLocation = findViewById(R.id.btn_live_location);
        View btnEventHistory = findViewById(R.id.btn_event_history);
        View btnEmergencyContacts = findViewById(R.id.btn_emergency_contacts);
        View cardLatestEvent = findViewById(R.id.card_latest_event);
        View btnProfile = findViewById(R.id.btn_profile);
        View btnNotifications = findViewById(R.id.btn_notifications);

        // Profile Navigation
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Notifications Click
        btnNotifications.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this,
                    "Notifications will be available in the next development stage.",
                    Toast.LENGTH_SHORT).show();
            findViewById(R.id.notification_badge).setVisibility(View.GONE);
        });

        // Live Location Navigation
        btnLiveLocation.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LiveLocationActivity.class);
            startActivity(intent);
        });

        // Event History Navigation
        btnEventHistory.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, EventHistoryActivity.class);
            startActivity(intent);
        });

        // Emergency Contacts Navigation
        btnEmergencyContacts.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, EmergencyContactsActivity.class);
            startActivity(intent);
        });

        // Shared Click Listener for Unavailable Future Features
        View.OnClickListener unavailableFeatureListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, 
                        getString(R.string.dash_feature_unavailable), 
                        Toast.LENGTH_SHORT).show();
            }
        };

        cardLatestEvent.setOnClickListener(unavailableFeatureListener);
    }
}
