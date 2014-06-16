package com.example.myanmarnews.Fragments;

import imageLoader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.myanmarnews.Constant;
import com.example.myanmarnews.MainActivity;
import com.example.myanmarnews.R;
import com.example.myanmarnews.RSS.RSSDatabaseHandler;
import com.example.myanmarnews.RSS.RSSItem;
import com.example.myanmarnews.RSS.WebSite;








import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
//import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayFullNewsFragment extends Fragment {
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	PagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	//private Fragment fragmentManager;
	//private ArrayList<RSSItem> rssItems = new ArrayList<RSSItem>();
	
	public static String ARG_ID = "";
	public static String ARG_TITLE = "";
	public static final String ARG_SIZE = null;
	
	private static int size = 0;
	private static int id = 0;
	
	
	private static com.example.myanmarnews.MainActivity myContext;
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	//private ArrayList<RSSItem> rssItems = (ArrayList<RSSItem>) MainActivity.rssItems;

	
	
	public DisplayFullNewsFragment(){
		
		super();
		//Log.d("DisplayFillNewsFragment", "get item");
	}
	
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
			//Log.d("DisplayFillNewsFragment", "get item");

	        View rootView = inflater.inflate(R.layout.swipe_view_layout, container, false);
	        //android.support.v4.app.FragmentManager fragmentManager = getActivity().get;
	        
	     // Create the adapter that will return a fragment for each of the three
			// primary sections of the activity.
	        Bundle bundle = this.getArguments();
	        id = bundle.getInt(ARG_ID);
	        size = bundle.getInt(ARG_SIZE);
	        //Log.d("Value of ID", String.valueOf(size));
	        
	        mSectionsPagerAdapter = new SectionsPagerAdapter(myContext.getSupportFragmentManager());

	        //int position = etIntExtra("POSITION_KEY", 0);
	       
			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
			// mSectionsPagerAdapter.instantiateItem(mViewPager, id);
			//mSectionsPagerAdapter.setPrimaryItem(mViewPager, id, bundle);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			

		    
	      //  Fragment currentFragment =  fragmentManager.findFragmentByTag(getTag());
	        //fragmentManager.beginTransaction().add(DisplayFullNewsFragment,"Current");
	       // fragmentManager.beginTransaction().replace(R.id.container,currentFragment).commit();
			//MainActivity.currentFragment = Constant.ListBrakingNews;
			return rootView;
	        
	 }
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

		/**
		 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
		 * one of the sections/tabs/pages.
		 */
		public class SectionsPagerAdapter extends FragmentPagerAdapter {

			public SectionsPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
				super(fragmentManager);
			}

			@Override
			public android.support.v4.app.Fragment getItem(int position) {
				// getItem is called to instantiate the fragment for the given page.
					// below).
				// Return a PlaceholderFragment (defined as a static inner class
				return PlaceholderFragment.newInstance(position+1);
			}

			@Override
			public int getCount() {
				// Show 3 total pages.
				//RSSDatabaseHandler rssDb = new RSSDatabaseHandler(myContext);
				
				return size;
			}

//			@Override
//			public CharSequence getPageTitle(int position) {
//				Locale l = Locale.getDefault();
//				switch (position) {
//				case 0:
//					return getString(R.string.title_section1).toUpperCase(l);
//				case 1:
//					return getString(R.string.title_section2).toUpperCase(l);
//				case 2:
//					return getString(R.string.title_section3).toUpperCase(l);
//				}
//				return null;
//			}
		}

		/**
		 * A placeholder fragment containing a simple view.
		 */
		public static class PlaceholderFragment extends android.support.v4.app.Fragment {

			/**
			 * Returns a new instance of this fragment for the given section number.
			 */
			public static PlaceholderFragment newInstance(int sectionNumber) {
				PlaceholderFragment fragment = new PlaceholderFragment();
				Bundle args = new Bundle();
				//Get data from database
		        RSSDatabaseHandler rssDb = new RSSDatabaseHandler(DisplayFullNewsFragment.myContext);
		        WebSite website = rssDb.getSiteById(sectionNumber);
		        //Log.d("value of sectionNumber in placeholder fragment new instance", String.valueOf(sectionNumber));
				args.putInt(ARG_ID, sectionNumber);
				fragment.setArguments(args);
				return fragment;
			}

			public PlaceholderFragment() {
			}

			private int id;
			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
				View rootView = inflater.inflate(R.layout.display_web_page, container,
						false);
		    	//Get Data from previous fragment
	    		Bundle bundle = this.getArguments();
		        int position = bundle.getInt(ARG_ID);
		       // size = bundle.getInt(ARG_SIZE);
		        Log.d("value of position in placeholder fragment", String.valueOf(position));
		        Log.d("value of ID in placeholder fragment", String.valueOf(DisplayFullNewsFragment.id+position-1));
		        Log.d("value of SIZE in placeholder fragment", String.valueOf(DisplayFullNewsFragment.size));
		        
		        //Get data from database
		        RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getActivity());
		        List<WebSite> websites = rssDb.getAllSites();
		        Log.d("value of SIZE OF DATABASE", String.valueOf(websites.size()));
		        WebSite website = rssDb.getSiteById(DisplayFullNewsFragment.id+position-1);
	    		
    			RSSItem item = new RSSItem(
    					website.getId(),
    					website.getTitle(),
    					website.getLink(),
    					website.getDescription(),
    					website.getPubDate(),
    					 website.getImageLink());
    			
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
				return rootView;
			}
		}
		
		@Override
		public void onAttach(Activity activity) {
		    myContext=(MainActivity) activity;
		    super.onAttach(activity);
		}

}