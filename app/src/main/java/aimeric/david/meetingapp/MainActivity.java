package aimeric.david.meetingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import aimeric.david.meetingapp.DI.DI;
import aimeric.david.meetingapp.Dialog.CreateMeeting;
import aimeric.david.meetingapp.service.MeetingApiService;

public class MainActivity extends AppCompatActivity {

    private MeetingApiService mMeetingApiService;

    /** RecyclerView */
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MeetingAdapter mMeetingAdapter;

    private List<Meeting> mMeetingList;

    /** MainActivity XML */
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Initier ApiService */
        mMeetingApiService = DI.getMeetingApiService();
        mMeetingList = mMeetingApiService.getMeetings();

        /** Initier RecyclerView */
        mRecyclerView = findViewById(R.id.meeting_recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMeetingAdapter = new MeetingAdapter(mMeetingList);
        mRecyclerView.setAdapter(mMeetingAdapter);

        /** Test setFont */


        /** Initier FAB  */
        addButton = findViewById(R.id.add_button);

        /** OnClick FAB */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Meeting
                //TEST DIALOG ALERT
                CreateMeeting createMeeting = new CreateMeeting();
                createMeeting.show(getSupportFragmentManager(), "Create Meeting Fragment");

            }
        });

    }
    private void initList() {
        mMeetingList = mMeetingApiService.getMeetings();
    }
}
