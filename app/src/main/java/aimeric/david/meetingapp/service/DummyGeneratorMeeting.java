package aimeric.david.meetingapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aimeric.david.meetingapp.Meeting;

/**
 * Created by Aimeric on 15/02/2020.
 */
public class DummyGeneratorMeeting {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Réunion A", "14h00", "Salle A", "david.aimeric@outlook.fr", "07/09/2001"),
            new Meeting("Réunion B", "17h00", "Salle B", "lour.titouan@gmail.fr", "22/12/1997"),
            new Meeting("Réunion C", "9h00", "Salle C", "SysValerie@outlook.fr", "18/07/2001")
    );


    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
