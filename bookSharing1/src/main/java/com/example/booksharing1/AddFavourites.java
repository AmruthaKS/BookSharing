package com.example.booksharing1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.booksharing1.JSON.Favourites;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AddFavourites extends Activity implements  OnClickListener {
    Button button;
    ListView listView;
    ArrayAdapter<String> adapter;
    int redirectBackflag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.add_favourites);
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.testbutton);
        String[] sports = getResources().getStringArray(R.array.planets_array);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, sports);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        String caller;

        if (extras != null) {
            caller = getIntent().getStringExtra("caller");
            if ( caller.equals("favourites")) {
                redirectBackflag = 1;
            }
        }


        button.setOnClickListener(this);


    }


    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }
        Set setA = new HashSet();


        for (int i = 0; i < selectedItems.size(); i++) {
            setA.add(selectedItems.get(i));
        }

        // call async task to add favorites
        new HttpRequestTaskPost().execute(setA);
    }

    private class HttpRequestTaskPost extends AsyncTask<Set<String>, Void, ResponseEntity<String>> {

        @Override
        protected ResponseEntity<String> doInBackground(Set<String>... params) {
            String url = "http://27.57.16.139:8389/neo4j/v1/users/";
            url += UserInfo.getInstance().getId();
            url += "/favourites";
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(acceptableMediaTypes);

            Favourites f = new Favourites(params[0]);

            HttpEntity<com.example.booksharing1.JSON.Favourites> entity = new HttpEntity<com.example.booksharing1.JSON.Favourites>(f, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<String> result = null;
            try {
                 result = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
                // add the favourites to the UserInfo class
                UserInfo uInfo = UserInfo.getInstance();
                uInfo.addFavorites(params[0]);
            } catch (Exception e) {
                System.out.print("Exception occurred.."+e.getMessage());
            }
            return result;
        }


        @Override
        protected void onPostExecute(ResponseEntity<String> result ) {
            // can start an intent to home page
            System.out.println("");
            if ( redirectBackflag == 1 ) {
                // this is called from Favourites tab. So return back there.
                Intent i = new Intent(AddFavourites.this, com.example.booksharing1.Favourites.class);
                startActivity(i);
            } else {
                Intent i = new Intent(AddFavourites.this, Books.class);
                startActivity(i);
            }

        }
    }

}
