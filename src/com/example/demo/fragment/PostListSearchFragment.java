package com.example.demo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockListFragment;
import com.example.demo.adapter.PostListAdapter;
import com.example.demo.bean.PostBean;
import com.example.demo.core.DBController;
import com.example.demo.halong.MainActivity;
import com.example.demo.halong.R;

public class PostListSearchFragment extends SherlockListFragment {

	int index = -1;
	int numPage = 1;
	private View mFormView;
	private View mStatusView;
	private View view;
	boolean related;
	private MainActivity mMainActivity;
	private PostListAdapter postAdapter;
	List<PostBean> post;

	ArrayList<HashMap<String, String>> PostList;

	DBController controller;

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

	@Override
	public void onStart() {
		super.onStart();
		loadPage();
		showProgress(false);
	}

	@Override
	public void onPause() {
		super.onPause();
		ListView list = (ListView) view.findViewById(android.R.id.list);
		index = list.getFirstVisiblePosition();
	}

	@Override
	public void onResume() {
		super.onResume();
		ListView list = (ListView) view.findViewById(android.R.id.list);
		if (index > -1)
			list.setSelectionFromTop(index, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_post_list_search, container, false);

		mFormView = view.findViewById(R.id.view_form);
		mStatusView = view.findViewById(R.id.view_status);

		return view;
	}

	// ////////////////////////////////////Load Page/////
	@SuppressLint("NewApi")
	public void loadPage() {
//		Toast.makeText(getActivity(), "SEARCH", Toast.LENGTH_LONG).show();
		Bundle bundle = getArguments();
		String keyword = bundle.getString("query");

		final List<PostBean> postquery = controller.getPostsByKw(keyword);
		// for (PostBean p : postquery) {
		// String log = "ID:" + p.get_ID() + " Cate ID: "
		// + p.get_Cate_ID() + " Title: " + p.get_TITLE()
		// + " Content: " + p.get_CONTENT() + " Add: "
		// + p.get_ADDRESS() + " Price: " + p.get_PRICE()
		// + " Time: " + p.get_TIME() + " Rate: " + p.get_RATE()
		// + " IMG: " + p.get_IMG() + " MAp: " + p.get_MAP();
		// Log.i("Log PostList: ", log);
		// }

		
		//Test Meta Query
		if (postquery.size() != 0) {

			postAdapter = new PostListAdapter(this.getActivity(), postquery);
			ListView myList = (ListView) getActivity().findViewById(
					android.R.id.list);
			myList.setAdapter(postAdapter);
			myList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent itMain = new Intent(mMainActivity,
							MainActivity.class);
					itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
							MainActivity.POST_DETAIL);
					itMain.putExtra("title", postquery.get(position)
							.get_TITLE());
					itMain.putExtra("add", postquery.get(position)
							.get_ADDRESS());
					itMain.putExtra("time", postquery.get(position).get_TIME());
					itMain.putExtra("price", postquery.get(position)
							.get_PRICE());
					itMain.putExtra("image", postquery.get(position).get_IMG());
					itMain.putExtra("content", postquery.get(position)
							.get_CONTENT());
					itMain.putExtra("rate", postquery.get(position).get_RATE());
					itMain.putExtra("map", postquery.get(position).get_MAP());
					getActivity().startActivityForResult(itMain, 1);
				}
			});

		} else {
			Log.i("getAllPost", "NO RECORD");
		}
	}

	// ////////////////////////////////////PROCESS...
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
