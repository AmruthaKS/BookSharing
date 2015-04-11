package com.example.booksharing1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static android.graphics.BitmapFactory.decodeStream;

public class CustomBookList extends ArrayAdapter<String>{
private final Activity context;
private final String bookarray[];
List<Book> books;

public CustomBookList(Activity context, List<Book> book ,String bookarray[]) {
	super(context, R.layout.mybook_list_single, bookarray);
	this.context = context;
    this.bookarray = bookarray;
    this.books = book;
}

@Override
public View getView(int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.book_list_single, null, true);

    TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	TextView txtTitle1 = (TextView) rowView.findViewById(R.id.txt1);
	TextView txtTitle2 = (TextView) rowView.findViewById(R.id.txt2);
	final ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

	txtTitle.setText(books.get(position).getName());
	txtTitle1.setText(books.get(position).getAuthorName());
	txtTitle2.setText(books.get(position).getDescription());
//	imageView.setImageResource(books.get(position).getImageUrl());
    imageView.setImageResource(R.drawable.saturn);


    final String img_url= books.get(position).getImageUrl();
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

	return rowView;
}
}