package com.ritik.accidentresponse;

public class EventModel {
    private final String type;
    private final String date;
    private final String time;
    private final String status;
    private final String location;
    private final String incidentId;

    public EventModel(String type, String date, String time, String status, String location, String incidentId) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.status = status;
        this.location = location;
        this.incidentId = incidentId;
    }

    public String getType() { return type; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public String getLocation() { return location; }
    public String getIncidentId() { return incidentId; }
}
