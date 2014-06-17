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
import android.view.View.OnClickListener;
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
import com.example.myanmarnews.RSS.LoadRSSFeedItems;
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
	public static ListView listNews;
	private PullToRefreshLayout mPullToRefreshLayout;
	ViewGroup viewGroup;
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


	public ListViewNewsLiveFragment() {
	}

	@Override        

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_news_layout, container,
				false);

		listNews = (ListView) rootView.findViewById(R.id.listNews);
		MainActivity.FirstOpen = true;
		// gridNews = (GridView)rootView.findViewById(R.id.gridNews);
		// get fragment data
		// Fragment fragment = getActivity();

		
		/**
		 * Calling a backgroung thread will loads recent articles of a website
		 * 
		 * @param rss
		 *            url of website
		 * */
		new LoadRSSFeedItems(listNews,mPullToRefreshLayout).execute();

		// selecting single ListView item
		// ListView lv = getListView();
		// Launching new screen on Selecting Single ListItem
		listNews.setOnItemClickListener(BasicFunctions.createOnItemClickListener());
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
				new LoadRSSFeedItems(listNews,mPullToRefreshLayout).execute();
			}
		};
		// We can now setup the PullToRefreshLayout
		BasicFunctions.IniPullToRefresh(getActivity(), (ViewGroup) view,
				(View) listNews, timerTask, mPullToRefreshLayout);
	}





}
