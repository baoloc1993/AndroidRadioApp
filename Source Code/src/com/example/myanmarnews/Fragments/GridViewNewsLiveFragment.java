package com.example.myanmarnews.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myanmarnews.MainActivity;
import com.example.myanmarnews.R;
import com.example.myanmarnews.Adapters.GridNewsItemAdapter;
import com.example.myanmarnews.Adapters.ListNewsItemAdapter;
import com.example.myanmarnews.Items.NewsItem;
import com.example.myanmarnews.RSS.RSSDatabaseHandler;
import com.example.myanmarnews.RSS.RSSFeed;
import com.example.myanmarnews.RSS.RSSItem;
import com.example.myanmarnews.RSS.RSSParser;
import com.example.myanmarnews.RSS.WebSite;

public class GridViewNewsLiveFragment extends Fragment {
	public static GridView gridNews;
	

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	private ProgressDialog pDialog;
	 
    // Array list for list view
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String,String>>();
 
    RSSParser rssParser = new RSSParser();
     
    List<RSSItem> rssItems = MainActivity.rssItems;
 //
    RSSFeed rssFeed;
     
//    private static String TAG_TITLE = "title";
//    private static String TAG_LINK = "link";
//    private static String TAG_DESRIPTION = "description";
//    private static String TAG_PUB_DATE = "pubDate";
//    private static String TAG_GUID = "guid"; // not used
//    private static String TAG_IMAGE = "image";

	public GridViewNewsLiveFragment() {
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_news_layout, container, false);
        
        gridNews = (GridView)rootView.findViewById(R.id.gridNews);
        
     // get fragment data
       // Fragment fragment = getActivity();
         
        // SQLite Row id
       // Integer site_id = Integer.parseInt(getActivity().getStringExtra("id"));
         
        // Getting Single website from SQLite
       // RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getActivity());
         
 
        
        
        /**
         * Calling a backgroung thread will loads recent articles of a website
         * @param rss url of website
         * */
        new loadRSSFeedItems().execute();
         
        // selecting single ListView item
        //ListView lv = getListView();
  
        // Launching new screen on Selecting Single ListItem
        gridNews.setOnItemClickListener(new OnItemClickListener() {
  
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
               // Intent in = new Intent(getApplicationContext(), DisPlayWebPageActivity.class);
                 
                // getting page url
                //String page_url = ((TextView) view.findViewById(R.id.rss_url)).getText().toString();
               // T//oast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
               // in.putExtra("page_url", page_url);
               // startActivity(in);
            }
        });
        
      //  listNews.setAdapter(new ListNewsItemAdapter(rootView.getContext(), R.layout.preview_single_news_layout, newsItems));
        return rootView;
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
             
            // list of rss items
        	
        	if (isConnectingToInternet()){
        		rssItems = rssParser.getRSSFeedItems(getString(R.string.rss_link));
        	}
             

             
            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
            	//InputStream input = null;
            	RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getActivity());
                public void run() {
                    /**
                     * Updating parsed items into listview
                     * */
                	
                	//NO INTERNET -> RSSITEMS is emtpy
                	if (rssItems.isEmpty()){
                		//Get All Website from database
                		List<WebSite> websites = rssDb.getAllSites();
                		for (WebSite website : websites){
                			RSSItem newItem = new RSSItem(
                					website.getId(),
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
					GridNewsItemAdapter adapter = new GridNewsItemAdapter(
							getActivity(), 
							R.layout.preview_single_news_grid_layout, 
							(ArrayList<RSSItem>) rssItems);
                   gridNews.setAdapter(adapter);
                   //MainActivity.rssItems = rssItems;
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