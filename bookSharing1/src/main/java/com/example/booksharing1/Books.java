package com.example.booksharing1;

import org.apache.http.client.HttpClient;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class BooksResponse {
    @JsonProperty("ownedBooks")
    List<Book> book;

    public List<Book> getBooks () {
        return book;
    }

    public void setBooks (List<Book> books) {
        book = books;
    }

}



public class Books extends NavigationDrawer {
    ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//setContentView(R.layout.activity_main1);
		super.onCreate(savedInstanceState);
		mDrawerList.setItemChecked(pos, true);
		setTitle(mPlanetTitles[pos]);
		getLayoutInflater().inflate(R.layout.books, frameLayout);
		
		new HttpRequestTask().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class HttpRequestTask extends AsyncTask<Void, Void, List<Book>> {
		
		
		@Override
		protected List<Book> doInBackground(Void... params) {
			// get an instance of the http client conn object
			// HttpClient client = HttpClientFactory.getThreadSafeClient();
			
		    //for one book
			final String url1 = "http://54.251.185.219:8080/neo4j/v1/books/0159569d-883d-4bff-a3b8-4ad30cc0582d";

            //for multiple books
            final String url = "http://54.251.185.219:8080/neo4j/v1/users/833cfa38-6b27-4eb3-9f6b-8015bf007417/books?filter=owned";

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            List<Book> book = restTemplate.getForObject(url, BooksResponse.class).getBooks();
            return book;
			
		}
		
		@Override
        protected void onPostExecute(final List<Book> book) {


/*
            TextView tv1 = (TextView) findViewById(R.id.textView1);
            TextView tv2 = (TextView) findViewById(R.id.textView2);
            TextView tv3 = (TextView) findViewById(R.id.textView3);
            tv1.setText(book.get(0).getAuthorName());
            tv2.setText(book.get(0).getName());
            tv3.setText(book.get(0).getIsbn());
*/

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