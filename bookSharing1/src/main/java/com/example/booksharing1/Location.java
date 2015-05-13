package com.example.booksharing1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Location extends Activity {

    String city = null;
    String streetname = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.location);
        super.onCreate(savedInstanceState);
        Button skip = (Button) findViewById(R.id.SkipButton);
        final Context c = this;
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "skip clicked",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c, MyBooks.class);
                c.startActivity(intent);
            }

        });

        ImageButton ib = (ImageButton) findViewById(R.id.imageButton1);
        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getApplicationContext(), "Get location clicked",
                        Toast.LENGTH_LONG).show();
                LocationHelper.LocationResult locationResult = new LocationHelper.LocationResult() {
                    @Override
                    public void gotLocation(android.location.Location loc) {
                        //Got the location!
                        //Toast.makeText(getApplicationContext(), "Got location !!!!!",
                        //	   Toast.LENGTH_LONG).show();
                        if (loc == null) {
                            city = "new";
                            streetname = "new1";
                        }
                        String cityName = "";
                        String street = "";
                        Address address;
                        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                        List<Address> addresses;
                        try {
                            addresses = gcd.getFromLocation(loc.getLatitude(),
                                    loc.getLongitude(), 1);
                            if (addresses != null && addresses.size() > 0) {

                                address = addresses.get(0);
                                System.out.println(addresses.get(0).getLocality());
                                cityName = addresses.get(0).getLocality();
                                // street = address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "";
                                for (int j = 0; j < address.getMaxAddressLineIndex(); j++)
                                    street = street.concat(address.getAddressLine(j));

                                city = cityName;
                                streetname = street;
                            } else {
                                city = "newem";
                                streetname = "new1em";
                            }
                            System.out.println("Got some location details.." + street);
                            // Toast.makeText(getApplicationContext(), "Get location !!!!!"+street+"\t"+cityName,
                            //	   Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            city = "newe";
                            streetname = "new1e";
                            e.printStackTrace();
                        }


                    }
                };
                LocationHelper myLocation = new LocationHelper();
                myLocation.getLocation(getApplicationContext(), locationResult);
                Toast.makeText(getApplicationContext(), "City : " + city,
                        Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "street: " + streetname,
                        Toast.LENGTH_LONG).show();

            }
        });
    }

}
