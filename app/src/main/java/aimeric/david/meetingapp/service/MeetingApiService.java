package aimeric.david.meetingapp.service;

import java.util.List;

import aimeric.david.meetingapp.Meeting;

/**
 * Created by Aimeric on 15/02/2020.
 */
public interface MeetingApiService {

    List<Meeting> getMeetings();

}