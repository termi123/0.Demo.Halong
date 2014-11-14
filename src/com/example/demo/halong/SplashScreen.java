package com.example.demo.halong;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.bean.CategoriesBean;
import com.example.demo.bean.PostBean;
import com.example.demo.bean.SyncBean;
import com.example.demo.core.DBController;
import com.example.demo.util.Constants;

public class SplashScreen extends Activity {

	String ID = "";
	String Address = "";
	String IMG = "";
	String Map = "";
	String Phone = "";
	String Price = "";
	String Rate = "";
	String Time = "";
	// private TextView skipLoginButton;
	// private SkipLoginCallback skipLoginCallback;

	HashMap<String, String> queryValues;
	private static int SPLASH_TIME_OUT = 1000;
	DBController controller = new DBController(this);

	// private final String PENDING_ACTION_BUNDLE_KEY =
	// "com.facebook.samples.hellofacebook:PendingAction";
	// private PendingAction pendingAction = PendingAction.NONE;
	// private LoginButton loginButton;
	// private GraphUser user;
	// private GraphPlace place;
	// private ProfilePictureView profilePictureView;
	// private TextView greeting;
	// private ViewGroup controlsContainer;
	// private List<GraphUser> tags;
	// private boolean canPresentShareDialog;
	// private boolean canPresentShareDialogWithPhotos;

	public interface SkipLoginCallback {
		void onSkipLoginPressed(SkipLoginCallback skipLoginCallback);
	}

	@Override
	public void onStart() {
		super.onStart();
		init();
	}

	// private enum PendingAction {
	// NONE, POST_PHOTO, POST_STATUS_UPDATE
	// }
	//
	// private UiLifecycleHelper uiHelper;
	//
	// private Session.StatusCallback callback = new Session.StatusCallback() {
	// @Override
	// public void call(Session session, SessionState state,
	// Exception exception) {
	// onSessionStateChange(session, state, exception);
	// }
	// };
	//
	// private FacebookDialog.Callback dialogCallback = new
	// FacebookDialog.Callback() {
	// @Override
	// public void onError(FacebookDialog.PendingCall pendingCall,
	// Exception error, Bundle data) {
	// Handler();
	// Log.i("HelloFacebook", String.format("Error: %s", error.toString()));
	// }
	//
	// @Override
	// public void onComplete(FacebookDialog.PendingCall pendingCall,
	// Bundle data) {
	// // Handler();
	// Intent itMain = new Intent(SplashScreen.this,MainActivity.class);
	// itMain.putExtra("id", user.getId());
	// startActivity(itMain);
	//
	// Log.i("HelloFacebook", "Success!");
	// }
	// };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// uiHelper = new UiLifecycleHelper(this, callback);
		// uiHelper.onCreate(savedInstanceState);
		//
		// if (savedInstanceState != null) {
		// String name = savedInstanceState
		// .getString(PENDING_ACTION_BUNDLE_KEY);
		// pendingAction = PendingAction.valueOf(name);
		// }
		setContentView(R.layout.activity_splash);

