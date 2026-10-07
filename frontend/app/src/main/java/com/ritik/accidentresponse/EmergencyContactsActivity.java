package com.ritik.accidentresponse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmergencyContactsActivity extends AppCompatActivity implements EmergencyContactAdapter.OnContactActionListener {

    private RecyclerView rvContacts;
    private EmergencyContactAdapter adapter;
    private List<ContactModel> contactList;
    private LinearLayout layoutEmptyState;
    private MaterialButton btnAddContact;
    private MaterialButton btnAddContactEmpty;
    private ImageView btnBack;
    private android.widget.TextView txtTotalContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        initViews();
        setupMockData();
        setupRecyclerView();
        updateUIState();

        btnBack.setOnClickListener(v -> finish());
        btnAddContact.setOnClickListener(v -> showContactDialog(null));
        btnAddContactEmpty.setOnClickListener(v -> showContactDialog(null));
    }

    private void initViews() {
        rvContacts = findViewById(R.id.rv_contacts);
        layoutEmptyState = findViewById(R.id.layout_empty_state);
        btnAddContact = findViewById(R.id.btn_add_contact);
        btnAddContactEmpty = findViewById(R.id.btn_add_contact_empty);
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupMockData() {
        contactList = new ArrayList<>();
        contactList.add(new ContactModel("1", "Rahul Sawarkar", "Brother", "+91 90000 00001", true));
        contactList.add(new ContactModel("2", "Emergency Contact 2", "Family", "+91 90000 00002", false));
        contactList.add(new ContactModel("3", "Emergency Contact 3", "Friend", "+91 90000 00003", false));
    }

    private void setupRecyclerView() {
        adapter = new EmergencyContactAdapter(contactList, this);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(adapter);
    }

    private void updateUIState() {
        View layoutContent = findViewById(R.id.layout_content);
        View tvSectionPrimary = findViewById(R.id.tv_section_primary);

        if (contactList.isEmpty()) {
            layoutEmptyState.setVisibility(View.VISIBLE);
            btnAddContact.setVisibility(View.GONE);
            if (layoutContent != null) layoutContent.setVisibility(View.GONE);
        } else {
            sortContacts(); // Ensure primary contact is at the top
            layoutEmptyState.setVisibility(View.GONE);
            btnAddContact.setVisibility(View.VISIBLE);
            if (layoutContent != null) layoutContent.setVisibility(View.VISIBLE);
            
            // Check if there's at least one primary to show the header
            boolean hasPrimary = false;
            for (ContactModel c : contactList) {
                if (c.isPrimary()) {
                    hasPrimary = true;
                    break;
                }
            }
            if (tvSectionPrimary != null) {
                tvSectionPrimary.setVisibility(hasPrimary ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void sortContacts() {
        if (contactList.size() <= 1) return;
        
        List<ContactModel> sorted = new ArrayList<>();
        ContactModel primary = null;
        
        for (ContactModel c : contactList) {
            if (c.isPrimary()) {
                primary = c;
            } else {
                sorted.add(c);
            }
        }
        
        if (primary != null) {
            contactList.clear();
            contactList.add(primary);
            contactList.addAll(sorted);
        }
    }

    @Override
    public void onEdit(ContactModel contact) {
        showContactDialog(contact);
    }

    @Override
    public void onDelete(final ContactModel contact) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.contacts_confirm_delete_title)
                .setMessage(R.string.contacts_confirm_delete_msg)
                .setPositiveButton(R.string.contacts_btn_delete, (dialog, which) -> {
                    boolean wasPrimary = contact.isPrimary();
                    contactList.remove(contact);
                    
                    if (wasPrimary && !contactList.isEmpty()) {
                        contactList.get(0).setPrimary(true);
                    }
                    
                    adapter.notifyDataSetChanged();
                    updateUIState();
                })
                .setNegativeButton(R.string.contacts_btn_cancel, null)
                .show();
    }

    private void showContactDialog(final ContactModel contactToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_contact_form, null);
        builder.setView(dialogView);

        final TextInputLayout tilName = dialogView.findViewById(R.id.til_name);
        final TextInputLayout tilRelationship = dialogView.findViewById(R.id.til_relationship);
        final TextInputLayout tilPhone = dialogView.findViewById(R.id.til_phone);
        final TextInputEditText etName = dialogView.findViewById(R.id.et_name);
        final TextInputEditText etRelationship = dialogView.findViewById(R.id.et_relationship);
        final TextInputEditText etPhone = dialogView.findViewById(R.id.et_phone);
        final CheckBox cbPrimary = dialogView.findViewById(R.id.cb_primary);
        final android.widget.TextView tvTitle = dialogView.findViewById(R.id.tv_dialog_title);

        if (contactToEdit != null) {
            tvTitle.setText(R.string.contacts_dialog_edit_title);
            etName.setText(contactToEdit.getName());
            etRelationship.setText(contactToEdit.getRelationship());
            etPhone.setText(contactToEdit.getPhoneNumber());
            cbPrimary.setChecked(contactToEdit.isPrimary());
        } else {
            tvTitle.setText(R.string.contacts_dialog_add_title);
        }

        builder.setPositiveButton(R.string.contacts_btn_save, null);
        builder.setNegativeButton(R.string.contacts_btn_cancel, null);

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String rel = etRelationship.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            boolean isPrimary = cbPrimary.isChecked();

            boolean isValid = true;

            if (name.isEmpty()) {
                tilName.setError(getString(R.string.contacts_error_name));
                isValid = false;
            } else {
                tilName.setError(null);
            }

            if (rel.isEmpty()) {
                tilRelationship.setError(getString(R.string.contacts_error_relationship));
                isValid = false;
            } else {
                tilRelationship.setError(null);
            }

            if (phone.isEmpty()) {
                tilPhone.setError(getString(R.string.contacts_error_phone));
                isValid = false;
            } else if (!isValidPhoneNumber(phone)) {
                tilPhone.setError(getString(R.string.contacts_error_phone_invalid));
                isValid = false;
            } else {
                tilPhone.setError(null);
            }

            if (isValid) {
                if (isPrimary) {
                    clearOtherPrimaries();
                } else if (contactToEdit != null && contactToEdit.isPrimary()) {
                    // If we are editing the ONLY primary contact and uncheck "Primary",
                    // we should probably keep it primary or assign another one?
                    // Rule 4: "Only one contact can be PRIMARY."
                    // If the user tries to uncheck the ONLY primary, we'll allow it 
                    // and handle it during state update if needed, but the rule says 
                    // "There must NEVER be more than one PRIMARY contact."
                    // It doesn't explicitly forbid ZERO, but Rule 8 suggests 
                    // if one is deleted, another becomes primary.
                    // For now, if they uncheck it, it stays secondary.
                }

                if (contactToEdit != null) {
                    contactToEdit.setName(name);
                    contactToEdit.setRelationship(rel);
                    contactToEdit.setPhoneNumber(phone);
                    contactToEdit.setPrimary(isPrimary);
                } else {
                    contactList.add(new ContactModel(UUID.randomUUID().toString(), name, rel, phone, isPrimary));
                }

                // Final check: if no contact is primary, make the first one primary
                ensureOnePrimaryExists();

                adapter.notifyDataSetChanged();
                updateUIState();
                dialog.dismiss();
            }
        });
    }

    private boolean isValidPhoneNumber(String phone) {
        // Accept a normal Indian 10-digit mobile number.
        // Allow optional +91 prefix.
        return phone.matches("^(\\+91[\\-\\s]?)?[0-9]{10}$") || phone.matches("^(\\+91)?[0-9]{10}$");
    }

    private void ensureOnePrimaryExists() {
        if (contactList.isEmpty()) return;
        
        boolean hasPrimary = false;
        for (ContactModel contact : contactList) {
            if (contact.isPrimary()) {
                hasPrimary = true;
                break;
            }
        }
        
        if (!hasPrimary) {
            contactList.get(0).setPrimary(true);
        }
    }

    private void clearOtherPrimaries() {
        for (ContactModel contact : contactList) {
            contact.setPrimary(false);
        }
    }
}
