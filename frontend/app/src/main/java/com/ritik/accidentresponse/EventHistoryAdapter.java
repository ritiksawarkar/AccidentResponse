package com.ritik.accidentresponse;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventHistoryAdapter extends RecyclerView.Adapter<EventHistoryAdapter.EventViewHolder> {

    private final List<EventModel> eventList;

    public EventHistoryAdapter(List<EventModel> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_history, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventModel event = eventList.get(position);
        Context context = holder.itemView.getContext();

        holder.txtEventType.setText(event.getType());
        holder.txtEventStatus.setText(event.getStatus());
        
        // Format timestamp with middle dot as requested
        String timestamp = event.getDate() + " · " + event.getTime();
        holder.txtEventTimestamp.setText(timestamp);
        
        // Labels are now in separate layout elements, so we just set the values
        holder.txtEventLocation.setText(event.getLocation());
        holder.txtEventId.setText(event.getIncidentId());

        // Map status to specific color indicators
        int statusColor;

        switch (event.getStatus()) {
            case "Normal":
            case "Resolved":
                statusColor = ContextCompat.getColor(context, R.color.status_safe);
                break;
            case "Possible Accident":
            case "Warning":
            case "System Warning":
                statusColor = ContextCompat.getColor(context, R.color.status_warning);
                break;
            case "Confirmed Emergency":
            case "Confirmed Accident":
                statusColor = ContextCompat.getColor(context, R.color.status_danger);
                break;
            case "Cancelled":
                statusColor = ContextCompat.getColor(context, R.color.text_sub);
                break;
            default:
                statusColor = ContextCompat.getColor(context, R.color.text_sub);
                break;
        }

        // Apply colors to dot and status text
        holder.viewStatusDot.setBackgroundTintList(ColorStateList.valueOf(statusColor));
        holder.txtEventStatus.setTextColor(statusColor);

        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context, EventDetailsActivity.class);
            intent.putExtra("event_type", event.getType());
            intent.putExtra("event_status", event.getStatus());
            intent.putExtra("event_date", event.getDate());
            intent.putExtra("event_time", event.getTime());
            intent.putExtra("event_location", event.getLocation());
            intent.putExtra("event_id", event.getIncidentId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        View viewStatusDot;
        TextView txtEventType, txtEventStatus, txtEventTimestamp, txtEventLocation, txtEventId;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            viewStatusDot = itemView.findViewById(R.id.view_status_dot);
            txtEventType = itemView.findViewById(R.id.txt_event_type);
            txtEventStatus = itemView.findViewById(R.id.txt_event_status);
            txtEventTimestamp = itemView.findViewById(R.id.txt_event_timestamp);
            txtEventLocation = itemView.findViewById(R.id.txt_event_location);
            txtEventId = itemView.findViewById(R.id.txt_event_id);
        }
    }
}
