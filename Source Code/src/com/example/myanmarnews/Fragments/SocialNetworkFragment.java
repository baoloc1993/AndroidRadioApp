package com.example.myanmarnews.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.myanmarnews.MainActivity;
import com.example.myanmarnews.R;
import com.example.myanmarnews.Adapters.ListNewsItemAdapter;
import com.example.myanmarnews.Adapters.SocialNetworkItemAdapter;
import com.example.myanmarnews.BasicFunctions.BasicFunctions;
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
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class SocialNetworkFragment extends Fragment {
	static Bundle savedInstanceState;
	private static int position = 0;
	GridView socialNetworkHeader;
	ArrayList<SocialNetworkItem> socialNetworkItems;
	WebView webView;
	public SocialNetworkFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.savedInstanceState = savedInstanceState;
		View rootView = inflater.inflate(R.layout.social_network_layout,
				container, false);

		/**
		 * Webview to show webpage
		 * 
		 */
		 webView = (WebView) rootView.findViewById(R.id.webView);
		webView.setPadding(0, 0, 0, 0);
		webView.setWebChromeClient(new MyWebViewClient());
		webView.setWebViewClient(new WebViewClient());

		webView.getSettings().setJavaScriptEnabled(true);
		// OR, you can also load from an HTML string:

		/**
		 * Social Network headers
		 */
		socialNetworkItems = new ArrayList<SocialNetworkItem>();
		socialNetworkItems.add(new SocialNetworkItem("http://facebook.com",
				R.drawable.facebook_icon, Color.rgb(70, 110, 169)));
		socialNetworkItems.add(new SocialNetworkItem(
				"http://plus.google.com/app/basic",
				R.drawable.google_plus_icon, Color.rgb(228, 96, 68)));
		socialNetworkItems.add(new SocialNetworkItem("http://twitter.com",
				R.drawable.twitter_icon, Color.rgb(0, 172, 237)));
		final int columnWidth = (int) (MainActivity.getScreenWidth() / socialNetworkItems
				.size());

		/**
		 * Show social network header in gridview
		 */
		socialNetworkHeader = (GridView) rootView
				.findViewById(R.id.socialNetworkHeader);

		socialNetworkHeader.setPadding(0, 0, 0, 0);
		socialNetworkHeader.setNumColumns(socialNetworkItems.size());

		socialNetworkHeader.setColumnWidth(columnWidth);
		socialNetworkHeader.setAdapter(new SocialNetworkItemAdapter(
				getActivity(), R.layout.single_social_network_header,
				socialNetworkItems, getActivity().getWindowManager()
						.getDefaultDisplay().getRotation()));

		socialNetworkHeader.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				SocialNetworkFragment.position = position;
				String url = socialNetworkItems.get(position).getUrl();
				webView.loadUrl(url);

			}
		});
		socialNetworkHeader.performItemClick(socialNetworkHeader, 0,
				socialNetworkHeader.getItemIdAtPosition(0));
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
		if (socialNetworkHeader != null) {
			for(int i=0;i<socialNetworkItems.size();i++){
				
				View v = socialNetworkHeader.getChildAt(i);
				ProgressBar progressBar = (ProgressBar) v
						.findViewById(R.id.progressBar);
				if(i!=position){ //Item not selected
					progressBar.setVisibility(v.INVISIBLE);
				}else{
					progressBar.setVisibility(v.VISIBLE);
					progressBar.setProgress(progress);
				}
				
				
			}
			
		}

	}

}
