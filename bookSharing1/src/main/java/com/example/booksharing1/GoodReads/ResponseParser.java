package com.example.booksharing1.GoodReads;



import java.io.IOException;
import java.io.InputStream;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.xml.sax.SAXException;


import android.net.Uri;
import android.sax.RootElement;
import android.util.Xml;

public class ResponseParser
{
    private static String _ConsumerKey = "QLM3lL2nqXe4LujHQt12A";
    private static String _ConsumerSecret = "Aegcm52QdTinBh6g5fZe81S5cVYdKk9P6IDVS38pDOw";

    private static CommonsHttpOAuthConsumer _Consumer = new CommonsHttpOAuthConsumer(_ConsumerKey, _ConsumerSecret);
    private static boolean _IsAuthenticated = false;

    public static Response parse(InputStream inputStream) throws IOException, SAXException
    {
        final Response response = new Response();

        RootElement root = new RootElement("GoodreadsResponse");
        response.set_User(User.appendSingletonListener(root, 0));


        try
        {
            Xml.parse(inputStream, Xml.Encoding.UTF_8, root.getContentHandler());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }

    public static User GetAuthorizedUser() throws Exception
    {
        HttpGet getRequest = new HttpGet("http://www.goodreads.com/api/auth_user");
        _Consumer.sign(getRequest);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(getRequest);

        Response responseData = ResponseParser.parse(response.getEntity().getContent());
        return responseData.get_User();
    }




    public static User GetUserDetails(String userId) throws Exception
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.authority("www.goodreads.com");
        builder.path("user/show/" + userId + ".xml");
        builder.appendQueryParameter("key", _ConsumerKey);

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(builder.build().toString());
        if (get_IsAuthenticated())
        {
            _Consumer.sign(getRequest);
        }
        HttpResponse response;

        response = httpClient.execute(getRequest);

        Response responseData = ResponseParser.parse(response.getEntity().getContent());

        return responseData.get_User();
    }

    private static void set_IsAuthenticated(boolean _IsAuthenticated)
    {
        ResponseParser._IsAuthenticated = _IsAuthenticated;
    }

    public static boolean get_IsAuthenticated()
    {
        return _IsAuthenticated;
    }

    public static void SetTokenWithSecret(String token, String tokenSecret)
    {
        _Consumer.setTokenWithSecret(token, tokenSecret);
        set_IsAuthenticated(true);
    }


}