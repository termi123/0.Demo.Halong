package com.example.demo.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONObject;

import android.R.color;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.demo.adapter.DirectionsJSONParser;
import com.example.demo.bean.PostBean;
import com.example.demo.core.DBController;
import com.example.demo.halong.MainActivity;
import com.example.demo.halong.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapDetailFragment extends SherlockFragment implements
		LocationListener, OnMarkerClickListener {

	private MainActivity mMainActivity;
	private View mFormView;
	private View mStatusView;
	private View view;
	GoogleMap mGoogleMap;
	ArrayList<LatLng> mMarkerPoints;
	double mLatitude = 0;
	double mLongitude = 0;
	DBController controller;
	TextView tvDistanceDuration, tvAddress;
	List<PostBean> postquery;

	public void setMainActivity(MainActivity mainActivity) {
		mMainActivity = mainActivity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		controller = new DBController(getActivity());
	}

	public void onStart() {
		super.onStart();
		loadPage();
		showProgress(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_map, container, false);

		mFormView = view.findViewById(R.id.view_form);
		mStatusView = view.findViewById(R.id.view_status);
		tvDistanceDuration = (TextView) view
				.findViewById(R.id.tv_distance_time);
		tvAddress = (TextView) view.findViewById(R.id.tv_add);
		Bundle bundle = getArguments();
		
		if(bundle.getString("add")!=null){
		tvAddress.setText("Address: " + bundle.getString("add"));
		}
//		tvAddress.setTextColor(Color.BLUE);
		return view;
	}

	public void loadPage() {
		Bundle bundle = getArguments();
		bundle.getString("type");

		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());

		if (status != ConnectionResult.SUCCESS) { // Google Play Services
													// are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,
					getActivity(), requestCode);
			dialog.show();

		} else { // Google Play Services are available

			// Initializing
			mMarkerPoints = new ArrayList<LatLng>();

			// Getting reference to SupportMapFragment of the activity_main
			SupportMapFragment fm = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.map);

			// Getting Map for the SupportMapFragment
			mGoogleMap = fm.getMap();

			// Enable MyLocation Button in the Map
			mGoogleMap.setMyLocationEnabled(true);
			mGoogleMap.getUiSettings().setCompassEnabled(true);
			mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
			mGoogleMap.setOnMarkerClickListener(this);
			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			@SuppressWarnings("static-access")
			LocationManager locationManager = (LocationManager) getActivity()
					.getSystemService(getActivity().LOCATION_SERVICE);
			if (!locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				buildAlertMessageNoGps();
			} else {

				// Creating a criteria object to retrieve provider
				Criteria criteria = new Criteria();

				// Getting the name of the best provider
				String provider = locationManager.getBestProvider(criteria,
						true);

				// Getting Current Location From GPS
				Location location = locationManager
						.getLastKnownLocation(provider);

				if (location != null) {
					onLocationChanged(location);
				}

				locationManager
						.requestLocationUpdates(provider, 20000, 0, this);

				switch (bundle.getString("type")) {

				case "0":
					StringTokenizer loc = new StringTokenizer(
							bundle.getString("map"), ",");
					Double x = Double.parseDouble(loc.nextToken());
					Double y = Double.parseDouble(loc.nextToken());
					LatLng lat = new LatLng(x, y);
					drawMarker(lat);

					float[] distances = new float[1];
					Log.i("location", location.getLatitude() + "," + location.getLongitude());
					Location.distanceBetween(location.getLatitude(),
							location.getLongitude(), lat.latitude,
							lat.longitude, distances);
					int dur = Math.round(distances[0] / 1000 / 50);
					Log.i("dur", Integer.toString(dur));
					if (dur > 0) {
						tvDistanceDuration.setText("Distance:"
								+ Math.round(distances[0] / 1000) + "Km, "
								+ "Duration:"
								+ Math.round(distances[0] / 1000 / 50) + "h");
					} else {
						tvDistanceDuration.setText("Distance:"
								+ Math.round(distances[0] / 1000) + "Km ");
					}

//					tvDistanceDuration.setTextColor(Color.RED);
					// Checks, whether start and end locations are captured
					if (mMarkerPoints.size() >= 2) {
						LatLng origin = mMarkerPoints.get(0);
						LatLng dest = mMarkerPoints.get(1);

						// Getting URL to the Google Directions API
						String url = getDirectionsUrl(origin, dest);

						DownloadTask downloadTask = new DownloadTask();

						// Start downloading json data from Google Directions
						// API
						downloadTask.execute(url);
					}

					// });

					break;
				case "1":
					final List<PostBean> postquery = controller.getAllPost();

					if (postquery.size() != 0) {
						for (int i = 0; i < postquery.size(); i++) {
							PostBean s = (PostBean) postquery.get(i);
							StringTokenizer locat = new StringTokenizer(
									s.get_MAP(), ",");
							Log.i("locat", s.get_MAP());
							Double dx = Double.parseDouble(locat.nextToken());
							Double dy = Double.parseDouble(locat.nextToken());
							LatLng letL = new LatLng(dx, dy);
							// 20.95,107.04
							mMarkerPoints.add(letL);
							MarkerOptions options = new MarkerOptions();
							options.position(letL);
							options.title(s.get_TITLE());
							options.snippet(s.get_ADDRESS());
							options.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_RED));
							mGoogleMap.addMarker(options);
						}
					}

					break;
				default:
					break;
				}
			}
		}
	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				getActivity());
		builder.setMessage(
				"Your GPS seems to be disabled, do you want to enable it?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(
									@SuppressWarnings("unused") final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							@SuppressWarnings("unused") final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;

			Log.i("result", result.toString());
			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);
				// Log.i("path", path.toString());
				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					// if (j == 0) { // Get distance from the list
					// // Log.i("distance", (String) point.get("distance"));
					// // distance = (String) point.get("distance");
					// continue;
					// } else if (j == 1) { // Get duration from the list
					// // Log.i("duration", (String) point.get("duration"));
					// duration = (String) point.get("duration");
					// continue;
					// }
					// Log.i("duration", point.get("duration")).get("text"));
					// ((JSONObject)path.get(j)).get("duration")).get("text").toString();
					// Log.i("Point", point.toString());

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(8);
				lineOptions.color(Color.RED);

			}
			// tvDistanceDuration.setText("Distance:" + distance +
			// ", Duration:"+duration);
			// Drawing polyline in the Google Map for the i-th route
			mGoogleMap.addPolyline(lineOptions);

		}
	}

	private void drawMarker(LatLng point) {
		mMarkerPoints.add(point);

		// Creating MarkerOptions
		MarkerOptions options = new MarkerOptions();

		// Setting the position of the marker
		options.position(point);

		/**
		 * For the start location, the color of marker is GREEN and for the end
		 * location, the color of marker is RED.
		 */
		if (mMarkerPoints.size() == 1) {
			options.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		} else if (mMarkerPoints.size() == 2) {
			options.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		}

		// Add new marker to the Google Map Android API V2
		mGoogleMap.addMarker(options);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@SuppressLint("NewApi")
	public void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mStatusView.setVisibility(View.VISIBLE);
			mStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@SuppressLint("NewApi")
						@Override
						public void onAnimationEnd(Animator animation) {
							mStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mFormView.setVisibility(View.VISIBLE);
			mFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		if (mMarkerPoints.size() < 2) {

			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
			LatLng point = new LatLng(mLatitude, mLongitude);

			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
			mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14));

			drawMarker(point);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		Log.i("getPosition: ", arg0.getPosition().toString());

		tvAddress.setText("Address: " + arg0.getSnippet());
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(getActivity().LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);
		//
		// String url = getDirectionsUrl(new LatLng(location.getLatitude(),
		// location.getLongitude())
		// ,new LatLng(arg0.getPosition().latitude,
		// arg0.getPosition().longitude));
		// DownloadTask downloadTask = new DownloadTask();
		// downloadTask.execute(url);

		float[] distances = new float[1];
		Location.distanceBetween(location.getLatitude(),
				location.getLongitude(), arg0.getPosition().latitude,
				arg0.getPosition().longitude, distances);

		int dur = Math.round(distances[0] / 1000 / 50);
		Log.i("dur", Integer.toString(dur));
		if (dur > 0) {
			tvDistanceDuration.setText("Distance:"
					+ Math.round(distances[0] / 1000) + "Km, " + "Duration:"
					+ Math.round(distances[0] / 1000 / 50) + "h");
		} else {
			tvDistanceDuration.setText("Distance:"
					+ Math.round(distances[0] / 1000) + "Km ");
		}
		tvDistanceDuration.setTextColor(Color.RED);
		return false;
	}
}
