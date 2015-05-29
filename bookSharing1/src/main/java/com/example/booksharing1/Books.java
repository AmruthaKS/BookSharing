package com.example.booksharing1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.booksharing1.JSON.OwnedBook;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)


public class Books extends NavigationDrawer {
    ListView list;
    ProgressBarHandler progressBarHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//setContentView(R.layout.activity_main1);
		super.onCreate(savedInstanceState);
		mDrawerList.setItemChecked(pos, true);
		setTitle(mPlanetTitles[pos]);
		getLayoutInflater().inflate(R.layout.books, frameLayout);
		progressBarHandler = new ProgressBarHandler(this);
        progressBarHandler.show();
		new HttpRequestTask().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class HttpRequestTask extends AsyncTask<Void, Void, List<OwnedBook>> {
		
		
		@Override
		protected List<OwnedBook> doInBackground(Void... params) {

            String url = "users/"+UserInfo.getInstance().getId()+"/books?filter=owned";
            List<OwnedBook> book = new HttpRestClient().getOwnedBooks(url);
            return book;
			
		}
		
		@Override
        protected void onPostExecute(final List<OwnedBook> book) {
            progressBarHandler.hide();
            String[] myStringArray = new String[book.size()];
            CustomBookList adapter = new CustomBookList(Books.this, book,myStringArray);
            list=(ListView)findViewById(R.id.book_list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(Books.this, "You Clicked at " + book.get(position).getName(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Books.this, SingleBook.class);
                    i.putExtra("bookObj",book.get(position));
                    startActivity(i);
                }

                public void onItemClick1(AdapterView<?> arg0, View arg1,
                                         int arg2, long arg3) {
                    // TODO Auto-generated method stub

                }
            });

        }
		
	}
}