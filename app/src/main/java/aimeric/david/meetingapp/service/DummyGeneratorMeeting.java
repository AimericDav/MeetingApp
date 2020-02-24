package aimeric.david.meetingapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import aimeric.david.meetingapp.Meeting;

/**
 * Created by Aimeric on 15/02/2020.
 */
public class DummyGeneratorMeeting {

    private static Calendar calendar = Calendar.getInstance();
    private static List<String> emails = new ArrayList<>();

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Réunion A","Salle A", emails, calendar),
            new Meeting("Réunion B","Salle B", emails, calendar),
            new Meeting("Réunion C","Salle C", emails, calendar)
    );


    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
