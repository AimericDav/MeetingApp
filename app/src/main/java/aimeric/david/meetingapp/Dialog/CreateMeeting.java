package aimeric.david.meetingapp.Dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import aimeric.david.meetingapp.R;

public class CreateMeeting extends DialogFragment {

    private Button buttonHour;
    private TextView heureTextView;

    private Button buttonDate;
    private TextView dateTextView;

    private Button buttonLocation;
    private TextView locationTextView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_create_meeting, container, false);

        final Context mContext = view.getContext();

        Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        buttonHour = view.findViewById(R.id.heure_button);
        heureTextView = view.findViewById(R.id.heure_minute_text);
        buttonDate = view.findViewById(R.id.date_button);
        dateTextView = view.findViewById(R.id.date_text);
        buttonLocation = view.findViewById(R.id.location_button);
        locationTextView = view.findViewById(R.id.location_text);

        buttonHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** TimePickerDialog */
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourChoice, int minuteChoice) {
                        if(minuteChoice < 10){
                            heureTextView.setText(hourChoice + "H" + "0"+ minuteChoice);
                        }else {
                            heureTextView.setText(hourChoice + "H" + minuteChoice);
                        }

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
                        String monthString = "";
                        String dayString = "";
                        monthOfyear = monthOfyear + 1;
                        if(monthOfyear < 10){
                            monthString = "0";
                        }
                        if(dayOfMonth < 10){
                            dayString = "0";
                        }
                        dateTextView.setText(dayString + dayOfMonth +"/" + monthString + monthOfyear +"/" + year);
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

        return view;
    }
}
