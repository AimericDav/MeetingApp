package aimeric.david.meetingapp.service;

import java.util.Calendar;
import java.util.List;

import aimeric.david.meetingapp.Meeting;

/**
 * Created by Aimeric on 15/02/2020.
 */
public interface MeetingApiService {

    List<Meeting> getMeetings();

    void addMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    List<Meeting> getDisplayMeetingWithDate(Calendar calendarStart, Calendar calendarEnd);

}
