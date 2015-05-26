package com.example.booksharing1;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booksharing1.LocationHelper.LocationResult;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.graphics.BitmapFactory.decodeStream;


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

        TextView name = (TextView)findViewById(R.id.name);
        TextView email = (TextView)findViewById(R.id.email);
        TextView address = (TextView)findViewById(R.id.address);
        final ImageView pic = (ImageView)findViewById(R.id.profilePic);

        UserInfo usr = UserInfo.getInstance();

        name.setText(usr.getName());
        email.setText("\nEmail : " +usr.getEmail());
        String Address = "\nAddress : ";
        if ( usr.getAddresses().isEmpty()) {
            // No addresses are found
            address.setText("\nAddress : No address listed \n");
        } else {
             for ( int i=0 ; i < usr.getAddresses().size() ; i++ ) {
                 Address.concat(usr.getAddresses().get(i).toString());
                 Address.concat("\n");
             }
             address.setText(Address);
        }
        final String img_url= usr.getProfileImageUrl();

        System.out.print("the url is ... "+img_url);
        final URL[] url = new URL[1];

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //Your code goes here
                    url[0] = new URL(img_url);
                    Bitmap bmp;
                    bmp = decodeStream(url[0].openConnection().getInputStream());
                    pic.setImageBitmap(bmp);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
		
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
            	LocationHelper myLocation = new LocationHelper();
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


        Button sync = (Button)findViewById(R.id.force_sync);
        sync.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // make a call to sync to good reads
                new HttpRequestTaskPost().execute();

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


     private class HttpRequestTaskPost extends AsyncTask<Void, Void, ResponseEntity<String>> {

         @Override
         protected ResponseEntity<String> doInBackground(Void... params) {
             String url = "http://106.206.213.59:8389/neo4j/v1/users/";
             url += UserInfo.getInstance().getId();
             url += "/goodreads/synch";

             List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
             acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

             HttpHeaders headers = new HttpHeaders();
             headers.setAccept(acceptableMediaTypes);


             HttpEntity<String> entity = new HttpEntity<String>("new", headers);


             RestTemplate restTemplate = new RestTemplate();
             restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
             ResponseEntity<String> result = null;
             try {
                 result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
             } catch (Exception e) {
                 System.out.print("Exception occurred while doing force sync.."+e.getMessage());
             }
             return result;
         }


         @Override
         protected void onPostExecute(ResponseEntity<String> result ) {
             // can start an intent to home page
             if ( result.getStatusCode().equals("200") ) {
                 System.out.println("Sync completed !!! yay ");
             }

             // to do : if fails update UI with the corresponding response string

         }
     }


}