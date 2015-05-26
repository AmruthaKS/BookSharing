package com.example.booksharing1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Set;


public class Favourites extends NavigationDrawer {
    Set<String> favourites;
    Context c;
    ListView listView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//setContentView(R.layout.activity_main1);
		super.onCreate(savedInstanceState);
		mDrawerList.setItemChecked(pos, true);
		setTitle(mPlanetTitles[pos]);
		getLayoutInflater().inflate(R.layout.favourites, frameLayout);
        c = this;
        // Get the favourites from the UserInfo class
        favourites  = UserInfo.getInstance().getFavorites();
        String[] f = favourites.toArray(new String[favourites.size()]);

        if (! favourites.isEmpty() ) {
            // show the list which are the user favourites
            listView = (ListView) findViewById(R.id.list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_list_item_fav, R.id.textFav, f);
            listView.setAdapter(adapter);

        }

        // Button to add new favourites
        Button addFavorites = (Button) findViewById(R.id.AddFavButton);


        addFavorites.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(c, AddFavourites.class);
                intent.putExtra("caller","favourites");
                c.startActivity(intent);
            }
        });
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}