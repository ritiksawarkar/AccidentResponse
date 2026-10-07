package com.ritik.accidentresponse;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class EventDetailsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvToolbarTitle;
    private TextView tvEventType, tvEventStatus, tvEventTime, tvEventDescription;
    private View viewStatusIndicator;
    private View detailId, detailLocation, detailCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        initViews();
        setupListeners();
        handleIntentData();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        tvEventType = findViewById(R.id.tv_event_type);
        tvEventStatus = findViewById(R.id.tv_event_status);
        tvEventTime = findViewById(R.id.tv_event_time);
        tvEventDescription = findViewById(R.id.tv_event_description);
        viewStatusIndicator = findViewById(R.id.view_status_indicator);

        detailId = findViewById(R.id.detail_id);
        detailLocation = findViewById(R.id.detail_location);
        detailCoordinates = findViewById(R.id.detail_coordinates);

        findViewById(R.id.btn_export).setOnClickListener(v -> 
            Toast.makeText(this, "Incident report export will be available in the next stage.", Toast.LENGTH_SHORT).show()
        );
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void handleIntentData() {
        String type = getIntent().getStringExtra("event_type");
        String status = getIntent().getStringExtra("event_status");
        String date = getIntent().getStringExtra("event_date");
        String time = getIntent().getStringExtra("event_time");
        String location = getIntent().getStringExtra("event_location");
        String id = getIntent().getStringExtra("event_id");

        if (type != null) {
            tvEventType.setText(type);
            tvToolbarTitle.setText(id);
            tvEventStatus.setText(status);
            tvEventTime.setText(date + " · " + time);

            setInfoItem(detailId, "Incident ID", id);
            setInfoItem(detailLocation, "Status", location);
            
            // Mock coordinates based on Nagpur region
            setInfoItem(detailCoordinates, "Coordinates", "21.1458° N, 79.0882° E");

            updateStatusUI(status);
        }
    }

    private void setInfoItem(View view, String label, String value) {
        TextView tvLabel = view.findViewById(R.id.tv_label);
        TextView tvValue = view.findViewById(R.id.tv_value);
        tvLabel.setText(label);
        tvValue.setText(value);
    }

    private void updateStatusUI(String status) {
        int color;
        switch (status) {
            case "Normal":
            case "Resolved":
                color = ContextCompat.getColor(this, R.color.status_safe);
                break;
            case "Possible Accident":
            case "Warning":
            case "System Warning":
                color = ContextCompat.getColor(this, R.color.status_warning);
                break;
            case "Confirmed Emergency":
            case "Confirmed Accident":
                color = ContextCompat.getColor(this, R.color.status_danger);
                break;
            case "Cancelled":
                color = ContextCompat.getColor(this, R.color.text_sub);
                break;
            default:
                color = ContextCompat.getColor(this, R.color.text_sub);
                break;
        }

        viewStatusIndicator.setBackgroundTintList(ColorStateList.valueOf(color));
        tvEventStatus.setTextColor(color);
    }
}
