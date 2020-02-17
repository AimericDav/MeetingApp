package aimeric.david.meetingapp.DI;

import aimeric.david.meetingapp.service.DummyMeetingApiService;
import aimeric.david.meetingapp.service.MeetingApiService;

/**
 * Created by Aimeric on 15/02/2020.
 */
public class DI {

    private static MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

}
