package com.example.demo.fragment;

import java.io.File;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.demo.core.DBController;
import com.example.demo.halong.MainActivity;
import com.example.demo.halong.R;
import com.example.demo.util.Constants;
import com.google.android.gms.maps.GoogleMap;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class PostDetailFragment extends SherlockFragment implements
		OnRatingBarChangeListener {

	private MainActivity mMainActivity;
	private View mFormView;
	private View mStatusView;
	private View view;
	GoogleMap map;
	DBController controller;

	String Add = "";
	String Time = "";
	String Rate = "";
	String Price = "";
	String IMG = "";
	String Phone = "";
	String Map = "";
	RatingBar ratingBar;

	public void setMainActivity(MainActivity mainActivity) {
		mMainActivity = mainActivity;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		controller = new DBController(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void onStart() {
		super.onStart();
		loadPage();
		showProgress(false);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.fragment_post_detail, container, false);

		mFormView = view.findViewById(R.id.view_form);
		mStatusView = view.findViewById(R.id.view_status);

		ratingBar = (RatingBar) view.findViewById(R.id.ratingBar1);
		ratingBar.setOnRatingBarChangeListener(this);

		return view;
	}

	private void loadPage() {
		TextView tvAddtt, tvAdd, tvDes, tvTime, tvPrice, tvContent;
		ImageView ivTitle;
		final Bundle bundle = getArguments();

		// IMAGE
		IMG = bundle.getString("image");
		ivTitle = (ImageView) view.findViewById(R.id.ivTitle);
		File imgFile = new File(Environment.getExternalStorageDirectory()
				+ Constants.CACHE_DIRECTORY + "/" + IMG);
		if (imgFile.exists()) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 1;
			Bitmap myBitmap = BitmapFactory.decodeFile(
					imgFile.getAbsolutePath(), options);

			ivTitle.setImageBitmap(myBitmap);
		} else {
			Log.i("imgFile", "NOT exists");
		}

		// Rate
		Rate = bundle.getString("rate");
		Log.i("Rate", Rate);
		if (!Rate.equals("0")) {
			float star = Float.parseFloat(Rate);
			ratingBar.setRating(star);
		} else {
			ratingBar.setRating(3);
		}

		// Add to Fav
		final ImageButton imb = (ImageButton) view
				.findViewById(R.id.ibdownload);

		if (bundle.getInt("fav") == 0) {
			imb.setImageResource(R.drawable.rating_important);
			imb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					controller.downloadPost(bundle.getString("id"), 1);
					imb.setImageResource(R.drawable.rating_important_inverse);
					Toast.makeText(
							getActivity(),
							"'" + bundle.getString("title") + "'"
									+ " was saved in your Favorites",
							Toast.LENGTH_LONG).show();
				}
			});
		} else if (bundle.getInt("fav") == 1) {
			imb.setImageResource(R.drawable.rating_important_inverse);
			imb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					controller.downloadPost(bundle.getString("id"), 0);
					imb.setImageResource(R.drawable.rating_important);
					Toast.makeText(
							getActivity(),
							"'" + bundle.getString("title") + "'"
									+ " was removed in your Favorites",
							Toast.LENGTH_LONG).show();
				}
			});
		}

		// ADD
		Add = bundle.getString("add");
		tvAdd = (TextView) view.findViewById(R.id.tvAdd);
		tvAddtt = (TextView) view.findViewById(R.id.tvAddtt);

		if (!Add.equals("")) {
			tvAdd.setText(Add);
		} else {
			tvAdd.setVisibility(View.GONE);
			tvAddtt.setVisibility(View.GONE);
		}

		tvDes = (TextView) view.findViewById(R.id.tvDes);
		if (Time.equals("") && Price.equals("")) {
			tvDes.setVisibility(View.GONE);
		}

		// TIME
		Time = bundle.getString("time");
		tvTime = (TextView) view.findViewById(R.id.tvTime);

		if (!Time.equals("")) {
			tvTime.setText("Time: " + Time);
		} else {
			tvTime.setVisibility(View.GONE);
		}

		// PRICE
		Price = bundle.getString("price");
		tvPrice = (TextView) view.findViewById(R.id.tvPrice);
		if (!Price.equals("")) {
			tvPrice.setText("Price: " + Price);
		} else {
			tvPrice.setVisibility(View.GONE);
		}

		// Phone
		Phone = bundle.getString("phone");
		TextView txtPhone = (TextView) getActivity().findViewById(R.id.tvPhone);
		LinearLayout llPhone = (LinearLayout) getActivity().findViewById(
				R.id.llPhone);
		if (!Phone.equals("")) {
			llPhone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(android.content.Intent.ACTION_DIAL,
							Uri.parse("tel:" + Phone));
					startActivity(i);
				}
			});

			txtPhone.setText("Phone Number: " + Phone);
		} else {
			llPhone.setVisibility(View.GONE);
			txtPhone.setVisibility(View.GONE);
		}

		// MAP
		Map = bundle.getString("map");
		LinearLayout llMap = (LinearLayout) getActivity().findViewById(
				R.id.llMap);
		
		llMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itMain = new Intent(getActivity(), MainActivity.class);
				itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
						MainActivity.MAP_DETAIL);
				itMain.putExtra("map", Map);
				itMain.putExtra("title", bundle.getString("title"));
				itMain.putExtra("add", Add);
				itMain.putExtra("type", "0");
				getActivity().startActivityForResult(itMain, 1);
			}
		});

		// Content
		LinearLayout llContent = (LinearLayout) getActivity().findViewById(
				R.id.llContent);
		llContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itMain = new Intent(getActivity(), MainActivity.class);
				itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
						MainActivity.CONTENT_DETAIL);
				itMain.putExtra("id", bundle.getString("id"));
				itMain.putExtra("title", bundle.getString("title"));
				getActivity().startActivityForResult(itMain, 1);
			}
		});
	}

	// PROCESS...
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
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		final int numStars = ratingBar.getNumStars();
		Log.i("numStars", Integer.toString(numStars));
	}

}
