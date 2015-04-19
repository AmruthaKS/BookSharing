package com.example.booksharing1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;




public class NavigationDrawer extends Activity {
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected ActionBarDrawerToggle mDrawerToggle;
    private static boolean isLaunch = true;
    protected CharSequence mDrawerTitle;
    protected CharSequence mTitle;
    protected String[] mPlanetTitles;
    protected static int pos;
    protected FrameLayout frameLayout;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    
        if(isLaunch){
			 /**
			  *Setting this flag false so that next time it will not open our first activity.
			  *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
			  *In this case this base activity will always be call when any child activity will launch.
			  */
			isLaunch = false;
			openActivity(0);
		}
    }
        
        /**
    	 * @param position
    	 *
    	 * Launching activity when any list item is clicked.
    	 */
    	protected void openActivity (int position) {
    		
    		/**
    		 * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
    		 * So whenever any activity is going to launch this BaseActivity is also going to be called and
    		 * it will reset this value because of initialization in onCreate method.
    		 * So that we are setting this in child activity.   
    		 */
//    		mDrawerList.setItemChecked(position, true);
//    		setTitle(listArray[position]);
    		mDrawerLayout.closeDrawer(mDrawerList);
    		NavigationDrawer.pos = position; //Setting currently selected position in this field so that it will be available in our child activities.
    		
    		switch (position) {
    		case 0:
    			startActivity(new Intent(this, MyBooks.class));
    			this.overridePendingTransition(android.R.anim.slide_in_left,
    	                android.R.anim.slide_out_right);
    			break;
    		case 1:
    			startActivity(new Intent(this, Profile.class));
    			this.overridePendingTransition(android.R.anim.slide_in_left,
    	                android.R.anim.slide_out_right);
    			break;
    		case 2:
    			startActivity(new Intent(this, Books.class));
    			this.overridePendingTransition(android.R.anim.slide_in_left,
    	                android.R.anim.slide_out_right);
    			break;
    		case 3:
    			startActivity(new Intent(this, BorrowedBooks.class));
    			this.overridePendingTransition(android.R.anim.slide_in_left,
    	                android.R.anim.slide_out_right);
    			break;
    		case 4:
    			startActivity(new Intent(this, Favourites.class));
    			break;

    		default:
    			break;
    		}
    		
    		//Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
    	}
       

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
      //  menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
      
        return super.onOptionsItemSelected(item);
        
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        
    	//Intent i = new Intent(NavigationDrawer.this, MainActivity.class);
        //startActivity(i); 
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        pos = position;
        
       // Toast.makeText(this, "selected position is"+position, Toast.LENGTH_LONG).show();
        openActivity(position);
      
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

   
}