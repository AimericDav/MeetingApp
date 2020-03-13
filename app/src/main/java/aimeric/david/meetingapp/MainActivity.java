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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
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

    final int START = 0;
    final int END = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();

        calendarEnd.add(Calendar.YEAR, 1);
        calendarStart.add(Calendar.YEAR, -1);

        /** Initier ApiService */
        mMeetingApiService = DI.getMeetingApiService();
        mMeetingList = mMeetingApiService.getMeetings();

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
        mMeetingList.clear();
        mMeetingList.addAll(mMeetingApiService.getMeetings());
        mMeetingAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClickDelete(Meeting meeting) {
        mMeetingApiService.deleteMeeting(meeting);
        mMeetingList.clear();
        mMeetingList.addAll(mMeetingApiService.getMeetings());
        mMeetingAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        MenuItem itemSearch = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mMeetingAdapter.getFilter().filter(s);
                return false;
            }
        });
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
            case R.id.menu_debut:
                createDatePicker(START);
                return true;
            case R.id.menu_fin:
                createDatePicker(END);
                return true;
            case R.id.menu_reinitialiser:
                calendarEnd.add(Calendar.YEAR, 1);
                calendarStart.add(Calendar.YEAR, -1);
                mMeetingList.clear();
                mMeetingList.addAll(mMeetingApiService.getMeetings());
                mMeetingAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createDatePicker(final int val){

        Calendar baseCalendar = Calendar.getInstance();
        final int year = baseCalendar.get(Calendar.YEAR);
        final int month = baseCalendar.get(Calendar.MONTH);
        final int day = baseCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfyear, int dayOfMonth) {
                if(val == START){
                    calendarStart.clear();
                    calendarStart.set(year, monthOfyear, dayOfMonth, 0, 0, 0);
                } else if( val == END){
                    calendarEnd.clear();
                    calendarEnd.set(year, monthOfyear, dayOfMonth, 23, 59, 59);
                }
                mMeetingList.clear();
                mMeetingList.addAll(mMeetingApiService.getDisplayMeetingWithDate(calendarStart, calendarEnd));
                mMeetingAdapter.notifyDataSetChanged();
            }
        }, year, month, day);

        datePickerDialog.show();
    }


}
