package aimeric.david.meetingapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import aimeric.david.meetingapp.Meeting;

/**
 * Created by Aimeric on 15/02/2020.
 */
public class DummyMeetingApiService implements MeetingApiService {

        private List<Meeting> meetings = DummyGeneratorMeeting.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void addMeeting(Meeting meeting) { meetings.add(meeting); }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public List<Meeting> getDisplayMeetingWithDate(Calendar calendarStart, Calendar calendarEnd) {
        List<Meeting> meetingListTemp = new ArrayList<>();
        for(Meeting meeting : meetings){
            if(meeting.getDateAndTime().after(calendarStart) && meeting.getDateAndTime().before(calendarEnd)){
                meetingListTemp.add(meeting);
            }
        }
        return meetingListTemp;
    }


}
