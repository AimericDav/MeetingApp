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
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import aimeric.david.meetingapp.DI.DI;
import aimeric.david.meetingapp.MainActivity;
import aimeric.david.meetingapp.Meeting;
import aimeric.david.meetingapp.R;
import aimeric.david.meetingapp.service.MeetingApiService;

public class CreateMeeting extends DialogFragment {

    private Button buttonHour;
    private TextView hourTextView;

    private Button buttonDate;
    private TextView dateTextView;

    private Button buttonLocation;
    private TextView locationTextView;

    private Button createButton;

    private EditText nameEditText;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_create_meeting, container, false);

        final Context mContext = view.getContext();

        /** ApiService */
        final MeetingApiService apiService = DI.getMeetingApiService();

        /** Calendar de base, temps réel */
        Calendar calendarReal = Calendar.getInstance();
        final int hour = calendarReal.get(Calendar.HOUR_OF_DAY);
        final int minute = calendarReal.get(Calendar.MINUTE);
        final int year = calendarReal.get(Calendar.YEAR);
        final int month = calendarReal.get(Calendar.MONTH);
        final int day = calendarReal.get(Calendar.DAY_OF_MONTH);

        /** Calendar return Meeting */
        final Calendar calendarMeeting = Calendar.getInstance();

        buttonHour = view.findViewById(R.id.heure_button);
        hourTextView = view.findViewById(R.id.heure_minute_text);
        buttonDate = view.findViewById(R.id.date_button);
        dateTextView = view.findViewById(R.id.date_text);
        buttonLocation = view.findViewById(R.id.location_button);
        locationTextView = view.findViewById(R.id.location_text);
        createButton = view.findViewById(R.id.create_button);
        nameEditText = view.findViewById(R.id.intitule_editText);

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
                        DateFormat heureFormmat = new SimpleDateFormat("HH'h'mm");
                        // Change text hour
                        hourTextView.setText(heureFormmat.format(calendarMeeting.getTime()));

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
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        dateTextView.setText(dateFormat.format(calendarMeeting.getTime()));
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
                                locationTextView.setText(items[which]);
                            }
                        });
                builder.show();

            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = new Meeting(nameEditText.getText().toString(), locationTextView.getText().toString(), "", calendarMeeting);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyyHH'h'mm");
                apiService.addMeeting(meeting);
            }
        });

        return view;
    }
}
