package aimeric.david.meetingapp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import aimeric.david.meetingapp.DI.DI;
import aimeric.david.meetingapp.service.MeetingApiService;

import static aimeric.david.meetingapp.RecyclerViewItemCountAssertion.withItemCount;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
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

        newMeeting(2020, 3, 20,0 );

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(1));

        onView(allOf(withId(R.id.trash), isDisplayed()))
                .perform(click());

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(0));

    }

    @Test
    public void meetingFilterTest() {

        newMeeting(2020, 3, 20, 0);
        newMeeting(2020, 3, 25, 0);

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

    @Test
    public void filterInstruTest() {

        newMeeting(2020, 03, 17, 0);
        newMeeting(2020, 03, 17, 1);
        newMeeting(2020, 03, 17, 2);

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Search"), isDisplayed()))
                .perform(click());

        onView(allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), isDisplayed()))
                .perform(replaceText("Salle A"), closeSoftKeyboard());

        onView(allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("Salle A"), isDisplayed()))
                .perform(pressImeActionButton());

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Clear query"), isDisplayed()))
                .perform(click());

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(1));

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Search"), isDisplayed()))
                .perform(click());

        onView(allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), isDisplayed()))
                .perform(replaceText("Salle H"), closeSoftKeyboard());

        onView(allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("Salle H"), isDisplayed()))
                .perform(pressImeActionButton());

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(0));

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Search"), isDisplayed()))
                .perform(click());

        onView(allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), isDisplayed()))
                .perform(replaceText("Salle"), closeSoftKeyboard());

        onView(allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("Salle"), isDisplayed()))
                .perform(pressImeActionButton());

        onView(withId(R.id.meeting_recyclerview)).check(withItemCount(3));


    }

    private void newMeeting(int year, int month, int day, int location){

        onView(allOf(withId(R.id.add_button), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.add_email), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.date_button), isDisplayed()))
                .perform(click());

        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month, day));

        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.location_button), isDisplayed()))
                .perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)))
                .atPosition(location);
        appCompatTextView.perform(click());

        onView(allOf(withId(R.id.create_button), isDisplayed()))
                .perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
