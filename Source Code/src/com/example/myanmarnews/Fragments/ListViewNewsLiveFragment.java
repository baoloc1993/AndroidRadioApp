package com.example.myanmarnews.Fragments;

import com.example.myanmarnews.libs.actionbarpulltorefresh.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListView;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myanmarnews.MainActivity;
import com.example.myanmarnews.R;
import com.example.myanmarnews.Adapters.ListNewsItemAdapter;
import com.example.myanmarnews.BasicFunctions.BasicFunctions;
import com.example.myanmarnews.RSS.RSSDatabaseHandler;
import com.example.myanmarnews.RSS.RSSFeed;
import com.example.myanmarnews.RSS.RSSItem;
import com.example.myanmarnews.RSS.RSSParser;
import com.example.myanmarnews.RSS.WebSite;
import com.example.myanmarnews.libs.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import com.example.myanmarnews.libs.actionbarpulltorefresh.library.Options;
import com.example.myanmarnews.libs.actionbarpulltorefresh.library.PullToRefreshLayout;
import com.example.myanmarnews.libs.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class ListViewNewsLiveFragment extends Fragment {
	private ListView listNews;
	private PullToRefreshLayout mPullToRefreshLayout;
	ViewGroup viewGroup;
	private boolean FirstOpen = true;
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	private ProgressDialog pDialog;

	// Array list for list view
	ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String, String>>();

	RSSParser rssParser = new RSSParser();

	// List<RSSItem> rssItems = MainActivity.rssItems;
	List<RSSItem> rssItems = new ArrayList<RSSItem>();

	RSSFeed rssFeed;

	// private static String TAG_TITLE = "title";
	// private static String TAG_LINK = "link";
	// private static String TAG_DESRIPTION = "description";
	// private static String TAG_PUB_DATE = "pubDate";
	// private static String TAG_GUID = "guid"; // not used
	// private static String TAG_IMAGE = "image";

	byte[] img;

	private GridView gridNews;

	public ListViewNewsLiveFragment() {
	}

	@Override

                    // updating listview

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_news_layout, container,
				false);

		listNews = (ListView) rootView.findViewById(R.id.listNews);
		// gridNews = (GridView)rootView.findViewById(R.id.gridNews);
		// get fragment data
		// Fragment fragment = getActivity();

		// SQLite Row id
		// Integer site_id =
		// Integer.parseInt(getActivity().getStringExtra("id"));

		// Getting Single website from SQLite
		// RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getActivity());

		// WebSite site = rssDB.getSite(site_id);
		// Create new website object
		// WebSite site = new WebSite("TITLE WEB", "LINK WEB", "RSS_LINK",
		// "DESCRIPTION");
		// String rss_link = site.getRSSLink();

		/**
		 * Calling a backgroung thread will loads recent articles of a website
		 * 
		 * @param rss
		 *            url of website
		 * */
		new loadRSSFeedItems().execute();

		// selecting single ListView item
		// ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		listNews.setOnItemClickListener(new OnItemClickListener() {
			  
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
//              
            	//Get Item of current position in database
            	RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getActivity());
            	RSSItem rss_item = (RSSItem) listNews.getItemAtPosition(position);
            	WebSite website = rssDb.getSiteByLink(rss_item.getLink());
            	List<WebSite> websites = rssDb.getAllSites();
            	
            	
            	//transfer link of current Item to other fragment
                Bundle args = new Bundle();
                args.putInt(DisplayFullNewsFragment.ARG_ID, website.getId());
                args.putInt(DisplayFullNewsFragment.ARG_SIZE, websites.size());
                //Log.d("SET ON ITEM CLICK LISTENER", String.valueOf(website.getId()));

                android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                DisplayFullNewsFragment displayFullNewsFragment = new DisplayFullNewsFragment();
                displayFullNewsFragment.setArguments(args);
                
                //Go to DisplayFullNewsFragment
    	        fragmentManager.beginTransaction().replace(R.id.container, displayFullNewsFragment).commit();
                //Log.d("SET ON ITEM CLICK LISTENER", String.valueOf(rss_item.getId()));

            }
        });

		// listNews.setAdapter(new ListNewsItemAdapter(rootView.getContext(),
		// R.layout.preview_single_news_layout, newsItems));
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		listNews = (ListView) view.findViewById(R.id.listNews);
		
		// We need to create a PullToRefreshLayout manually
		mPullToRefreshLayout = new PullToRefreshLayout(view.getContext());
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new loadRSSFeedItems().execute();
			}
		};
		// We can now setup the PullToRefreshLayout
		BasicFunctions.IniPullToRefresh(getActivity(), (ViewGroup) view,
				(View) listNews, timerTask, mPullToRefreshLayout);
	}

	/**
	 * Background Async Task to get RSS Feed Items data from URL
	 * */
	public class loadRSSFeedItems extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (FirstOpen) {
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage("Loading recent articles...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
				FirstOpen = false;
			} else {
			}

		}

		/**
		 * getting all recent articles and showing them in listview
		 * */
		@Override
		protected String doInBackground(String... args) {
			// rss link url
			// String rss_url = args[0];
			// IF INTERNET CONNECTING, RETRIVE DATA FROM RSS LINK
			// list of rss items
			if (BasicFunctions.isConnectingToInternet(getActivity().getApplicationContext())) {
				rssItems = rssParser
						.getRSSFeedItems(getString(R.string.rss_link));
			}

			// updating UI from Background Thread
			Log.d("DEBUG", "DEBUG");
			getActivity().runOnUiThread(new Runnable() {

				// InputStream input = null;

				public void run() {
					RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
							getActivity());

					/**
					 * Updating parsed items into listview
					 * */
					// rssDb.onCreate(rssDb);

					// NO INTERNET -> RSSITEMS is emtpy
					if (rssItems.isEmpty()) {

						// Get All Website from database
						List<WebSite> websites = rssDb.getAllSites();
						for (WebSite website : websites) {
							RSSItem newItem = new RSSItem(website.getId(),
									website.getTitle(), website.getLink(),
									website.getDescription(), website
											.getPubDate(), website
											.getImageLink());

							// Add RSSItem to RSSItems
							rssItems.add(newItem);

						}

					} else {
						for (RSSItem item : rssItems) {

							// ADD EACH ITEM INTO DATABASE
							WebSite site = new WebSite(item.getTitle(), item
									.getLink(), item.getDescription(), item
									.getPubdate(), item.getImgUrl());
							rssDb.addSite(site);
							// Log.d("LINK",item.getLink());
						}
					}

					// updating listview

					ListAdapter adapter = new ListNewsItemAdapter(
							getActivity(),
							R.layout.preview_single_news_list_layout,
							(ArrayList<RSSItem>) rssItems);

					listNews.setAdapter(adapter);
					// MainActivity.rssItems = rssItems;
				}
			});
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String args) {
			// dismiss the dialog after getting all products
			if (pDialog != null) {
				pDialog.dismiss();
			} else {

			}
			if (mPullToRefreshLayout != null) {
				mPullToRefreshLayout.setRefreshComplete();
			}

		}
	}



}