		// try {
		// PackageInfo info = getPackageManager().getPackageInfo(
		// "com.example.demo.halong", PackageManager.GET_SIGNATURES);
		// for (Signature signature : info.signatures) {
		// MessageDigest md = MessageDigest.getInstance("SHA");
		// md.update(signature.toByteArray());
		// Log.e("MY KEY HASH:",
		// Base64.encodeToString(md.digest(), Base64.DEFAULT));
		// }
		// } catch (NameNotFoundException e) {
		//
		// } catch (NoSuchAlgorithmException e) {
		//
		// }
		//
		// skipLoginButton = (TextView)
		// this.findViewById(R.id.skip_login_button);
		// skipLoginButton.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View view) {
		// Handler();
		// }
		// });
		//
		// profilePictureView = (ProfilePictureView)
		// findViewById(R.id.profilePicture);
		//
		// loginButton = (LoginButton) findViewById(R.id.login_button);
		// loginButton
		// .setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback()
		// {
		// @Override
		// public void onUserInfoFetched(GraphUser user) {
		// SplashScreen.this.user = user;
		// if (user != null) {
		// // Log.i("user Login", user.getId());
		// Intent itMain = new Intent(SplashScreen.this,MainActivity.class);
		// itMain.putExtra("id", user.getId());
		// startActivity(itMain);
		// Log.i("HelloFacebook", "Success!");
		// }
		// // updateUI();
		// }
		// });
	}

	@Override
	protected void onResume() {
		super.onResume();
		// uiHelper.onResume();
		// updateUI();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// uiHelper.onSaveInstanceState(outState);
		//
		// outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// uiHelper.onActivityResult(requestCode, resultCode, data,
		// dialogCallback);
	}

	@Override
	public void onPause() {
		super.onPause();
		// uiHelper.onPause();

		// Call the 'deactivateApp' method to log an app event for use in
		// analytics and advertising
		// reporting. Do so in the onPause methods of the primary Activities
		// that an app may be launched into.
		// AppEventsLogger.deactivateApp(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// uiHelper.onDestroy();
	}

	// private void onSessionStateChange(Session session, SessionState state,
	// Exception exception) {
	// if (pendingAction != PendingAction.NONE
	// && (exception instanceof FacebookOperationCanceledException || exception
	// instanceof FacebookAuthorizationException)) {
	// // new AlertDialog.Builder(SplashScreen.this)
	// // .setTitle(R.string.cancelled)
	// // .setMessage(R.string.permission_not_granted)
	// // .setPositiveButton(R.string.ok, null).show();
	// // pendingAction = PendingAction.NONE;
	// // Log.i("Login", "No");
	// } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
	// // handlePendingAction();
	// // Log.i("Login", "Yes");
	// }
	// // updateUI();
	// }

	// protected void updateUI() {
	// if (user != null) {
	// // Log.i("user update Ui", user.getId());
	// // profilePictureView.setProfileId(user.getId());
	// }
	// }

	// public void setSkipLoginCallback(SkipLoginCallback callback) {
	// skipLoginCallback = callback;
	// }

	private void init() {

		if (hasConnection()) {
			List<SyncBean> SyncList = controller.getAllSync();

			if (SyncList.isEmpty()) {
				Log.i("SyncList", "Empty");
				new DLCate1All().execute((Void) null);
				new DLPostAll().execute((Void) null);
				new DLMetaAll().execute((Void) null);
			} else {
				Log.i("SyncList", "Not Empty");
				new DLCate1Unsync().execute((Void) null);
				new DLPostUnsync().execute((Void) null);
				new DLMetaUnsync().execute((Void) null);
				// new DLPostUpdate().execute((Void) null);
				// new DLMetaUpdate().execute((Void) null);
			}

			// Logall("Sync");
			// Logall("Cate");
			Logall("Post");
			// Logall("Content");
			Handler();
		} else {
			Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG)
					.show();
			Handler();
		}
	}

	public void Handler() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

	private boolean hasConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}

	// ////////////////////////////////////Log//////////////////////////////////////

	public void Logall(String get) {
		switch (get) {
		case "Cate":
			// ////////////////////////////////////Test Get Cate
			List<CategoriesBean> CateList = controller.getAllCate();
			for (CategoriesBean cn : CateList) {
				String log = "ID:" + cn.get_ID() + " Name: " + cn.get_Name()
						+ " Date: " + cn.get_DateSync();
				Log.i("Log Cate: ", log);

			}
			break;
		case "Sync":
			// ////////////////////////////////////Test Get SyncDate
			List<SyncBean> SyncList = controller.getAllSync();
			for (SyncBean cn : SyncList) {
				String log = "ID:" + cn.get_ID() + " Name: "
						+ cn.get_CateName() + " Date: " + cn.get_DateSync();
				Log.i("Log SyncList: ", log);
			}
			break;
		case "Postq":
			// ////////////////////////////////////Test Get Post
			List<PostBean> postquery = controller.getPostsByCate("3229", true);
			for (PostBean p : postquery) {
				String log = "ID:" + p.get_ID() + " Cate ID: "
						+ p.get_Cate_ID() + " Title: " + p.get_TITLE()
						+ " Content: " + p.get_CONTENT()
				// + " Add: "
				// + p.get_ADDRESS() + " Price: " + p.get_PRICE()
				// + " Time: " + p.get_TIME() + " Rate: " + p.get_RATE()
				// + " IMG: " + p.get_IMG() + " MAp: " + p.get_MAP()
				;
				Log.i("Log PostQuery: ", log);
			}
			break;
		case "Post":
			List<PostBean> postlist = controller.getAllPost();
			for (PostBean p : postlist) {
				String log = "ID:" + p.get_ID() + " Cate ID: "
						+ p.get_Cate_ID()
						+ " Title: "
						+ p.get_TITLE()
						// + " Content: " + p.get_CONTENT()
						+ " Add: " + p.get_ADDRESS() + " Price: "
						+ p.get_PRICE() + " Time: " + p.get_TIME() + " Rate: "
						+ p.get_RATE() + " IMG: " + p.get_IMG() + " MAp: "
						+ p.get_MAP();
				Log.i("Log PostList: ", log);
			}
			break;
		case "Content":
			List<PostBean> postlistContent = controller.getAllPost();
			String a = "";
			for (PostBean p : postlistContent) {
				String log = " Content: " + p.get_CONTENT();
				a = a.concat(p.get_CONTENT());
				// int i = 0;
				// Log.i("Log PostList"+i, log);
				// i++;
			}
			Log.i("Log PostList a", a);
			break;
		default:
			Log.i("Logall: ", "???");
		}
	}

	// ////////////////////////////////////Cate//////////////////////////////////////
	class DLCate1All extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			// prgDialog.setMessage("Progress start");
			// prgDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			Log.i("Start DLCate1All", "Start DLCate1All");
			StringBuilder builder = new StringBuilder();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(Constants.DLCate1All);
			try {
				HttpResponse response = httpclient.execute(httpget);
				//
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					insertCate(builder.toString());

				} else if (statusCode == 404) {
					Toast.makeText(SplashScreen.this, "404", Toast.LENGTH_SHORT)
							.show();
					Log.i("Connect Err", "404");
					Handler();
				} else if (statusCode == 500) {
					Toast.makeText(SplashScreen.this, "500", Toast.LENGTH_SHORT)
							.show();
					Log.i("Connect Err", "500");
					Handler();
				} else {
					Toast.makeText(SplashScreen.this, "Error occured!",
							Toast.LENGTH_SHORT).show();
					Log.i("Connect Err", "Error occured!");
					Handler();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(final Boolean success) {
			// if (prgDialog.isShowing()) {
			// prgDialog.dismiss();
			// }
			// new DLPostAll().execute((Void) null);
			AddSyncTime("Cate");
		}
	}

	class DLCate1Unsync extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			// prgDialog.setMessage("Progress start");
			// prgDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i("Start DLCate1 Unsync", "DLCate1 Unsync");

			StringBuilder builder = new StringBuilder();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.DLCate1Unsync);
			try {
				List<SyncBean> SyncList = controller.getDateSync("1");
				String datesync = "";
				for (SyncBean cn : SyncList) {
					datesync = cn.get_DateSync();
				}
				Log.i("datesync cate:", datesync);
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("datesync", datesync));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				//
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();

				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					if (builder.length() > 2) {
						Log.i("Cate new", "YUP");
						insertCate(builder.toString());

					} else {
						Log.i("Cate new", "NULL");
					}
				} else {

				}

			} catch (ClientProtocolException e) {
				Log.i("ClientProtocolException", e.toString());
			} catch (IOException e) {
				Log.i("IOException", e.toString());
			}
			return null;
		}

		protected void onPostExecute(final Boolean success) {
			// if (prgDialog.isShowing()) {
			// prgDialog.dismiss();
			// }
			UpdateSyncTime("Cate");
		}
	}

	public void insertCate(String response) {
		Log.i("Updating", "Updating...");

		CategoriesBean cate;
		try {
			JSONArray arr = new JSONArray(response);
			if (arr.length() != 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = (JSONObject) arr.get(i);
					cate = new CategoriesBean(obj.get("term_id").toString(),
							obj.get("name").toString(), obj.get("date_add")
									.toString());
					controller.insertCate(cate);
				}
				Logall("Cate");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// ////////////////////////////////////Post//////////////////////////////////////
	class DLPostAll extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			// prgDialog.setMessage("Progress start");
			// prgDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			Log.i("Start DLPostAll", "Start DLPostAll");
			StringBuilder builder1 = new StringBuilder();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(Constants.DLPostAll);
			try {
				HttpResponse response = httpclient.execute(httpget);
				//
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder1.append(line);
					}
					insertPost(builder1.toString());
					// Log.i("builder1 Post", builder1.toString());
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(final Boolean success) {
			// if (prgDialog.isShowing()) {
			// prgDialog.dismiss();
			// }
		}
	}

	class DLPostUnsync extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			// prgDialog.setMessage("Progress start");
			// prgDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i("Start DLPost Unsync", "DLPost Unsync");

			StringBuilder builder1 = new StringBuilder();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.DLPostUnsync);
			try {

				List<SyncBean> SyncList = controller.getDateSync("2");
				String datesync = "";
				for (SyncBean cn : SyncList) {
					datesync = cn.get_DateSync();
				}
				Log.i("datesync post", datesync);
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("datesync", datesync));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				//
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();

				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder1.append(line);
					}
					Log.i("builder1 post", builder1.toString());
					insertPost(builder1.toString());
				}
			} catch (ClientProtocolException e) {
				Log.i("ClientProtocolException", e.toString());
			} catch (IOException e) {
				Log.i("IOException", e.toString());
			}

			return null;
		}

		protected void onPostExecute(final Boolean success) {
			// if (prgDialog.isShowing()) {
			// prgDialog.dismiss();
			// }
		}
	}

	class DLPostUpdate extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			// prgDialog.setMessage("Progress start");
			// prgDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i("Start DLPost Unsync", "DLPost Unsync");

			StringBuilder builder1 = new StringBuilder();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.DLPostUpdate);
			try {

				List<SyncBean> SyncList = controller.getDateSync("2");
				String datesync = "";
				for (SyncBean cn : SyncList) {
					datesync = cn.get_DateSync();
				}
				Log.i("datesync post", datesync);
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("datesync", datesync));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				//
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();

				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder1.append(line);
					}
					Log.i("builder1 post", builder1.toString());
					// insertPost(builder.toString());
				}
			} catch (ClientProtocolException e) {
				Log.i("ClientProtocolException", e.toString());
			} catch (IOException e) {
				Log.i("IOException", e.toString());
			}

			return null;
		}

		protected void onPostExecute(final Boolean success) {
			// if (prgDialog.isShowing()) {
			// prgDialog.dismiss();
			// }
		}
	}

	public void insertPost(String response1) {
		Log.i("Updating", "Updating...");
		PostBean post;
		try {
			JSONArray arr = new JSONArray(response1);
			if (arr.length() != 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = (JSONObject) arr.get(i);
					post = new PostBean(obj.get("ID").toString(), obj.get(
							"term_id").toString(), obj.get("post_title")
							.toString(), obj.get("post_content").toString(),
							"", "", "", "", "", "", "");
					controller.insertPost(post);
					// Logall("Post");
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// ////////////////////////////////////Meta//////////////////////////////////////

	class DLMetaAll extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i("Start DLMetaAll", "Start DLMetaAll");
			StringBuilder builder1 = new StringBuilder();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(Constants.DLMetaAll);
			try {
				HttpResponse response = httpclient.execute(httpget);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder1.append(line);
					}
					// Log.i("builder Meta", builder1.toString());
					insertMeta(builder1.toString());

				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(final Boolean success) {
			// if (prgDialog.isShowing()) {
			// prgDialog.dismiss();
			// }
			// new DLPostAll().execute((Void) null);
			AddSyncTime("Post");
		}
	}

	class DLMetaUnsync extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i("Start DLMeta Unsync", "DLMeta Unsync");

			StringBuilder builder = new StringBuilder();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.DLMetaUnsync);
			try {
				List<SyncBean> SyncList = controller.getDateSync("2");
				String datesync = "";
				for (SyncBean cn : SyncList) {
					datesync = cn.get_DateSync();
				}
				Log.i("datesync meta:", datesync);
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("datesync", datesync));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				//
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();

				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					if (builder.length() > 2) {
						Log.i("Meta new", "YUP");
						insertMeta(builder.toString());

					} else {
						Log.i("Meta new", "NULL");
					}
				} else {

				}

			} catch (ClientProtocolException e) {
				Log.i("ClientProtocolException", e.toString());
			} catch (IOException e) {
				Log.i("IOException", e.toString());
			}
			return null;
		}

		protected void onPostExecute(final Boolean success) {
			// if (prgDialog.isShowing()) {
			// prgDialog.dismiss();
			// }
			UpdateSyncTime("Post");
		}

	}

	class DLMetaUpdate extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i("Start DLMeta Unsync", "DLMeta Unsync");

			StringBuilder builder = new StringBuilder();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.DLMetaUpdate);
			try {
				List<SyncBean> SyncList = controller.getDateSync("2");
				String datesync = "";
				for (SyncBean cn : SyncList) {
					datesync = cn.get_DateSync();
				}
				Log.i("datesync meta:", datesync);
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("datesync", datesync));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				//
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();

				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					if (builder.length() > 2) {
						Log.i("Meta new", "YUP");
						insertMeta(builder.toString());

					} else {
						Log.i("Meta new", "NULL");
					}
				} else {

				}

			} catch (ClientProtocolException e) {
				Log.i("ClientProtocolException", e.toString());
			} catch (IOException e) {
				Log.i("IOException", e.toString());
			}
			return null;
		}

		protected void onPostExecute(final Boolean success) {
			// if (prgDialog.isShowing()) {
			// prgDialog.dismiss();
			// }
			UpdateSyncTime("Post");
		}

	}

	private void insertMeta(String response) {
		Log.i("Updating", "Updating...");

		try {
			JSONArray arr = new JSONArray(response);
			if (arr.length() != 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = (JSONObject) arr.get(i);
					// Log.i("OBJ", obj.toString());

					if (obj.get("meta_key").toString().equals("Address")) {
						// Log.i("Address", obj.get("meta_value").toString());
						Address = obj.get("meta_value").toString();
						ID = obj.get("post_id").toString();

						queryValues = new HashMap<String, String>();
						queryValues.put("ID", ID);
						queryValues.put("Address", Address);
						controller.insertMetaAdd(queryValues);

					} else if (obj.get("meta_key").toString().equals("IMG")) {
						// Log.i("IMG After", obj.get("meta_value").toString());
						IMG = obj.get("meta_value").toString();
						new DLImage().execute(IMG);
						IMG = getId(IMG);
						// Log.i("IMG Before", IMG);
						ID = obj.get("post_id").toString();

						queryValues = new HashMap<String, String>();
						queryValues.put("ID", ID);
						queryValues.put("IMG", IMG);
						controller.insertMetaIMG(queryValues);

					} else if (obj.get("meta_key").toString().equals("Map")) {
						// Log.i("Map", obj.get("meta_value").toString());
						Map = obj.get("meta_value").toString();
						ID = obj.get("post_id").toString();

						queryValues = new HashMap<String, String>();
						queryValues.put("ID", ID);
						queryValues.put("Map", Map);
						controller.insertMetaMap(queryValues);
					} else if (obj.get("meta_key").toString().equals("Time")) {
						// Log.i("Time", obj.get("meta_value").toString());
						Time = obj.get("meta_value").toString();
						ID = obj.get("post_id").toString();

						HashMap<String, String> queryValues1 = new HashMap<String, String>();
						queryValues1.put("ID", ID);
						queryValues1.put("Time", Time);
						controller.insertMetaTime(queryValues1);
					} else if (obj.get("meta_key").toString().equals("Phone")) {
						// Log.i("Phone", obj.get("meta_value").toString());
						Phone = obj.get("meta_value").toString();
						ID = obj.get("post_id").toString();

						queryValues = new HashMap<String, String>();
						queryValues.put("ID", ID);
						queryValues.put("Phone", Phone);
						controller.insertMetaPhone(queryValues);
					} else if (obj.get("meta_key").toString().equals("Price")) {
						// Log.i("Price", obj.get("meta_value").toString());
						Price = obj.get("meta_value").toString();
						ID = obj.get("post_id").toString();

						queryValues = new HashMap<String, String>();
						queryValues.put("ID", ID);
						queryValues.put("Price", Price);
						controller.insertMetaPrice(queryValues);
					} else if (obj.get("meta_key").toString()
							.equals("_et_boutique_user_rating")) {
						// Log.i("Rate", obj.get("meta_value").toString());
						Rate = obj.get("meta_value").toString();
						ID = obj.get("post_id").toString();

						queryValues = new HashMap<String, String>();
						queryValues.put("ID", ID);
						queryValues.put("Rate", Rate);
						controller.insertMetaRate(queryValues);
					}
				}
				// Logall("Post");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// ////////////////////////////////////FILE//////////////////////////////////////

	class DLImage extends AsyncTask<String, Void, Void> {

		protected Void doInBackground(String... params) {
			try {
				String a = params[0];
				File dir = getCacheDir(getApplicationContext());
				File myFile = new File(dir, getFileImagePage(a));
				saveInStorage(myFile, a);
				Log.i("Save", "Done");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	public String getId(String url) {
		int idxStart = url.lastIndexOf("/");
		// Log.i("url.substring(idxStart)", url.substring(idxStart));
		return url.substring(idxStart);
	}

	public String getFileImagePage(String url) {
		// Log.i("url", url);
		return getId(url);
	}

	public static File getCacheDir(Context context) {
		File file = null;
		SharedPreferences prefs = PreferenceManager

		.getDefaultSharedPreferences(context);
		String settingDir = prefs.getString("dir_download", "0");
		if (settingDir.equals(Constants.EXTERNAL_STORAGE + "")) {
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				file = new File(Environment.getExternalStorageDirectory()
						+ Constants.CACHE_DIRECTORY);
				boolean success = true;
				if (!file.exists()) {
					success = file.mkdirs();
				}

				if (!success)
					file = null;
			}
		}
		if (file == null)
			try {
				file = new File(Environment.getRootDirectory()
						+ Constants.CACHE_DIRECTORY);
			} catch (Exception e) {
				file = context.getDir("", Context.MODE_WORLD_WRITEABLE);
				file = new File(file, Constants.CACHE_DIRECTORY);
			}

		if (!file.exists()) {
			if (!file.mkdirs()) {
				file = context.getDir("", Context.MODE_WORLD_WRITEABLE);
				file = new File(file, Constants.CACHE_DIRECTORY);
				if (!file.exists())
					file.mkdirs();
			}
		}
		return file;
	}

	public static void saveInStorage(File file, String imageUrl)
			throws Exception {

		imageUrl = escapeURL(imageUrl);

		OutputStream output = null;
		InputStream input = null;

		try {
			if (!file.exists()) {
				URL url = new URL(imageUrl);
				Log.i("imageUrl", imageUrl);
				Log.i("url", url.toString());
				URLConnection connection = url.openConnection();
				connection.connect();

				input = new BufferedInputStream(url.openStream());

				output = new FileOutputStream(file);

				byte data[] = new byte[1024];
				int count;
				while ((count = input.read(data)) != -1) {
					output.write(data, 0, count);
				}
				output.flush();
			}
		} catch (Exception e) {
			if (file.exists()) {
				file.delete();
			}
			throw e;
		} finally {
			if (output != null) {
				output.close();
			}
			if (input != null) {
				input.close();
			}
		}
	}

	public static String escapeURL(String link) {
		try {
			String path = link;
			path = java.net.URLEncoder.encode(path, "utf8");
			path = path.replace("%3A", ":");
			path = path.replace("%2F", "/");
			path = path.replace("+", "%20");
			path = path.replace("%23", "#");
			path = path.replace("%3D", "=");
			return path;
		} catch (Exception e) {
			link = link.replaceAll("\\[", "%5B");
			link = link.replaceAll("\\]", "%5D");
			link = link.replaceAll("\\s", "%20");
		}
		return link;
	}

	// ////////////////////////////////////SyncTime//////////////////////////////////////
	private void AddSyncTime(String option) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Date currentLocalTime = cal.getTime();
		DateFormat date = new SimpleDateFormat("yyy-MM-dd HH:mm:ss z");
		date.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		String localTime = date.format(currentLocalTime);

		if (option == "Cate") {
			String[] Id = { "1" };
			String[] Cate = { "Cate1" };
			String[] Syncdate = { localTime };
			SyncBean Sync;
			for (int i = 0; i < 1; i++) {
				Sync = new SyncBean(Id[i], Cate[i], Syncdate[i]);
				controller.insertSync(Sync);
			}
		} else if (option == "Post") {
			String[] Id = { "2" };
			String[] Cate = { "Post" };
			String[] Syncdate = { localTime };
			SyncBean Sync;
			for (int i = 0; i < 1; i++) {
				Sync = new SyncBean(Id[i], Cate[i], Syncdate[i]);
				controller.insertSync(Sync);
			}
		}
		Logall("Sync");
		// Handler();
	}

	private void UpdateSyncTime(String option) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Date currentLocalTime = cal.getTime();
		DateFormat date = new SimpleDateFormat("yyy-MM-dd HH:mm:ss z");
		date.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		String localTime = date.format(currentLocalTime);

		if (option == "Cate") {
			controller.updateSync("1", localTime);
		} else if (option == "Post") {
			controller.updateSync("2", localTime);
		}
		// Handler();
	}

}
