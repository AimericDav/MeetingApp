package aimeric.david.meetingapp;

import java.util.List;

/**
 * Created by Aimeric on 14/02/2020.
 */
public class Meeting {

    private String name;
    private String hour;
    private String location;
    private String email;
    private String date;

    public Meeting(String name, String hour, String location, String email, String date) {
        this.name = name;
        this.hour = hour;
        this.location = location;
        this.email = email;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
