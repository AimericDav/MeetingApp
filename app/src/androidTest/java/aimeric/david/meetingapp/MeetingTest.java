package aimeric.david.meetingapp;


import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import aimeric.david.meetingapp.DI.DI;
import aimeric.david.meetingapp.service.MeetingApiService;

import static aimeric.david.meetingapp.RecyclerViewItemCountAssertion.withItemCount;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingTest {

    private MeetingApiService service = DI.getMeetingApiService();

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        MainActivity mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @After
    public void cleanMeeting(){
        for(Meeting meeting : service.getMeetings()){
            service.deleteMeeting(meeting);
        }
    }

    @Test
    public void meetingAddTest() {

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(0));

        onView(allOf(withId(R.id.add_button), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.intitule_editText), isDisplayed()))
                .perform(replaceText("Reunion"), closeSoftKeyboard());

        onView(allOf(withId(R.id.participant_editText), isDisplayed()))
                .perform(replaceText("a@gmail.com"), closeSoftKeyboard());

        onView(allOf(withId(R.id.add_email), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.participant_editText), isDisplayed()))
                .perform(replaceText("m@gmail.com"), closeSoftKeyboard());

        onView(allOf(withId(R.id.add_email), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.date_button), isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.heure_button), isDisplayed()))
                .perform(click());

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatRadioButton")), withText("PM"), isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.create_button), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.meeting_recyclerview), isDisplayed())).check(withItemCount(1));

    }

    @Test
    public void meetingDeleteTest() {

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(0));

        newMeeting(2020, 3, 20);

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(1));

        onView(allOf(withId(R.id.trash), isDisplayed()))
                .perform(click());

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(0));

    }

    @Test
    public void meetingFilterTest() {

        newMeeting(2020, 3, 20);
        newMeeting(2020, 3, 25);

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(2));

        onView(allOf(withContentDescription("More options"), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.title), withText("Début"), isDisplayed()))
                .perform(click());

        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 3, 18));

        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());

        onView(allOf(withContentDescription("More options"), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.title), withText("Fin"), isDisplayed()))
                .perform(click());

        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 3, 22));

        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(1));

        onView(allOf(withContentDescription("More options"), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.title), withText("Réinitialiser"), isDisplayed()))
                .perform(click());

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(2));

    }

    private void newMeeting(int year, int month, int day){

        onView(allOf(withId(R.id.add_button), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.add_email), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.date_button), isDisplayed()))
                .perform(click());

        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month, day));

        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.create_button), isDisplayed()))
                .perform(click());

    }

}
