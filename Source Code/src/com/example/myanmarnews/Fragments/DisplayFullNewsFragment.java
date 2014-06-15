package com.example.myanmarnews.Fragments;

import imageLoader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import com.example.myanmarnews.Constant;
import com.example.myanmarnews.MainActivity;
import com.example.myanmarnews.R;
import com.example.myanmarnews.RSS.RSSDatabaseHandler;
import com.example.myanmarnews.RSS.RSSItem;
import com.example.myanmarnews.RSS.WebSite;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayFullNewsFragment extends Fragment {
	
	
	//private Fragment fragmentManager;
	//private ArrayList<RSSItem> rssItems = new ArrayList<RSSItem>();
	private String link;
	public static String ARG_LINK = "";
	
	//private ArrayList<RSSItem> rssItems = (ArrayList<RSSItem>) MainActivity.rssItems;
	
	public DisplayFullNewsFragment(){
		super();
	}
	
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.display_web_page, container, false);
	        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
	    	//Get Data from previous fragment
    		Bundle bundle = this.getArguments();
	        link = bundle.getString(ARG_LINK);
	        
	        
	        //Get data from database
	        RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getActivity());
	        WebSite website = rssDb.getSite(link);
    		
    			RSSItem item = new RSSItem(
    					website.getId(),
    					website.getTitle(),
    					website.getLink(),
    					website.getDescription(),
    					website.getPubDate(),
    					 website.getImageLink());
    			
    			//Add RSSItem to RSSItems
    			//rssItems.add(newItem);
    		
    		
    	
	        //Get ID Layout
	        TextView page_title = (TextView) rootView.findViewById(R.id.page_title);
		    TextView page_public_date = (TextView) rootView.findViewById(R.id.page_public_date);
		    ImageView page_image = (ImageView) rootView.findViewById(R.id.page_image);
		    TextView page_content = (TextView) rootView.findViewById(R.id.page_content);
		    Button go_to_website = (Button) rootView.findViewById(R.id.go_to_website_button);
		    
		    //Get information of current News
		    
		   // RSSItem item = MainActivity.rssItems.get(position);
		    //Log.d("POSITION", page_title.toString());
		    //Set Information for text
		    page_title.setText(item.getTitle());
		    page_public_date.setText(item.getPubdate());
		    page_content.setText(item.getDescription());
		    
		    //Set information for ImageView
		   ImageLoader imgLoader = new ImageLoader(getActivity());
			// Loader image - will be shown before loading image
	        int loader = R.drawable.image_not_found;
	         
			imgLoader.DisplayImage(item.getImgUrl(), loader, page_image);
		    //Set Information for button
		    //go_to_website.
		    
	      //  Fragment currentFragment =  fragmentManager.findFragmentByTag(getTag());
	        //fragmentManager.beginTransaction().add(DisplayFullNewsFragment,"Current");
	       // fragmentManager.beginTransaction().replace(R.id.container,currentFragment).commit();
			//MainActivity.currentFragment = Constant.ListBrakingNews;
			return rootView;
	        
	 }
}
