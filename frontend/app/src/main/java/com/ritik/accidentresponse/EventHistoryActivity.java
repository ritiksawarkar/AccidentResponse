package com.ritik.accidentresponse;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventHistoryActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView txtTotalEvents;
    private RecyclerView recyclerEvents;
    private LinearLayout layoutEmptyState;
    private EventHistoryAdapter adapter;
    private List<EventModel> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_history);

        initViews();
        setupRecyclerView();
        loadMockEvents();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        txtTotalEvents = findViewById(R.id.txt_total_events);
        recyclerEvents = findViewById(R.id.recycler_events);
        layoutEmptyState = findViewById(R.id.layout_empty_state);
    }

    private void setupRecyclerView() {
        eventList = new ArrayList<>();
        recyclerEvents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventHistoryAdapter(eventList);
        recyclerEvents.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadMockEvents() {
        // Clear existing just in case
        eventList.clear();

        // Adding events - Newest will be at the top after reversal
        
        // Event 1 (Earliest)
        eventList.add(new EventModel(
                "System Check",
                "29 Sep 2026",
                "10:15 AM",
                "Normal",
                "Available",
                "EVT-0001"
        ));

        // Event 2
        eventList.add(new EventModel(
                "Obstacle Detected",
                "29 Sep 2026",
                "10:42 AM",
                "Resolved",
                "Available",
                "EVT-0002"
        ));

        // Event 3
        eventList.add(new EventModel(
                "Possible Accident",
                "29 Sep 2026",
                "11:08 AM",
                "Cancelled",
                "Available",
                "EVT-0003"
        ));

        // Event 4 (Latest)
        eventList.add(new EventModel(
                "System Warning",
                "29 Sep 2026",
                "11:24 AM",
                "Resolved",
                "Unavailable",
                "EVT-0004"
        ));

        // Reverse to show newest first as requested
        Collections.reverse(eventList);

        // Update UI
        updateUI();
    }

    private void updateUI() {
        int total = eventList.size();
        txtTotalEvents.setText(String.valueOf(total));

        if (total == 0) {
            recyclerEvents.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            recyclerEvents.setVisibility(View.VISIBLE);
            layoutEmptyState.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }
}
