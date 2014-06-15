package com.example.myanmarnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayWebPageActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.display_web_page);
	    //Variable for layout of activity
	    TextView page_title = (TextView) findViewById(R.id.page_title);
	    TextView page_public_date = (TextView) findViewById(R.id.page_public_date);
	    ImageView page_image = (ImageView) findViewById(R.id.page_image);
	    TextView page_content = (TextView) findViewById(R.id.page_content);
	    Button go_to_website = (Button) findViewById(R.id.go_to_website_button);
	    String page_image_link;
	    
	    //Get information from fragment
	    Intent i = getIntent();
	    
	    Log.d("DEBUG", i.getStringExtra("page_img_link"));
	    page_title.setText(i.getStringExtra("page_title"));
	    page_public_date.setText(i.getStringExtra("page_public_date"));
	    page_image_link = i.getStringExtra("page_img_link");
	    //page_title.setText("aaaa");
	    
	    
	    
	    // TODO Auto-generated method stub
	}

}
