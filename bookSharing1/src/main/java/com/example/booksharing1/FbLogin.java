package com.example.booksharing1;
import com.example.booksharing1.JSON.User;
import com.facebook.Request;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class FbLogin extends FragmentActivity {
	private LoginButton loginBtn;
	private TextView username;
	private UiLifecycleHelper uiHelper;
    protected String fbUserName = null;
    protected String fbUserId = null;
    protected String fbUserEmail = null;
    private  static int newUser = 0;

    public static int getNewUser() {
        return newUser;
    }
    public static void setNewUser (int i) {
        newUser = i;
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.fblogin);


		username = (TextView) findViewById(R.id.username);
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		loginBtn.setReadPermissions(Arrays.asList("email" ));
        final int[] flag = {0};
        final Context context = this;
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					username.setText("You are currently logged in as " + user.getName()+" Please wait while we redirect");
					// can now redirect to our app page:
                    //use a thread to find out if user exists and get ID else do a POST
                    flag[0] = 1;
                    fbUserId = user.getId();
                    fbUserName = user.getName();
                    fbUserEmail = (String) user.getProperty("email");
                    new HttpRequestTaskGet(context).execute();
					
				} else {
					username.setText("You are not logged in.");
				}
			}
		});
        if (flag[0]==0) {

        }

      //  loginBtn.getUserInfoChangedCallback();
	}


	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				Log.d("MainActivity", "Facebook session opened.");
                Request request1 = new Request();
                String ver = request1.getVersion();
                System.out.println("version is "+ver);

                Request request = new Request(session, "me/events?fields=cover,name,start_time");
                request.executeAsync();

                /*
                Request.newMeRequest(session, new Request.GraphUserCallback()
                {
                    @Override
                    public void onCompleted(GraphUser user, Response response)
                    {
                        if (null != user)
                        {
                            fbUserId = user.getId();
                            fbUserName = user.getName();
                            fbUserEmail = (String) user.getProperty("email");
                            new HttpRequestTaskGet().execute();
                        }
                    }
                }).executeAsync();
                */
			} else if (state.isClosed()) {
				Log.d("MainActivity", "Facebook session closed.");
			}
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

    private class HttpRequestTaskGet extends AsyncTask<Void, Void, User> {
        private Context context;
        public HttpRequestTaskGet(Context c) {
            this.context = c;
        }
        @Override
        protected User doInBackground(Void... fbId) {

            String url = "http://117.96.1.204:8389/neo4j/v1/users/fbId/"+fbUserId;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("in doing get for the object...!!!!!!!"+fbUserId);
            User user = new User();
            try {

               user = restTemplate.getForObject(url, User.class);
               System.out.println("user details are"+user.getEmail());
            } catch (Exception e){
                System.out.print("exception!!!!!!!!!!"+e.getMessage());
            }
            return user;
        }

        @Override
        protected void onPostExecute(User u) {
            //now check if the user email-id is null (this means some exception occurred / user doesn't exist and hence create user
            System.out.print("in post execute of get user !!!!!!!!!!!!!!!!!!");
            if(u.getEmail() == null) {
                new HttpRequestTaskPost().execute();
            }
            // save user details in global config
            else {
                UserInfo u1 = UserInfo.getInstance();
                u1.copy(u);
                setNewUser(0);
                String auth_status = u.getGoodreadsAuthStatus();
                int gFlag = 1;   // flag to determine whether to show the good reads login page

                if (auth_status == "yes" )
                {
                    gFlag = 1;
                } else if (auth_status == "no")
                {
                    gFlag = 0;
                } else if (auth_status == "done") {
                    gFlag = 0;
                }
                System.out.print("The auth status is ...."+auth_status);
                if (gFlag == 1)  // show the good reads page
                {
                  //  Intent intent = new Intent(context, GoodReadsLogin.class);

                    Intent intent = new Intent(context, FindOwnedBooks.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else {        // show the books app page
                    Intent intent = new Intent(context, MyBooks.class);
                    context.startActivity(intent);
                }

            }
        }
    }
    private class HttpRequestTaskPost extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... params) {
            String url = "http://117.96.1.204:8389/neo4j/v1/users/";
            System.out.print("in creating user!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            // create the user
            User u1 = new User(fbUserName,fbUserEmail,fbUserId);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            User result = restTemplate.postForObject(url,u1,User.class);

            return result;
        }


        @Override
        protected void onPostExecute(User u) {
            // save user details in global config
            UserInfo u2 = UserInfo.getInstance();
            u2.copy(u);
            setNewUser(1);
            Intent i = new Intent(FbLogin.this, GoodReadsLogin.class);
            startActivity(i);
        }
    }
}


