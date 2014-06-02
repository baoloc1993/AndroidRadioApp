package com.example.myanmarnews;

import com.example.myanmarnews.Fragments.BreakingNewsFragment;
import com.example.myanmarnews.Fragments.NavigationDrawerFragment;
import com.example.myanmarnews.Fragments.NewsLiveFragment;
import com.example.myanmarnews.Fragments.SelectedNewsFragment;
import com.example.myanmarnews.Fragments.SocialNetworkFragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    /**
     * Screen's Size
     */
    public static int screenHeight;
    public static int screenWidth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Get attribute of screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;			//Height of screen
        screenWidth = displayMetrics.widthPixels;			//Width of screen
        
        //Set up Navigation Drawer Fragment
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
        mTitle = getTitle();

        
    }

    //Action when select each child of DrawerList
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        switch (position) {
        
        //New live fragment
        case 0:
        	fragmentManager.beginTransaction()
            .replace(R.id.container, new NewsLiveFragment())
            .commit();
        	break;
        	
        //Breaking new fragment
        case 1:
        	fragmentManager.beginTransaction()
            .replace(R.id.container, new BreakingNewsFragment())
            .commit();
        	break;
        	
        //Selected News Fragment	
        case 2:
        	fragmentManager.beginTransaction()
            .replace(R.id.container, new SelectedNewsFragment())
            .commit();
            break;
        //Social NetWork Fragment
        case 3:
        	fragmentManager.beginTransaction()
            .replace(R.id.container, new SocialNetworkFragment())
            .commit();
            break;
        default :
        	fragmentManager.beginTransaction()
            .replace(R.id.container, new SocialNetworkFragment())
            .commit();
        	break;
        }
        
        
    }

    //Set title for each fragment which is selected
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
//        ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    

}
