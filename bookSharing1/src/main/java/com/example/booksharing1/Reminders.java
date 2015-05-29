package com.example.booksharing1;


import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class Reminders extends NavigationDrawer{
    Context c;
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(R.layout.activity_main1);
        super.onCreate(savedInstanceState);
        mDrawerList.setItemChecked(pos, true);
        setTitle(mPlanetTitles[pos]);
        getLayoutInflater().inflate(R.layout.reminders, frameLayout);
        c = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
