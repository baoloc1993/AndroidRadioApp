package com.example.myanmarnews.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class ListViewNewsLiveFragment extends Fragment {
	private ListView listNews;
	

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	private ProgressDialog pDialog;
	 
    // Array list for list view
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String,String>>();
 
    RSSParser rssParser = new RSSParser();
     
    List<RSSItem> rssItems = new ArrayList<RSSItem>();
 
    RSSFeed rssFeed;
     
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_PUB_DATE = "pubDate";
    private static String TAG_GUID = "guid"; // not used
    private static String TAG_IMAGE = "image";

	public ListViewNewsLiveFragment() {
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_news_layout, container, false);
        
        listNews = (ListView)rootView.findViewById(R.id.listNews);
     // get fragment data
       // Fragment fragment = getActivity();
         
        // SQLite Row id
       // Integer site_id = Integer.parseInt(getActivity().getStringExtra("id"));
         
        // Getting Single website from SQLite
        RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getActivity());
         
         
       // WebSite site = rssDB.getSite(site_id);
        //Create new website object
        WebSite site = new WebSite("TITLE WEB", "LINK WEB", "RSS_LINK", "DESCRIPTION");
        String rss_link = site.getRSSLink();
        
//        ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
//        for(int i=0;i<10;i++){
//	        newsItems.add(new NewsItem(
//	        		"Title " + String.valueOf(newsItems.size()+1),
//	        		R.drawable.ic_launcher,
//	        		"a asldkasd asdlkjasldj asldkjasjd aslkdalkjsd qwqw 1212 3lkas asldka1oijd Content " + String.valueOf(newsItems.size()+1),
//					"Time Stamp "+ String.valueOf(newsItems.size()+1)
//	        ));
//        }
        
        
        /**
         * Calling a backgroung thread will loads recent articles of a website
         * @param rss url of website
         * */
        new loadRSSFeedItems().execute(rss_link);
         
        // selecting single ListView item
        //ListView lv = getListView();
  
        // Launching new screen on Selecting Single ListItem
        listNews.setOnItemClickListener(new OnItemClickListener() {
  
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
               // Intent in = new Intent(getApplicationContext(), DisPlayWebPageActivity.class);
                 
                // getting page url
                String page_url = ((TextView) view.findViewById(R.id.rss_url)).getText().toString();
                Toast.makeText(getActivity(), page_url, Toast.LENGTH_SHORT).show();
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
            String rss_url = args[0];
             
            // list of rss items
            rssItems = rssParser.getRSSFeedItems(getString(R.string.rss_link));
             
            // looping through each item
            for(RSSItem item : rssItems){
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                 
                // adding each child node to HashMap key => value
                map.put(TAG_TITLE, item.getTitle());
                map.put(TAG_LINK, item.getLink());
                map.put(TAG_PUB_DATE, item.getPubdate());
                String description = item.getDescription();
               // String url_img = "";
               
                // taking only 200 chars from description
                
                if(description.length() > 100){
                    description = description.substring(0, 97) + "..";
                }
                map.put(TAG_DESRIPTION, description);
                
 
                // adding HashList to ArrayList
                rssItemList.add(map);
            }
             
            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
//                    /**
//                     * Updating parsed items into listview
//                     * */
//                	RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getActivity());
//                	WebSite site = new WebSite(
//							"title", "link","rss_link","description" );
//					WebSite site2 = new WebSite(
//							"title2", "link2","rss_link2","description2" );
//					// listing all websites from SQLite
//					
//					rssDb.addSite(site);
//					rssDb.addSite(site2);
//                    ListAdapter adapter = new SimpleAdapter(
//                           getActivity(),
//                            rssItemList, R.layout.preview_single_news_layout,
//                            new String[] { TAG_LINK, TAG_TITLE, TAG_PUB_DATE, TAG_DESRIPTION, TAG_IMAGE},
//                            new int[] {
//									R.id.sqlite_id, R.id.title, R.id.rss_url, R.id.content , R.id.icon});
//                     
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
	// @Override
	// public void onAttach(Activity activity) {
	// super.onAttach(activity);
	// ((MainActivity) activity).onSectionAttached(
	// getArguments().getInt(ARG_SECTION_NUMBER));
	// }
	
}