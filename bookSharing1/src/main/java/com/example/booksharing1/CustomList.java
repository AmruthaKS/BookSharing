package com.example.booksharing1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class CustomList extends ArrayAdapter<String>{
private final Activity context;
private final String[] bookname;
private final String[] author;
private final String[] genre;
private final Integer[] imageId;

public CustomList(Activity context, String[] bookname,String[] author,String[] genre, Integer[] imageId) {
	super(context, R.layout.mybook_list_single, bookname);
	this.context = context;
	this.bookname = bookname;
	this.author = author;
	this.genre = genre;
	this.imageId = imageId;
}

@Override
public View getView(int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.mybook_list_single, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	TextView txtTitle1 = (TextView) rowView.findViewById(R.id.txt1);
	TextView txtTitle2 = (TextView) rowView.findViewById(R.id.txt2);
	
	ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
	txtTitle.setText(bookname[position]);
	txtTitle1.setText(author[position]);
	txtTitle2.setText(genre[position]);
	
	imageView.setImageResource(imageId[position]);
	return rowView;
}
}