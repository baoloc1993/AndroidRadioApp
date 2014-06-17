package com.example.myanmarnews.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
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
import com.example.myanmarnews.BasicFunctions.BasicFunctions;
import com.example.myanmarnews.Items.NewsItem;
import com.example.myanmarnews.RSS.LoadRSSFeedItems;
import com.example.myanmarnews.RSS.RSSDatabaseHandler;
import com.example.myanmarnews.RSS.RSSFeed;
import com.example.myanmarnews.RSS.RSSItem;
import com.example.myanmarnews.RSS.RSSParser;
import com.example.myanmarnews.RSS.WebSite;
import com.example.myanmarnews.libs.actionbarpulltorefresh.library.PullToRefreshLayout;

public class GridViewNewsLiveFragment extends Fragment {
	public static GridView gridNews;
	private boolean FirstOpen = true;
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


	private PullToRefreshLayout mPullToRefreshLayout;
     
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
        new LoadRSSFeedItems(gridNews,mPullToRefreshLayout).execute();
         
        // selecting single ListView item
        //ListView lv = getListView();
  
        // Launching new screen on Selecting Single ListItem
        gridNews.setOnItemClickListener(BasicFunctions.createOnItemClickListener());
        
      //  listNews.setAdapter(new ListNewsItemAdapter(rootView.getContext(), R.layout.preview_single_news_layout, newsItems));
        return rootView;
    }
	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	
    	gridNews = (GridView)view.findViewById(R.id.gridNews);
    	
    	

		// We need to create a PullToRefreshLayout manually
		
		
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new LoadRSSFeedItems(gridNews,mPullToRefreshLayout).execute();
			}
		};
		mPullToRefreshLayout = new PullToRefreshLayout(view.getContext());
		// We can now setup the PullToRefreshLayout
		BasicFunctions.IniPullToRefresh(getActivity(), (ViewGroup) view, (View)gridNews, timerTask,mPullToRefreshLayout);
    }
	 
	
}