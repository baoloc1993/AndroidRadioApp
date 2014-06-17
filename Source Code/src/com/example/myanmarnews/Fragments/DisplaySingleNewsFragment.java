package com.example.myanmarnews.Fragments;

import com.example.myanmarnews.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DisplaySingleNewsFragment extends Fragment {
	
	public static String KEY_SITE_LINK = "";
	public DisplaySingleNewsFragment(){
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.preview_single_news_web_view, container,
				false);
		
		//Get SITE_LINK sent from other fragment
		Bundle args = this.getArguments();
		String link = args.getString(KEY_SITE_LINK);
		Log.d("DEBUG", "LINK = " + link);
		WebView webView = (WebView) rootView.findViewById(R.id.single_web_view);
		 
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setGeolocationEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDomStorageEnabled(true);

		 
		webView.setWebChromeClient(new WebChromeClient() {
		});
		
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(link);
		 
		return rootView;
	}
	
	
}
