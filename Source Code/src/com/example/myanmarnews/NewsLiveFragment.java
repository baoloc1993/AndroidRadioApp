package com.example.myanmarnews;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class NewsLiveFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    

    public NewsLiveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_live_layout, container, false);
        ImageView avatar = (ImageView) rootView.findViewById(R.id.image_single_news);
        
        TextView titleNews = (TextView) rootView.findViewById(R.id.title_single_news);
        TextView contentNews = (TextView) rootView.findViewById(R.id.content_single_news);
        TextView dateNews = (TextView) rootView.findViewById(R.id.date_time_single_news);
        //titleNews.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        contentNews.setText("CONTENT");
        dateNews.setText("DATE");
        avatar.setImageResource(R.drawable.ic_launcher);
        return rootView;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
//    }
}