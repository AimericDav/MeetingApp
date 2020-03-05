package aimeric.david.meetingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import aimeric.david.meetingapp.DI.DI;
import aimeric.david.meetingapp.Dialog.CreateMeeting;
import aimeric.david.meetingapp.service.MeetingApiService;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements CreateMeeting.OnButtonClickedListener, MeetingAdapter.Listener {

    private MeetingApiService mMeetingApiService;

    /** RecyclerView */
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MeetingAdapter mMeetingAdapter;

    private List<Meeting> mMeetingList;

    /** MainActivity XML */
    private FloatingActionButton addButton;

    /** Calendar */
    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();

        /** Initier ApiService */
        mMeetingApiService = DI.getMeetingApiService();
        mMeetingList = mMeetingApiService.getMeetings();
        mMeetingList.get(0).getEmail().add("david.aimeric@outlook.fr");
        mMeetingList.get(0).getEmail().add("marie.dore18072001@gmail.com");

        /** Initier RecyclerView */
        mRecyclerView = findViewById(R.id.meeting_recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMeetingAdapter = new MeetingAdapter(mMeetingList, this);
        mRecyclerView.setAdapter(mMeetingAdapter);

        /** Initier XML  */
        addButton = findViewById(R.id.add_button);

        /** OnClick FAB */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMeeting createMeeting = new CreateMeeting();
                createMeeting.show(getSupportFragmentManager(), "Create Meeting Fragment");
            }
        });

    }

    @Override
    public void onButtonClicked(View view) {
        //Notify the adapter for refresh the list
        mMeetingAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClickDelete(Meeting meeting) {
        mMeetingApiService.deleteMeeting(meeting);
        mMeetingAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                //Action
                return true;
            case R.id.menu_debut:
                createDatePicker(calendarStart);
                mMeetingList = mMeetingApiService.getDisplayMeetingWithDate(calendarStart, calendarEnd);
                mMeetingAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_fin:
                createDatePicker(calendarEnd);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Calendar createDatePicker(final Calendar calendar){

        Calendar baseCalendar = Calendar.getInstance();
        final int year = baseCalendar.get(Calendar.YEAR);
        final int month = baseCalendar.get(Calendar.MONTH);
        final int day = baseCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfyear, int dayOfMonth) {
                calendar.set(year, monthOfyear, dayOfMonth);
            }
        }, year, month, day);

        datePickerDialog.show();
        return calendar;
    }


}
