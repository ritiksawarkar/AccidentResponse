package com.ritik.accidentresponse;

import java.io.Serializable;

public class ContactModel implements Serializable {
    private String id;
    private String name;
    private String relationship;
    private String phoneNumber;
    private boolean isPrimary;

    public ContactModel(String id, String name, String relationship, String phoneNumber, boolean isPrimary) {
        this.id = id;
        this.name = name;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.isPrimary = isPrimary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
