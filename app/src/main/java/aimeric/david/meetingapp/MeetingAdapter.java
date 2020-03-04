package aimeric.david.meetingapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import aimeric.david.meetingapp.DI.DI;
import aimeric.david.meetingapp.service.MeetingApiService;
import de.greenrobot.event.EventBus;

/**
 * Created by Aimeric on 14/02/2020.
 */
public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>{

    List<Meeting> mMeetingList;
    MeetingApiService mApiService = DI.getMeetingApiService();

    Meeting mMeeting;

    Listener callback;
    public interface Listener {
        void onClickDelete(Meeting meeting);
    }

    public static class MeetingViewHolder extends RecyclerView.ViewHolder{
        TextView nameHourLocation;
        TextView email;
        TextView date;
        ImageView trash;
        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            nameHourLocation = itemView.findViewById(R.id.nameHourLocation_meeting);
            email = itemView.findViewById(R.id.email_meeting);
            date = itemView.findViewById(R.id.date_meeting);
            trash = itemView.findViewById(R.id.trash);
        }
    }

    public MeetingAdapter(List<Meeting> meetingList, Listener callback) {
        this.mMeetingList = meetingList;
        this.callback = callback;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent, false);
        MeetingViewHolder mMeetingViewHolder = new MeetingViewHolder(view);
        return mMeetingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MeetingViewHolder holder, final int position) {
        mMeeting = mMeetingList.get(position);
        DateFormat formatHour = new SimpleDateFormat("HH'h'mm");
        holder.nameHourLocation.setText(mMeeting.getName() + " - " + formatHour.format(mMeeting.getDateAndTime().getTime()) + " - " + mMeeting.getLocation());
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.date.setText(dateFormat.format(mMeeting.getDateAndTime().getTime()));
        holder.email.setText(mMeeting.getEmail().toString());
        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickDelete(mMeetingList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

}
