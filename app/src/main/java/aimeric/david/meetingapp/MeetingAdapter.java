package aimeric.david.meetingapp;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Aimeric on 14/02/2020.
 */
public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>{

    List<Meeting> mMeetingList;

    public static class MeetingViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView hour;
        TextView location;
        TextView email;
        TextView date;
        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_meeting);
            hour = itemView.findViewById(R.id.hour_meeting);
            location = itemView.findViewById(R.id.location_meeting);
            email = itemView.findViewById(R.id.email_meeting);
            date = itemView.findViewById(R.id.date_meeting);
        }
    }

    public MeetingAdapter(List<Meeting> meetingList) {
        this.mMeetingList = meetingList;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent, false);
        MeetingViewHolder mMeetingViewHolder = new MeetingViewHolder(view);
        return mMeetingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        Meeting mMeeting = mMeetingList.get(position);
        holder.name.setText(mMeeting.getName());
        holder.location.setText(mMeeting.getLocation());
        holder.email.setText(mMeeting.getEmail());

    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

}
