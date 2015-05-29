package com.example.booksharing1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.booksharing1.JSON.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FindOwnedBooks extends Activity {
    private ListView mainListView ;
    private ReadBooks[] readBooks;
    private ArrayAdapter<ReadBooks> listAdapter;
    private Context ctx;
    private ProgressBarHandler mProgressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.find_owned_books);
        super.onCreate(savedInstanceState);
        ctx = this;

        readBooks = (ReadBooks[]) getLastNonConfigurationInstance() ;
        if ( readBooks == null ) {
            // call async task to find out the books read by the user (from goodreads) and ask him to mark owned books
            new HttpRequestGetReadBooks().execute();

        }

        Button skip = (Button) findViewById(R.id.skipOwnedBooks);
        Button add  = (Button) findViewById(R.id.AddOwnedBooks);

        skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // goto mybooks
                Intent intent = new Intent(ctx, AddFavourites.class);
                ctx.startActivity(intent);

            }
        });


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // goto mybooks
                Intent intent = new Intent(ctx, AddFavourites.class);
                ctx.startActivity(intent);

            }
        });


        mainListView = (ListView) findViewById( R.id.mainListView );

        // When item is tapped, toggle checked properties of CheckBox and ReadBook.
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View item,
                                     int position, long id) {
                ReadBooks readBooks = listAdapter.getItem( position );
                readBooks.toggleChecked();
                ReadBooksViewHolder viewHolder = (ReadBooksViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked( readBooks.isChecked() );
            }
        });


        mProgressBarHandler = new ProgressBarHandler(this);
        mProgressBarHandler.show();


       // bar.setVisibility(View.VISIBLE);
      //  progress.setProgressDrawable();
        /*
        progress.setTitle("Loading");
        progress.setMessage("Fetching Books...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show(); */

    }

    private static class ReadBooks {
        private String name = "" ;
        private boolean checked = false ;
        private String bookId = "";
        public ReadBooks() {}
        public ReadBooks(String name) {
            this.name = name ;
        }
        public ReadBooks(String name, boolean checked) {
            this.name = name ;
            this.checked = checked ;
        }
        public ReadBooks(String name, String bookId) {
            this.name = name ;
            this.bookId = bookId ;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public boolean isChecked() {
            return checked;
        }
        public void setChecked(boolean checked) {
            this.checked = checked;
            // call async task
            if (checked ) {
                // true -> so call async task
                new HttpRequestPostOwnedBooks().execute(bookId);

            }
        }
        public String toString() {
            return name ;
        }
        public void toggleChecked() {

            checked = !checked ;
            System.out.print("in toggle checked !!!!!!!!!.. the value is "+checked);
        }
    }

    /** Holds child views for one row. */
    private static class ReadBooksViewHolder {
        private CheckBox checkBox ;
        private TextView textView ;
        public ReadBooksViewHolder() {}
        public ReadBooksViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox ;
            this.textView = textView ;
        }
        public CheckBox getCheckBox() {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox) {

            this.checkBox = checkBox;
        }
        public TextView getTextView() {
            return textView;
        }
        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    /** Custom adapter for displaying an array of ReadBook objects. */
    private static class ReadBooksArrayAdapter extends ArrayAdapter<ReadBooks> {

        private LayoutInflater inflater;

        public ReadBooksArrayAdapter(Context context, List<ReadBooks> readBooksList) {
            super( context, R.layout.books_wid_checkbox_row, R.id.rowTextView, readBooksList);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context) ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // ReadBook to display
            ReadBooks readBooks = (ReadBooks) this.getItem( position );

            // The child views in each row.
            CheckBox checkBox ;
            TextView textView ;

            // Create a new row view
            if ( convertView == null ) {
                convertView = inflater.inflate(R.layout.books_wid_checkbox_row, null);

                // Find the child views.
                textView = (TextView) convertView.findViewById( R.id.rowTextView );
                checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );

                // Optimization: Tag the row with it's child views, so we don't have to
                // call findViewById() later when we reuse the row.
                convertView.setTag( new ReadBooksViewHolder(textView,checkBox) );

                // If CheckBox is toggled, update the ReadBook it is tagged with.
                checkBox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        ReadBooks readBooks = (ReadBooks) cb.getTag();
                        readBooks.setChecked(cb.isChecked());

                    }
                });
            }
            // Reuse existing row view
            else {
                // Because we use a ViewHolder, we avoid having to call findViewById().
                ReadBooksViewHolder viewHolder = (ReadBooksViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox() ;
                textView = viewHolder.getTextView() ;
            }

            // Tag the CheckBox with the ReadBook it is displaying, so that we can
            // access the ReadBook in onClick() when the CheckBox is toggled.
            checkBox.setTag(readBooks);

            // Display ReadBook data
            checkBox.setChecked( readBooks.isChecked() );
            textView.setText( readBooks.getName() );

            return convertView;
        }

    }


    public Object onRetainNonConfigurationInstance() {
        return readBooks;
    }

    private class HttpRequestGetReadBooks extends AsyncTask<Void, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(Void... params) {

            String url = "users/"+UserInfo.getInstance().getId()+"/books?filter=read";
            List<Book> books = new HttpRestClient().getReadBooks(url);
            return books;
        }


        @Override
        protected void onPostExecute(List<Book> books) {
            // To dismiss the dialog
            //progress.dismiss();
            //bar.setVisibility(View.INVISIBLE);
            mProgressBarHandler.hide();

            System.out.print("after getting the read books !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            // Got the list of books.
            readBooks = new ReadBooks[books.size()];
            for ( int i=0 ; i < books.size() ; i++ ) {
                readBooks[i] = new ReadBooks(books.get(i).getName(),books.get(i).getId());
            }

            ArrayList<ReadBooks> readBooksList = new ArrayList<ReadBooks>();
            readBooksList.addAll( Arrays.asList(readBooks) );

            listAdapter = new ReadBooksArrayAdapter(ctx, readBooksList);
            mainListView.setAdapter( listAdapter );

        }
    }


    private static class HttpRequestPostOwnedBooks extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String url = "users/"+UserInfo.getInstance().getId()+"/books/{id}/own";
            String result = new HttpRestClient().addBookToUser(url, new Book(params[0]));
            return result;
        }


        @Override
        protected void onPostExecute(String response) {
            // find the values stored in the book


        }
    }
}
