package com.example.rsams4190.seatgeekapitest;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private CurrentEvents[] mDataSource;

    public EventAdapter(Context context, CurrentEvents[] items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //1
    @Override
    public int getCount() {
        return mDataSource.length;
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource[position];
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_event, parent, false);

        // Get title element
        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.event_list_title);

        // Get subtitle element
        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.event_list_subtitle);

        // Get subtitle2 element
        TextView subtitle2TextView =
                (TextView) rowView.findViewById(R.id.event_list_subtitle2);

        // Get detail element
        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.event_list_detail);

        // Get thumbnail element
        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.event_list_thumbnail);

        // 1
        CurrentEvents currentevent = (CurrentEvents) getItem(position);

        // 2
        titleTextView.setText(currentevent.getTitle());
        subtitleTextView.setText(currentevent.getAddress());
        subtitle2TextView.setText(currentevent.getExtended_address());
        detailTextView.setText(currentevent.getType());


        // 3
        Log.d("Test",currentevent.getImage());
        //Picasso.with(mContext).load("https://chairnerd.global.ssl.fastly.net/images/performers-landscape/boris-b7d3a8/10411/huge.jpg").placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        Picasso.with(mContext).load(currentevent.getImage()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        //Picasso.with(mContext).load(String.valueOf(currentevent.getPerformers())).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return rowView;
    }





}
