package com.example.myanmarnews.Fragments;

import imageLoader.ImageLoader;

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

import com.example.myanmarnews.R;
import com.example.myanmarnews.Adapters.ListNewsItemAdapter;
import com.example.myanmarnews.Items.NewsItem;
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

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	private ProgressDialog pDialog;
	 
    // Array list for list view
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String,String>>();
 
    RSSParser rssParser = new RSSParser();
     
    List<RSSItem> rssItems = new ArrayList<RSSItem>();
 
    RSSFeed rssFeed;
     
//    private static String TAG_TITLE = "title";
//    private static String TAG_LINK = "link";
//    private static String TAG_DESRIPTION = "description";
//    private static String TAG_PUB_DATE = "pubDate";
//    private static String TAG_GUID = "guid"; // not used
//    private static String TAG_IMAGE = "image";
    
    byte[] img;


	private GridView gridNews;

	public ListViewNewsLiveFragment() {
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_news_layout, container, false);
        
        listNews = (ListView)rootView.findViewById(R.id.listNews);
        //gridNews = (GridView)rootView.findViewById(R.id.gridNews);
     // get fragment data
       // Fragment fragment = getActivity();
         
        // SQLite Row id
       // Integer site_id = Integer.parseInt(getActivity().getStringExtra("id"));
         
        // Getting Single website from SQLite
      //  RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getActivity());
         
         
       // WebSite site = rssDB.getSite(site_id);
        //Create new website object
       // WebSite site = new WebSite("TITLE WEB", "LINK WEB", "RSS_LINK", "DESCRIPTION");
       // String rss_link = site.getRSSLink();
        

        
        /**
         * Calling a backgroung thread will loads recent articles of a website
         * @param rss url of website
         * */
        new loadRSSFeedItems().execute();
         
        // selecting single ListView item
        //ListView lv = getListView();
  
        // Launching new screen on Selecting Single ListItem
        listNews.setOnItemClickListener(new OnItemClickListener() {
  
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
               // Intent in = new Intent(getApplicationContext(), DisPlayWebPageActivity.class);
                 
                // getting page url
               // String page_url = ((TextView) view.findViewById(R.id.rss_url)).getText().toString();
                Toast.makeText(getActivity(), "Clicked to item", Toast.LENGTH_SHORT).show();
               // in.putExtra("page_url", page_url);
               // startActivity(in);
            }
        });
        
      //  listNews.setAdapter(new ListNewsItemAdapter(rootView.getContext(), R.layout.preview_single_news_layout, newsItems));
        return rootView;
    }
	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	
    	listNews = (ListView)view.findViewById(R.id.listNews);
    	
    	// This is the View which is created by ListFragment
		ViewGroup viewGroup = (ViewGroup) view;

		// We need to create a PullToRefreshLayout manually
		mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

		// We can now setup the PullToRefreshLayout
		ActionBarPullToRefresh.from(getActivity())
		.insertLayoutInto(viewGroup) // We need to insert the PullToRefreshLayout into the Fragment's ViewGroup
		.theseChildrenArePullable(listNews) // We need to mark the ListView and it's Empty View as pullable This is because they are not direct children of the
		// ViewGroup
		.options(Options.create()
				.refreshingText("Fetching A lot of Stuff...")
				.pullText("Pull me down!")
				.releaseText("Let go of me!!!")
				.titleTextColor(android.R.color.black)
				.progressBarColor(android.R.color.holo_orange_light)
				.headerBackgroundColor(android.R.color.holo_blue_light)
				.progressBarStyle(Options.PROGRESS_BAR_STYLE_INSIDE)
				.build())
		.listener(new OnRefreshListener() { // We can now complete the setup as desired
			@Override
			public void onRefreshStarted(View view) {

				Timer timer = new Timer();
	        	TimerTask task = new TimerTask() {
					@Override
					public void run() {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mPullToRefreshLayout.setRefreshComplete();
							}
						});
					}
				};
	        	timer.schedule(task, 5000);
			}
		})
		.setup(mPullToRefreshLayout);
    }
	
	
	 /**
     * Background Async Task to get RSS Feed Items data from URL
     * */
    class loadRSSFeedItems extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(
                    getActivity());
            pDialog.setMessage("Loading recent articles...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting all recent articles and showing them in listview
         * */
        @Override
        protected String doInBackground(String... args) {
            // rss link url
            //String rss_url = args[0];
             //IF INTERNET CONNECTING, RETRIVE DATA FROM RSS LINK
            // list of rss items
        	if (isConnectingToInternet()){
        		rssItems = rssParser.getRSSFeedItems(getString(R.string.rss_link));
        	}
             

             
            // updating UI from Background Thread
            Log.d("DEBUG","DEBUG");
            getActivity().runOnUiThread(new Runnable() {
            	
            	//InputStream input = null;
            	
            	
                public void run() {
                	RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getActivity());
                	
                    /**
                     * Updating parsed items into listview
                     * */
                	//rssDb.onCreate(rssDb);
                	
                	//NO INTERNET -> RSSITEMS is emtpy
                	if (rssItems.isEmpty()){
                		
                		//Get All Website from database
                		List<WebSite> websites = rssDb.getAllSites();
                		for (WebSite website : websites){
                			RSSItem newItem = new RSSItem(
                					website.getTitle(),
                					website.getLink(),
                					website.getDescription(),
                					website.getPubDate(),
                					 website.getImageLink());
                			
                			//Add RSSItem to RSSItems
                			rssItems.add(newItem);
                		}
                		
                	}else{
	                	for (RSSItem item : rssItems){

	                		//ADD EACH ITEM INTO DATABASE
	                		WebSite site = new WebSite(
	    							item.getTitle(), 
	    							item.getLink(),
	    							item.getDescription(),
	    							item.getPubdate(),
	    							item.getImgUrl());
	                		rssDb.addSite(site);
	                	}
                	}
                	

                    // updating listview
					ListAdapter adapter = new ListNewsItemAdapter(
							getActivity(), 
							R.layout.preview_single_news_list_layout, 
							(ArrayList<RSSItem>) rssItems);
					
                   listNews.setAdapter(adapter);
                }
            });
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String args) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
        }
    }
    
    //CHECK IF IS CONNECTION TO INTERNET OR NOT
    public boolean isConnectingToInternet(){
    	Context _context = getActivity().getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null) 
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
	// @Override
	// public void onAttach(Activity activity) {
	// super.onAttach(activity);
	// ((MainActivity) activity).onSectionAttached(
	// getArguments().getInt(ARG_SECTION_NUMBER));
	// }
    
}
  