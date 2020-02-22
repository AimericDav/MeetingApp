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

    private  static Calendar dateAndTime;

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Réunion A","Salle A", "david.aimeric@outlook.fr", dateAndTime),
            new Meeting("Réunion B","Salle B", "lour.titouan@gmail.fr", dateAndTime),
            new Meeting("Réunion C","Salle C", "SysValerie@outlook.fr", dateAndTime)
    );


    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
