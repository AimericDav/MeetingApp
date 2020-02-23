package aimeric.david.meetingapp.service;

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
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }


}
