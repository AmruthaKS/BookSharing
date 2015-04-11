package com.example.booksharing1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MyBooks extends NavigationDrawer {
	ListView list;
	  String[] bookname = {
	    "Book1",
	      "Book2",
	      "Book3",
	      "Book4",
	      "Book5",
	      "Book6",
	      "Book7"
	  } ;
	  String[] author = {
			  "Author1",
		      "Author2",
		      "Author3",
		      "Author4",
		      "Author5",
		      "Author6",
		      "Author7"	  
	  };
	  
	  String[] genre = {
			  "Genre1",
		      "Genre2",
		      "Genre3",
		      "Genre4",
		      "Genre5",
		      "Genre6",
		      "Genre7"	  
	  };
	  
	  Integer[] imageId = {
	      R.drawable.earth,
	      R.drawable.mercury,
	      R.drawable.jupiter,
	      R.drawable.mars,
	      R.drawable.neptune,
	      R.drawable.saturn,
	      R.drawable.uranus
	  };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		//setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		
		getLayoutInflater().inflate(R.layout.my_books, frameLayout);
		
		mDrawerList.setItemChecked(pos, true);
		setTitle(mPlanetTitles[pos]);
	
		CustomList adapter = new CustomList(MyBooks.this, bookname,author,genre, imageId);
		list=(ListView)findViewById(R.id.mybook_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(MyBooks.this, "You Clicked at " +bookname[+ position], Toast.LENGTH_SHORT).show();
                }

                public void onItemClick1(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3) {
                    // TODO Auto-generated method stub

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
