package com.example.myanmarnews.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.myanmarnews.MainActivity;
import com.example.myanmarnews.R;
import com.example.myanmarnews.Adapters.ListNewsItemAdapter;
import com.example.myanmarnews.Adapters.SocialNetworkItemAdapter;
import com.example.myanmarnews.Items.NewsItem;
import com.example.myanmarnews.Items.SocialNetworkItem;
import com.example.myanmarnews.RSS.*;
import com.example.myanmarnews.R.drawable;
import com.example.myanmarnews.R.id;
import com.example.myanmarnews.R.layout;

import android.R.anim;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;


public class SocialNetworkFragment extends Fragment  {
	ProgressBar progressBar;
	public SocialNetworkFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.social_network_layout, container,
				false);
		/**
		 * 
		 * Progress Bar 
		 */
		progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
		progressBar.setMax(100);
		/**
		 * Webview to show webpage
		 * 
		 */
		final WebView webView = (WebView)rootView.findViewById(R.id.webView);
		webView.setWebChromeClient(new MyWebViewClient());
		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		// OR, you can also load from an HTML string:
		 String defaultContent = "<html><body>Press icons above to go to our social network</body></html>";
		 webView.loadData(defaultContent, "text/html", null);
		
		/**
		 * Social Network headers
		 */
		final ArrayList<SocialNetworkItem> socialNetworkItems = new ArrayList<SocialNetworkItem>();
		socialNetworkItems.add(new SocialNetworkItem("http://facebook.com",R.drawable.ic_launcher));
		socialNetworkItems.add(new SocialNetworkItem("http://plus.google.com/app/basic",R.drawable.ic_launcher));
		socialNetworkItems.add(new SocialNetworkItem("http://twitter.com",R.drawable.ic_launcher));
		/**
		 * Show social network header in gridview
		 */
		GridView socialNetworkHeader = (GridView)rootView.findViewById(R.id.socialNetworkHeader);
		int columnWidth = (int)(MainActivity.screenWidth/socialNetworkItems.size());
		socialNetworkHeader.setColumnWidth(columnWidth);
		socialNetworkHeader.setAdapter(new SocialNetworkItemAdapter(getActivity(),
				R.layout.single_social_network_header,
				socialNetworkItems,
				getActivity().getResources().getConfiguration().orientation));
		socialNetworkHeader.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				progressBar.setVisibility(View.VISIBLE);
				String url = socialNetworkItems.get(position).getUrl();
				webView.loadUrl(url);
				
				SocialNetworkFragment.this.progressBar.setProgress(0);
			}
		});
		return rootView;
	}
	private class MyWebViewClient extends WebChromeClient { 
        @Override
        public void onProgressChanged(WebView view, int newProgress) {          
            SocialNetworkFragment.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
 
    public void setValue(int progress) {
        progressBar.setProgress(progress);
        if(progress == progressBar.getMax()){
        	progressBar.setVisibility(View.INVISIBLE);
        }
    }
	
		
}

