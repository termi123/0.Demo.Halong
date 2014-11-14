package com.example.demo.fragment;

import java.util.LinkedList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.example.demo.adapter.MenuListAdapter;
import com.example.demo.bean.LeftMenuBean;
import com.example.demo.component.ActionImageButton;
import com.example.demo.halong.MainActivity;
import com.example.demo.halong.R;

public class MenuListFragment extends SherlockListFragment{
	
	private LinkedList<LeftMenuBean> lstURL;
	private View mFormView;
	private View mStatusView;
	private View ll;
	private View view;
	int nroPage = 1;
	int currentList = 0;
	int typeView;
	public final static int BROWSER_MANGA = 0;
	public final static int BROWSER_DOUJIN = 1;
	public final static int TYPE_LIST_TAGS = 0;
	public final static int TYPE_LIST_SERIES = 1;

	public void setMainActivity(MainActivity mainActivity) {
	}

	private View findViewById(int resource) {
		return view.findViewById(resource);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), com.actionbarsherlock.R.style.Sherlock___Theme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

		view = localInflater.inflate(R.layout.fragment_menu, container, false);
		
		mFormView = findViewById(R.id.view_form);
		mStatusView = findViewById(R.id.view_status);
		ll = findViewById(R.id.ll);
		ActionImageButton btnPreviousPage = (ActionImageButton) findViewById(R.id.btnPreviousPage);
		ActionImageButton btnNextPage = (ActionImageButton) findViewById(R.id.btnNextPage);

		btnPreviousPage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				previousPage();
			}
		});

		btnNextPage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nextPage();
			}
		});

		createMainMenu();
		return view;
	}

	// ////////////////////////////////////Button event
	public void nextPage() {
		nroPage++;
		loadPage();
		CharSequence text = "Page " + nroPage;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(getActivity(), text, duration);
		toast.show();
	}

	public void previousPage() {
		if (nroPage - 1 == 0) {

			CharSequence text = "There aren't more pages.";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(getActivity(), text, duration);
			toast.show();
		} else {
			nroPage--;
			loadPage();
			CharSequence text = "Page " + nroPage;
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(getActivity(), text, duration);
			toast.show();
		}
	}

	@SuppressLint("NewApi")
	private void loadPage() {
		TextView tvPage = (TextView) findViewById(R.id.tvPage);
		tvPage.setText("Page " + nroPage);
	}
	
	// ////////////////////////////////////CreateMenu
	public void createMainMenu() {
		String[] lstMainMenu = getActivity().getResources().getStringArray(
				R.array.main_menu);
		int[] lstIcons = new int[] { 
				R.drawable.home,
//				R.drawable.navigation_forward_inverse,
				R.drawable.rating_important_inverse,
//				R.drawable.navigation_back_inverse,
				R.drawable.icon_res, 
				R.drawable.icon_hotel, 
				R.drawable.icon_att, 
				R.drawable.icon_night, 
				R.drawable.icon_shop, 
				R.drawable.icon_ticket, 
				R.drawable.av_upload_inverse,
				R.drawable.action_settings_inverse };
		lstURL = new LinkedList<LeftMenuBean>();

		for (int i = 0; i < lstMainMenu.length; i++) {
			LeftMenuBean bean = new LeftMenuBean(lstMainMenu[i]);
			bean.setIcon(lstIcons[i]);
			lstURL.add(bean);
		}
		this.setListAdapter(new MenuListAdapter(this.getActivity(),
				R.layout.row_menu, 0, lstURL, true));
	}
	
	// ////////////////////////////////////Menu Item event
	@SuppressLint("NewApi")
	public void onListItemClick(ListView l, View v, int position, long id) {
		LeftMenuBean bean = lstURL.get(position);
			if (position == 0) {
                Intent itMain = new Intent(getActivity(), MainActivity.class);
                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.MAIN_LIST);
                getActivity().startActivityForResult(itMain, 1);
                
			} else if (bean.getDescription().equals("My favorites")) {
				Intent itMain = new Intent(getActivity(), MainActivity.class);
                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.DOWNLOAD_LIST);
                getActivity().startActivityForResult(itMain, 1);

			} else if (bean.getDescription().equals("Restaurants")) {
				
				Intent itMain = new Intent(getActivity(), MainActivity.class);
                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.POST_LIST);
                itMain.putExtra(MainActivity.INTENT_VAR_CateID,MainActivity.CATEID_RESTAURANT);
                itMain.putExtra(MainActivity.INTENT_VAR_CateTITLE,MainActivity.CATETITLE_RESTAURANT);
                getActivity().startActivityForResult(itMain, 1);
                
			} else if (bean.getDescription().equals("Hotels")) {
				
				Intent itMain = new Intent(getActivity(), MainActivity.class);
                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.POST_LIST);
                itMain.putExtra(MainActivity.INTENT_VAR_CateID,MainActivity.CATEID_HOTEL);
                itMain.putExtra(MainActivity.INTENT_VAR_CateTITLE,MainActivity.CATETITLE_HOTEL);
                getActivity().startActivityForResult(itMain, 1);
                
			} else if (bean.getDescription().equals("Attractions")) {
				
				Intent itMain = new Intent(getActivity(), MainActivity.class);
                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.POST_LIST);
				itMain.putExtra(MainActivity.INTENT_VAR_CateID,MainActivity.CATEID_ATT);
                itMain.putExtra(MainActivity.INTENT_VAR_CateTITLE,MainActivity.CATETITLE_ATT);
                itMain.putExtra("type", "2");
                getActivity().startActivityForResult(itMain, 1);
				
			} else if (bean.getDescription().equals("Bar/Pub/Coffee")) {
				
				Intent itMain = new Intent(getActivity(), MainActivity.class);
                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.POST_LIST);
                itMain.putExtra(MainActivity.INTENT_VAR_CateID,MainActivity.CATEID_BAR);
                itMain.putExtra(MainActivity.INTENT_VAR_CateTITLE,MainActivity.CATETITLE_BAR);
                getActivity().startActivityForResult(itMain, 1);
				
			} else if (bean.getDescription().equals("Shopping")) {
				
				Intent itMain = new Intent(getActivity(), MainActivity.class);
                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.POST_LIST);
				itMain.putExtra(MainActivity.INTENT_VAR_CateID,MainActivity.CATEID_SHOP);
                itMain.putExtra(MainActivity.INTENT_VAR_CateTITLE,MainActivity.CATETITLE_SHOP);
                getActivity().startActivityForResult(itMain, 1);
                
			} else if (bean.getDescription().equals("Tour")) {
				
				Intent itMain = new Intent(getActivity(), MainActivity.class);
                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.POST_LIST);
				itMain.putExtra(MainActivity.INTENT_VAR_CateID,MainActivity.CATEID_TICKET);
                itMain.putExtra(MainActivity.INTENT_VAR_CateTITLE,MainActivity.CATETITLE_TICKET);
                itMain.putExtra("type", "2");
                getActivity().startActivityForResult(itMain, 1);
                
//			} else if (bean.getDescription().startsWith("Sign")) {
//				Intent itMain = new Intent(getActivity(), MainActivity.class);
//                itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT, MainActivity.LOGIN);
//                getActivity().startActivityForResult(itMain, 1);
//			} else if (bean.getDescription().equals("Preferences")) {
//				
//			} else if (bean.getDescription().equals("Logout")) {
//				
//			} else if (bean.getDescription().startsWith("Check")) {
//				
			}
	}
	
	
	// ////////////////////////////////////Progress
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mStatusView.setVisibility(View.VISIBLE);
			mStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
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
			mStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}