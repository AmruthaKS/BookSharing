package com.example.booksharing1;
import com.example.booksharing1.JSON.Fields;
import com.example.booksharing1.JSON.Field;

import com.example.booksharing1.GoodReads.OAuthRequestTokenTask;
import com.example.booksharing1.GoodReads.ResponseParser;
import com.example.booksharing1.GoodReads.User;


import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GoodReadsLogin extends FragmentActivity
{
    private final static String _ConsumerKey = "QLM3lL2nqXe4LujHQt12A";
    private final static String _ConsumerSecret = "Aegcm52QdTinBh6g5fZe81S5cVYdKk9P6IDVS38pDOw";
    private final static String _CallbackUrl = "oauth:///";
    private final static CommonsHttpOAuthConsumer _Consumer = new CommonsHttpOAuthConsumer(_ConsumerKey, _ConsumerSecret);
    private Context c;
    private String token;
    private String secret;
    private String userid;

    private final static OAuthProvider _Provider = new DefaultOAuthProvider(
            "http://www.goodreads.com/oauth/request_token",
            "http://www.goodreads.com/oauth/access_token",
            "http://www.goodreads.com/oauth/authorize");

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.goodreads_login);
        c = this;
        Button loginButton = (Button)findViewById(R.id.gr_login_button);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {

                new OAuthRequestTokenTask(c, _Consumer, _Provider).execute();

            }
        });

        Button noLoginButton = (Button) findViewById(R.id.gr_no_login_button);
        noLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent tokens = new Intent(getIntent());
                tokens.putExtra("token", "");
                tokens.putExtra("tokenSecret", "");
                setResult(RESULT_OK, tokens);
                finish();
            }
        });
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processResponse();
    }

    protected void processResponse(){
        Intent intent = getIntent();
        Uri uri = this.getIntent().getData();
      //  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences prefs = getSharedPreferences("com.onesadjam.yagrac", MODE_PRIVATE);
        if (uri != null
                && uri.toString().startsWith(_CallbackUrl)) {
            Log.i("abc", "Callback received : " + uri);
            Log.i("abc", "Retrieving Access Token");
            new RetrieveAccessTokenTask(this, _Consumer, _Provider, prefs)
                    .execute(uri);
        //    finish();
        }

        String token = prefs.getString("userId", "");
        Toast.makeText(this, "The request success .. yay."+token, Toast.LENGTH_LONG).show();

    }

    /** Called when the activity is resumed. */
    @Override
    public void onResume()
    {
        super.onResume();

        // We might be resuming due to the web browser sending us our
        // access tokens.  If so, save them and finish.
        Uri uri = this.getIntent().getData();
        if (uri != null && uri.toString().startsWith(_CallbackUrl))
        {
            String oauthToken = uri.getQueryParameter(OAuth.OAUTH_TOKEN);
            // this will populate token and token_secret in consumer
            try
            {
                // Crazy sauce can happen here. Believe it or not, the entire app may have been flushed
                // from memory while the browser was active.
                SharedPreferences sharedPreferences = getSharedPreferences("com.onesadjam.yagrac", MODE_PRIVATE);
                String requestToken = sharedPreferences.getString("RequestToken", "");
                String requestTokenSecret = sharedPreferences.getString("RequestTokenSecret", "");
                if (requestToken.length() == 0 || requestTokenSecret.length() == 0)
                {
                    Toast.makeText(this, "The request tokens were lost, please close the browser and application and try again.", Toast.LENGTH_LONG).show();
                 //   finish();
                    return;
                }
                processResponse();

               /*
                _Consumer.setTokenWithSecret(requestToken, requestTokenSecret);
                _Provider.retrieveAccessToken(_Consumer, oauthToken);
                */



            } finally {

            };

        }

        // we also might be resuming because the user backed out of the browser.
        else
        {
            SharedPreferences sharedPreferences = getSharedPreferences("com.onesadjam.yagrac", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            String tokenSecret = sharedPreferences.getString("tokenSecret", "");
            String userId = sharedPreferences.getString("userId", "");

            if ( token != "" )
            {

                Intent tokens = new Intent(getIntent());
                tokens.putExtra("com.onesadjam.yagrac.token", token);
                tokens.putExtra("com.onesadjam.yagrac.tokenSecret", tokenSecret);
                tokens.putExtra("com.onesadjam.yagrac.userId", userId);
                setResult(RESULT_OK, tokens);
                new HttpRequestTaskPut().execute();
             //   finish();
            }
        }
    }

    private class HttpRequestTaskPut extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://10.74.229.154:8389/neo4j/v1/users/{id}/fields";

            // get the userId for the user from singleton class
            String id = UserInfo.getInstance().getId();
           // url+=id;
           // url+="/fields";

            System.out.print("in updating user goodreads details!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            SharedPreferences sharedPreferences = getSharedPreferences("com.onesadjam.yagrac", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            String tokenSecret = sharedPreferences.getString("tokenSecret", "");
            String userId = sharedPreferences.getString("userId", "");

            Map<String, String> putParams = new HashMap<String, String>();
            putParams.put("id", id);

            com.example.booksharing1.JSON.User updatedUser = new com.example.booksharing1.JSON.User(id);
            com.example.booksharing1.JSON.User euser = updatedUser;

            updatedUser.setGoodreadsAccessToken(token);
            updatedUser.setGoodreadsAccessTokenSecret(tokenSecret);
            updatedUser.setGoodreadsAuthStatus("yes");
            updatedUser.setGoodreadsId(userId);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
           // acceptableMediaTypes.add(MediaType.APPLICATION_XML);
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(acceptableMediaTypes);

            List<Field> fields = new ArrayList<Field>();
            fields.add(new Field("goodreadsAccessToken",token));
            fields.add(new Field("goodreadsAccessTokenSecret",tokenSecret));
            fields.add(new Field("goodreadsAuthStatus","yes"));
            fields.add(new Field("goodreadsId",userId));

            Fields f = new Fields(fields);

            HttpEntity<Fields> entity = new HttpEntity<Fields>(f, headers);
            try {
                ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, id);
            } catch (Exception e) {
                System.out.print("in exception of PUT request..."+e.getMessage());
            }

            return null;
        }



    }



    public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

        private Context context;
        private OAuthProvider provider;
        private OAuthConsumer consumer;
        private SharedPreferences prefs;

        public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,
                                       OAuthProvider provider, SharedPreferences prefs) {
            this.context = context;
            this.consumer = consumer;
            this.provider = provider;
            this.prefs = prefs;
        }

        /**
         * Retrieve the oauth_verifier, and store the oauth and
         * oauth_token_secret for future API calls.
         */
        @Override
        protected Void doInBackground(Uri... params) {
            final Uri uri = params[0];
            final String oauth_verifier = uri
                    .getQueryParameter(OAuth.OAUTH_VERIFIER);

            try {
                provider.retrieveAccessToken(consumer, oauth_verifier);

                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("token", consumer.getToken());
                edit.putString("tokenSecret",
                        consumer.getTokenSecret());
                edit.commit();

                String token = prefs.getString("token", "");
                String secret = prefs.getString("tokenSecret", "");

                consumer.setTokenWithSecret(token, secret);

                ResponseParser.SetTokenWithSecret(token, secret);
                String userId = "";
                try
                {
                    User authorizedUser = ResponseParser.GetAuthorizedUser();
                    userId = authorizedUser.get_Id();
                    edit = prefs.edit();
                    Log.d("YAY","the user got is !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+userId);
                    edit.putString("userId", userId);
                    edit.commit();
                  //  Toast.makeText(this, "user id is "+userId, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                   // Toast.makeText(this, "Error getting authorized user:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("error","Exception in getting the user"+e.getMessage());
                }
                // save all the data we got in the user details
                new HttpRequestTaskPut().execute();

                // show location only for new user. else goto mybooks class
                int isNewUser = FbLogin.getNewUser();
                if(isNewUser == 1) {
                    context.startActivity(new Intent(context, Location.class));
                } else {
                    context.startActivity(new Intent(context, FindOwnedBooks.class));
                }

                Log.i("abc", "OAuth - Access Token Retrieved");
                Log.i("abc", "access token: "+token); // prints access token

            } catch (Exception e) {
                Log.e("abc", "OAuth - Access Token Retrieval Error", e);
            }

            return null;
        }
    }

}

