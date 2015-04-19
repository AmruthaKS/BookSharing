package com.example.booksharing1.GoodReads;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;


public class OAuthRequestTokenTask extends AsyncTask<Void, Void, Void> {
    final String TAG = getClass().getName();
    private Context context;
    private OAuthProvider provider;
    private OAuthConsumer consumer;
    private String _CallbackUrl = "oauth:///";

    /**
     *
     * We pass the OAuth consumer and provider.
     *
     * @param context
     *            Required to be able to start the intent to launch the browser.
     * @param provider
     *            The OAuthProvider object
     * @param consumer
     *            The OAuthConsumer object
     */
    public OAuthRequestTokenTask(Context context, OAuthConsumer consumer,
                                 OAuthProvider provider) {
        this.context = context;
        this.consumer = consumer;
        this.provider = provider;
    }

    /**
     *
     * Retrieve the OAuth Request Token and present a browser to the user to
     * authorize the token.
     *
     */
    @Override
    protected Void doInBackground(Void... params) {

        try {
            Log.i(TAG, "Retrieving request token from Goodreads server");
            final String url = provider.retrieveRequestToken(consumer, _CallbackUrl);
            Log.i(TAG, "Popping a browser with the authorize URL : " + url);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    | Intent.FLAG_ACTIVITY_NO_HISTORY
                                    | Intent.FLAG_FROM_BACKGROUND
                    );
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.onesadjam.yagrac", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("RequestToken", consumer.getToken());
            editor.putString("RequestTokenSecret", consumer.getTokenSecret());
            editor.commit();

            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error during OAUth retrieve request token", e);
        }

        return null;
    }



}
