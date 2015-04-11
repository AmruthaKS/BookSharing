package com.example.booksharing1;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.booksharing1.MyLocation.LocationResult;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;



public class Profile extends NavigationDrawer 
 {
	String city = null;
	String streetname = null;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//setContentView(R.layout.activity_main1);
		super.onCreate(savedInstanceState);
		mDrawerList.setItemChecked(pos, true);
		setTitle(mPlanetTitles[pos]);
		getLayoutInflater().inflate(R.layout.profile, frameLayout);
		
		
		ImageButton ib = (ImageButton)findViewById(R.id.imageButton1);
        ib.setOnClickListener(new OnClickListener() {
        
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	
            	Toast.makeText(getApplicationContext(), "Get location clicked",
            			   Toast.LENGTH_LONG).show();
            	LocationResult locationResult = new LocationResult(){
            	    @Override
            	    public void gotLocation(Location loc){
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
            	            	 for(int j=0;j<address.getMaxAddressLineIndex();j++) 
            	            		 street =  street.concat(address.getAddressLine(j));
            	            	 
            	            	 city = cityName;
                 	            streetname = street;
            	            }
            	            else {
            	            	city = "newem";
                 	            streetname = "new1em";
            	            }
            	            System.out.println("Got some location details.."+street);
            	           // Toast.makeText(getApplicationContext(), "Get location !!!!!"+street+"\t"+cityName,
                      		//	   Toast.LENGTH_LONG).show();              	            
            	            		
            	        }
            	        catch (IOException e) {
            	        	city = "newe";
             	            streetname = "new1e";
            	            e.printStackTrace();
            	        }
            	        
            	    	
            	    }
            	};
            	MyLocation myLocation = new MyLocation();
            	myLocation.getLocation(getApplicationContext(), locationResult);
            	Toast.makeText(getApplicationContext(), "City : "+city,
            			Toast.LENGTH_LONG).show(); 
            	Toast.makeText(getApplicationContext(), "street: "+streetname,
            			Toast.LENGTH_LONG).show();
            	
            }
        });
        
        
        ImageButton ib2 = (ImageButton)findViewById(R.id.imageButton2);
        ib2.setOnClickListener(new OnClickListener() {
        
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            
            	IntentIntegrator integrator = new IntentIntegrator(Profile.this);
            	integrator.initiateScan();
            	
           }
            
            
            	
        });
        
       
	
	
	}

	 @Override
     public void onActivityResult(int requestCode, int resultCode, Intent intent) {
   	  IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
   	  if (result != null) {
   	    // handle scan result
   		String contents = result.getContents();
   		if (contents != null) {
   			showDialog(R.string.result_succeeded, result.toString());
   		} else {
   			showDialog(R.string.result_failed, getString(R.string.result_failed_why));
   		}
   		 
   	  }
   	  // else continue with any other code you need in the method
   	  
   	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void showDialog(int title, CharSequence message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(title);
	    builder.setMessage(message);
	    builder.setPositiveButton(R.string.ok_button, null);
	    builder.show();
	  }
}