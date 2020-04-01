package aimeric.david.meetingapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aimeric.david.meetingapp.DI.DI;
import aimeric.david.meetingapp.service.MeetingApiService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class MeetingServiceTest {

    MeetingApiService apiService = DI.getNewInstanceApiService();

    private static Calendar calendar = Calendar.getInstance();
    private static List<String> emails = new ArrayList<>();

    @Test
    public void getMeetingsIsEmpty(){
        assertTrue(apiService.getMeetings().isEmpty());
    }

    @Test
    public void addOnGetMeetingsWithSucces(){
        assertTrue(apiService.getMeetings().isEmpty());
        Meeting meeting_1 = new Meeting("Réunion A","Salle A", emails, calendar);
        Meeting meeting_2 = new Meeting("Réunion B","Salle B", emails, calendar);
        apiService.addMeeting(meeting_1);
        apiService.addMeeting(meeting_2);
        assertEquals(2, apiService.getMeetings().size());
        assertTrue(apiService.getMeetings().contains(meeting_1));
        assertTrue(apiService.getMeetings().contains(meeting_2));
    }

    @Test
    public void deleteMeetingWithSucces(){
        assertTrue(apiService.getMeetings().isEmpty());
        Meeting meeting_1 = new Meeting("Réunion A","Salle A", emails, calendar);
        apiService.addMeeting(meeting_1);
        assertEquals(1, apiService.getMeetings().size());
        assertTrue(apiService.getMeetings().contains(meeting_1));
        apiService.deleteMeeting(meeting_1);
        assertTrue(apiService.getMeetings().isEmpty());
    }

    @Test
    public void getDisplayMeetingWithCalendarWithSucces(){
        assertTrue(apiService.getMeetings().isEmpty());

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.clear();
        calendarStart.set(2020, 04, 20, 0, 0, 0);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.clear();
        calendarEnd.set(2020, 04, 25, 23, 59, 59);

        Calendar calendarMeeting1 = Calendar.getInstance();
        calendarMeeting1.clear();
        calendarMeeting1.set(2020, 04, 22);

        Calendar calendarMeeting2 = Calendar.getInstance();
        calendarMeeting2.clear();
        calendarMeeting2.set(2020, 04, 19);

        Calendar calendarMeeting3 = Calendar.getInstance();
        calendarMeeting3.clear();
        calendarMeeting3.set(2020, 04, 26);

        Meeting meeting_1 = new Meeting("Réunion A","Salle A", emails, calendarMeeting1);
        Meeting meeting_2 = new Meeting("Réunion A","Salle A", emails, calendarMeeting2);
        Meeting meeting_3 = new Meeting("Réunion A","Salle A", emails, calendarMeeting3);

        apiService.addMeeting(meeting_1);
        apiService.addMeeting(meeting_2);
        apiService.addMeeting(meeting_3);

        List<Meeting> meetingsCalendar = apiService.getDisplayMeetingWithDate(calendarStart, calendarEnd);

        assertEquals(3, apiService.getMeetings().size());
        assertEquals(1, meetingsCalendar.size());
        assertTrue(meetingsCalendar.contains(meeting_1));
        assertFalse(meetingsCalendar.contains(meeting_2));
        assertFalse(meetingsCalendar.contains(meeting_3));
    }

    @Test
    public void testFilter(){

        MeetingApiService service = DI.getMeetingApiService();

        Meeting meeting_1 = new Meeting("Réunion A","Salle A", emails, calendar);
        Meeting meeting_2 = new Meeting("Réunion A","Salle B", emails, calendar);
        Meeting meeting_3 = new Meeting("Réunion A","Salle C", emails, calendar);

        service.addMeeting(meeting_1);
        service.addMeeting(meeting_2);
        service.addMeeting(meeting_3);

        MeetingAdapter meetingAdapter = new MeetingAdapter(service.getMeetings(), null);
        assertEquals(3, meetingAdapter.getItemCount());

        meetingAdapter.getFilter().filter("Salle A");
        assertEquals(1, meetingAdapter.getItemCount());
        meetingAdapter.getFilter().filter("Salle B");
        assertEquals(1, meetingAdapter.getItemCount());
        meetingAdapter.getFilter().filter("Salle");
        assertEquals(3, meetingAdapter.getItemCount());
        meetingAdapter.getFilter().filter("Salle D");
        assertEquals(0, meetingAdapter.getItemCount());
    }

}
