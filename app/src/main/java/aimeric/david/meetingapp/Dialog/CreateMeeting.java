package aimeric.david.meetingapp.Dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aimeric.david.meetingapp.DI.DI;
import aimeric.david.meetingapp.MainActivity;
import aimeric.david.meetingapp.Meeting;
import aimeric.david.meetingapp.R;
import aimeric.david.meetingapp.service.MeetingApiService;

public class CreateMeeting extends DialogFragment implements View.OnClickListener {

    private Button buttonHour;

    private Button buttonDate;

    private Button buttonLocation;

    private Button createButton;

    private EditText nameEditText;

    private ImageView emailAddButton;

    private EditText emailEditText;

    /** ApiService */
    final MeetingApiService apiService = DI.getMeetingApiService();

    /** Calendar return Meeting */
    final Calendar calendarMeeting = Calendar.getInstance();

    private OnButtonClickedListener mCallback;

    public interface OnButtonClickedListener {
        void onButtonClicked(View view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_create_meeting, container, false);

        final Context mContext = view.getContext();


        /** Calendar de base, temps réel */
        Calendar calendarReal = Calendar.getInstance();
        final int hour = calendarReal.get(Calendar.HOUR_OF_DAY);
        final int minute = calendarReal.get(Calendar.MINUTE);
        final int year = calendarReal.get(Calendar.YEAR);
        final int month = calendarReal.get(Calendar.MONTH);
        final int day = calendarReal.get(Calendar.DAY_OF_MONTH);

        buttonHour = view.findViewById(R.id.heure_button);
        buttonDate = view.findViewById(R.id.date_button);
        buttonLocation = view.findViewById(R.id.location_button);
        createButton = view.findViewById(R.id.create_button);
        nameEditText = view.findViewById(R.id.intitule_editText);
        emailEditText = view.findViewById(R.id.email_meeting);
        emailAddButton = view.findViewById(R.id.add_email);

        /** Hour Format */
        final DateFormat heureFormat = new SimpleDateFormat("HH'h'mm");
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        /** Init good text on Button */
        buttonHour.setHint(heureFormat.format(calendarReal.getTime()));
        buttonDate.setHint(dateFormat.format(calendarReal.getTime()));


        /** Meeting for add */


        buttonHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** TimePickerDialog */
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourChoice, int minuteChoice) {
                        calendarMeeting.set(Calendar.HOUR, hourChoice);
                        calendarMeeting.set(Calendar.MINUTE, minuteChoice);
                        // Change text hour
                        buttonHour.setHint(heureFormat.format(calendarMeeting.getTime()));

                    }
                },hour, minute, android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();
            }
        });

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfyear, int dayOfMonth) {
                        calendarMeeting.set(year, monthOfyear, dayOfMonth);
                        buttonDate.setHint(dateFormat.format(calendarMeeting.getTime()));
                 }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"Salle A", "Salle B", "Salle C", "Salle D", "Salle E", "Salle F", "Salle G", "Salle H", "Salle I", "Salle J"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Pick your location")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                buttonLocation.setHint(items[which]);
                            }
                        });
                builder.show();

            }
        });

        emailAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        createButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

    @Override
    public void onClick(View v) {
        List<String> email = new ArrayList<>();
        Meeting meeting = new Meeting(nameEditText.getText().toString(), buttonLocation.getHint().toString(), email , calendarMeeting);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyyHH'h'mm");
        apiService.addMeeting(meeting);
        mCallback.onButtonClicked(v);
        dismiss();
    }

}
