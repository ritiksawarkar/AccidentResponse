package com.ritik.accidentresponse;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ContactViewHolder> {

    public interface OnContactActionListener {
        void onEdit(ContactModel contact);
        void onDelete(ContactModel contact);
    }

    private final List<ContactModel> contactList;
    private final OnContactActionListener listener;

    public EmergencyContactAdapter(List<ContactModel> contactList, OnContactActionListener listener) {
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emergency_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contactList, position, listener);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRelationshipType;
        private final TextView tvBadgePriority;
        private final TextView tvContactName;
        private final TextView tvContactRelationship;
        private final TextView tvContactPhone;
        private final View btnContactEdit;
        private final View btnContactDelete;
        private final TextView tvSectionHeader;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRelationshipType = itemView.findViewById(R.id.tv_relationship_type);
            tvBadgePriority = itemView.findViewById(R.id.tv_badge_priority);
            tvContactName = itemView.findViewById(R.id.tv_contact_name);
            tvContactRelationship = itemView.findViewById(R.id.tv_contact_relationship);
            tvContactPhone = itemView.findViewById(R.id.tv_contact_phone);
            btnContactEdit = itemView.findViewById(R.id.btn_contact_edit);
            btnContactDelete = itemView.findViewById(R.id.btn_contact_delete);
            tvSectionHeader = itemView.findViewById(R.id.tv_section_header);
        }

        public void bind(final List<ContactModel> contactList, int position, final OnContactActionListener listener) {
            ContactModel contact = contactList.get(position);
            tvContactName.setText(contact.getName());
            tvContactRelationship.setText(contact.getRelationship());
            tvContactPhone.setText(contact.getPhoneNumber());

            // Relationship label
            String rel = contact.getRelationship().toLowerCase();
            if (rel.contains("brother") || rel.contains("sister") || rel.contains("parent") || rel.contains("mother") || rel.contains("father") || rel.contains("family") || rel.contains("wife") || rel.contains("husband")) {
                tvRelationshipType.setText("Family");
            } else if (rel.contains("friend")) {
                tvRelationshipType.setText("Friend");
            } else {
                tvRelationshipType.setText("Other");
            }

            // Setup Badge
            if (contact.isPrimary()) {
                tvBadgePriority.setText(itemView.getContext().getString(R.string.contacts_badge_primary));
                tvBadgePriority.setVisibility(View.VISIBLE);
            } else {
                tvBadgePriority.setText(itemView.getContext().getString(R.string.contacts_badge_secondary));
                tvBadgePriority.setVisibility(View.GONE); // Requirement says "Primary badge should use app's blue", doesn't explicitly say secondary needs one.
            }

            // Section Header logic for "OTHER CONTACTS"
            if (!contact.isPrimary() && (position == 0 || contactList.get(position - 1).isPrimary())) {
                tvSectionHeader.setVisibility(View.VISIBLE);
            } else {
                tvSectionHeader.setVisibility(View.GONE);
            }

            btnContactEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEdit(contact);
                }
            });

            btnContactDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDelete(contact);
                }
            });
        }
    }
}
