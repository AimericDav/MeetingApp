package aimeric.david.meetingapp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        MainActivity mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void meetingAddTest() {
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

        onView(allOf(withId(R.id.meeting_recyclerview), isDisplayed())).check();

    }

}
