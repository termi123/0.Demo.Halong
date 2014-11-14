package com.example.demo.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.demo.halong.MainActivity;
import com.example.demo.halong.R;
import com.example.demo.util.Constants;

public class ContentDetailFragment extends SherlockFragment {

	private MainActivity mMainActivity;
	private View mFormView;
	private View mStatusView;
	private View view;

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
	}

	public void onStart() {
		super.onStart();
		loadPage();
		showProgress(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_content, container, false);

		mFormView = view.findViewById(R.id.view_form);
		mStatusView = view.findViewById(R.id.view_status);
		// tvDistanceDuration = (TextView)
		// view.findViewById(R.id.tv_distance_time);
		return view;
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void loadPage() {

		Bundle bundle = getArguments();
		String url = bundle.getString("id");
		Log.i("URL", Constants.PostLink + url);
		WebView webView = (WebView) getActivity().findViewById(R.id.webView1);
//		webView.setVerticalScrollBarEnabled(true);
//		webView.setHorizontalScrollBarEnabled(true);
//		webView.requestFocusFromTouch();
		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setUseWideViewPort(true);
//		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.getSettings().setBuiltInZoomControls(true);
		
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.loadUrl(Constants.PostLink + url);
		webView.setWebViewClient(new WebViewClient());

		webView.setOnTouchListener(new View.OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_UP:
					if (!v.hasFocus()) {
						v.requestFocus();
					}
					break;
				}
				return false;
			}

		});

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
}
