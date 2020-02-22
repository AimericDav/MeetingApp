package aimeric.david.meetingapp;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Aimeric on 14/02/2020.
 */
public class Meeting {

    private String name;
    private String location;
    private String email;

    private Calendar dateAndTime;

    public Meeting(String name, String location, String email, Calendar dateAndTime) {
        this.name = name;
        this.location = location;
        this.email = email;
        this.dateAndTime = dateAndTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Calendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
