package com.example.booksharing1;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.LeadingMarginSpan;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.booksharing1.JSON.Book;
import com.example.booksharing1.JSON.BookRelation;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static android.graphics.BitmapFactory.decodeStream;


class FlowTextHelper {
    private static boolean mNewClassAvailable;

    /* class initialization fails when this throws an exception */
    static {
        try {
            Class.forName("android.text.style.LeadingMarginSpan$LeadingMarginSpan2");
            mNewClassAvailable = true;
        } catch (Exception ex) {
            mNewClassAvailable = false;
        }
    }

    public static void tryFlowText(String text, View thumbnailView, TextView messageView, Display display, int addPadding){
        // There is nothing I can do for older versions, so just return
        if(!mNewClassAvailable) return;

        // Get height and width of the image and height of the text line
        thumbnailView.measure(display.getWidth(), display.getHeight());
        int height = thumbnailView.getMeasuredHeight();
        height = height/2;
        int width = thumbnailView.getMeasuredWidth() + addPadding;
        messageView.measure(width, height); //to allow getTotalPaddingTop
        int padding = messageView.getTotalPaddingTop();
        float textLineHeight = messageView.getPaint().getTextSize();

        // Set the span according to the number of lines and width of the image
        int lines =  (int)Math.round((height) / textLineHeight);
        SpannableString ss = new SpannableString(text);
        //For an html text you can use this line: SpannableStringBuilder ss = (SpannableStringBuilder)Html.fromHtml(text);
        ss.setSpan(new MyLeadingMarginSpan2(lines, width), 0, ss.length(), 0);
        messageView.setText(ss);

        // Align the text with the image by removing the rule that the text is to the right of the image
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)messageView.getLayoutParams();
        int[]rules = params.getRules();
        rules[RelativeLayout.RIGHT_OF] = 0;
    }


}

class MyLeadingMarginSpan2 implements LeadingMarginSpan.LeadingMarginSpan2 {
    private int margin;
    private int lines;

    public MyLeadingMarginSpan2(int lines, int margin) {
        this.margin = margin;
        this.lines = lines;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return first ? margin : 0;
    }

    @Override
    public int getLeadingMarginLineCount() {
        return lines;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom, CharSequence text,
                                  int start, int end, boolean first, Layout layout) {}
}

public class SingleBook  extends NavigationDrawer {
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(R.layout.activity_main1);
        super.onCreate(savedInstanceState);
        mDrawerList.setItemChecked(pos, true);
        setTitle(mPlanetTitles[pos]);
        getLayoutInflater().inflate(R.layout.single_book, frameLayout);
        c = this;
        TextView title = (TextView) findViewById(R.id.title);
        TextView author = (TextView) findViewById(R.id.author);
        TextView description = (TextView) findViewById(R.id.description);
        TextView year = (TextView) findViewById(R.id.year);
        TextView publisher = (TextView) findViewById(R.id.publisher);
        TextView numPages = (TextView) findViewById(R.id.numPages);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        TableLayout tableLend = (TableLayout)findViewById(R.id.single_book_lend);
        TableLayout tableOwn = (TableLayout)findViewById(R.id.single_book_own);
        TableLayout tableWant = (TableLayout)findViewById(R.id.single_book_want);
        ListView availabilityView = (ListView) findViewById( R.id.AvailabilityListView);

        tableLend.setVisibility(View.INVISIBLE);
        tableOwn.setVisibility(View.INVISIBLE);
        tableWant.setVisibility(View.INVISIBLE);
        availabilityView.setVisibility(View.INVISIBLE);


        Intent i = getIntent();
        Book book = (Book)i.getSerializableExtra("bookObj");

        title.setText(book.getName());
        author.setText("by "+book.getAuthorName());
        description.setText(book.getDescription());

        // Find how the book is related to user.
        new HttpRequestTaskGetBook(c).execute(book.getId());

    //  Display display = getWindowManager().getDefaultDisplay();
    //    FlowTextHelper.tryFlowText(book.getDescription(), imageView, description, display ,10);

        year.setText("Published in "+String.valueOf(book.getPublishedYear()));
        publisher.setText("Published by "+book.getPublisher());
        numPages.setText(String.valueOf(book.getNumberOfPages())+ " pages");

        final String img_url= book.getImageUrl();

        final URL[] url = new URL[1];
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //Your code goes here
                    url[0] = new URL(img_url);
                    Bitmap bmp;
                    bmp = decodeStream(url[0].openConnection().getInputStream());
                    imageView.setImageBitmap(bmp);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class HttpRequestTaskGetBook extends AsyncTask<String, Void, BookRelation> {
        private Context context;
        public HttpRequestTaskGetBook(Context c) {
            this.context = c;
        }
        @Override
        protected BookRelation doInBackground(String... id) {

            String url = "http://106.206.193.172:8389/neo4j/v1/"+id+"/users/"+UserInfo.getInstance().getId();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("in doing get for the object...!!!!!!!"+id);
            BookRelation book = new BookRelation();
            try {

                book = restTemplate.getForObject(url, BookRelation.class);
                System.out.println("user details are"+book.getStatus());
            } catch (Exception e){
                System.out.print("exception!!!!!!!!!!"+e.getMessage());
            }
            return book;
        }

        @Override
        protected void onPostExecute(BookRelation u) {

            System.out.print("in post execute of get book !!!!!!!!!!!!!!!!!!");
            // now based on the status of the book, hide/unhide the ui elements accordingly

            if ( u.getStatus().equals("own") ) {

            } else if ( u.getStatus().equals("lent") ) {

            } else if ( u.getStatus().equals("wish") ) {
                // If the user has it in wish list then show the list of users who have that book
                // call another async task to get that
                new HttpRequestTaskGetUsers().execute(u.getId().toString());
            } else {

            }



        }

    }


    private class HttpRequestTaskGetUsers extends AsyncTask<String, Void, BookRelation> {

        @Override
        protected BookRelation doInBackground(String... id) {

            String url = "http://106.206.193.172:8389/neo4j/v1/"+id+"/users/"+UserInfo.getInstance().getId();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("in doing get for the object...!!!!!!!"+id);
            BookRelation book = new BookRelation();
            try {

                book = restTemplate.getForObject(url, BookRelation.class);
                System.out.println("user details are"+book.getStatus());
            } catch (Exception e){
                System.out.print("exception!!!!!!!!!!"+e.getMessage());
            }
            return book;
        }

        @Override
        protected void onPostExecute(BookRelation u) {

            System.out.print("in post execute of get book !!!!!!!!!!!!!!!!!!");
            // now based on the status of the book, hide/unhide the ui elements accordingly

            if ( u.getStatus().equals("owns") ) {

            } else if ( u.getStatus().equals("lent") ) {

            } else if ( u.getStatus().equals("wishlist") ) {
                // If the user has it in wish list then show the list of users who have that book
                // call another async task

            } else {

            }


        }

    }
}
