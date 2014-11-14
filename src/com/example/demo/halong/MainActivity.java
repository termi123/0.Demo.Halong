package com.example.demo.halong;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.example.demo.core.DBController;
import com.example.demo.fragment.AdFragment;
import com.example.demo.fragment.ContentDetailFragment;
import com.example.demo.fragment.DownloadListFragment;
import com.example.demo.fragment.MainListFragment;
import com.example.demo.fragment.MapDetailFragment;
import com.example.demo.fragment.MenuListFragment;
import com.example.demo.fragment.PostDetailFragment;
import com.example.demo.fragment.PostListFragment;
import com.example.demo.fragment.PostListSearchFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class MainActivity extends SherlockFragmentActivity implements
		SearchView.OnQueryTextListener {

	// ////////////////////////////////////StringExtra
	public final static String INTENT_VAR_CateID = "CateID";
	public final static String INTENT_VAR_CateTITLE = "CateTitle";
	// public final static String INTENT_VAR_TITLE = "intentVarTitle";
	// public final static String INTENT_VAR_DATA = "intentVarUser";
	public final static String INTENT_VAR_CURRENT_CONTENT = "intentVarCurrentContent";
	// public final static String INTENT_VAR_IS_RELATED = "intentVarIsRelated";
	public final static String INTENT_VAR_Query = "Query";

	// ////////////////////////////////////Categories ID
	public final static String CATEID_HOTEL = "3220";
	public final static String CATEID_RESTAURANT = "3221";
	public final static String CATEID_ATT = "3222";
	public final static String CATEID_SHOP = "3224";
	public final static String CATEID_TICKET = "3225";
	public final static String CATEID_BAR = "3245";

	// ////////////////////////////////////Categories TITLE
	public final static String CATETITLE_RESTAURANT = "Top New Restaurants";
	public final static String CATETITLE_TICKET = "Top New Tickets";
	public final static String CATETITLE_SHOP = "Top New Shops";
	public final static String CATETITLE_HOTEL = "Top New Hotels";
	public final static String CATETITLE_ATT = "Top New Attractions";
	public final static String CATETITLE_BAR = "Top New Bar, Pub, Coffee";
	// ////////////////////////////////////Order
	// public final static Boolean ORDER = true;
	// ////////////////////////////////////Content Page
	public static final int MAIN_LIST = 0;
	public static final int POST_LIST = 1;
	public static final int POST_LIST_SEARCH = 2;
	public static final int POST_DETAIL = 3;
	public static final int MAP_DETAIL = 4;
	public static final int CONTENT_DETAIL = 5;
	public static final int DOWNLOAD_LIST = 6;
	public static final int LOGIN = 7;

	private DrawerLayout mDrawerLayout;
	private MenuListFragment frmMenu;
	private ActionBarDrawerToggle mDrawerToggle;
//    private LoginButton loginButton;
    
	private MainListFragment frmMainList;
	private PostListFragment frmPostList;
	private PostDetailFragment frmPostDetail;
	private MapDetailFragment frmMapDetail;
	private PostListSearchFragment frmPostListSearch;
	private ContentDetailFragment frmContentDetail;
	private DownloadListFragment frmDownList;
	private AdFragment frmAd;
	
	ProgressDialog prgDialog;

	DBController controller = new DBController(this);

	private int currentContent = MAIN_LIST;
	
//	private ProfilePictureView profilePictureView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// //////DrawerLayout////////

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View view) {
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// //////Fragment Left Menu////////
		frmMenu = new MenuListFragment();
		frmMenu.setMainActivity(this);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.menu_frame, frmMenu)
				.commit();
		
		// //////Fragment Ad////////
		frmAd = new AdFragment();
		FragmentManager fragmentManager1 = getSupportFragmentManager();
		fragmentManager1.beginTransaction().replace(R.id.ad_frame, frmAd)
				.commit();
		
		////////////////////////////
		
		int tempCurrentContent = getIntent().getIntExtra(
				INTENT_VAR_CURRENT_CONTENT, -1);

		if (tempCurrentContent != -1)
			currentContent = tempCurrentContent;

		if (currentContent == MAIN_LIST) {
//			Bundle bundle = getIntent().getExtras();
//			Toast.makeText(this, bundle.getString("id"), Toast.LENGTH_SHORT).show();
//			Log.i("id Get:", bundle.getString("id"));
//			profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
//			profilePictureView.setProfileId(bundle.getString("id"));
//			profilePictureView.setProfileId("855984071078968");
			
			mainlistload();
		} else if (currentContent == POST_LIST) {
			postlistload();
		} else if (currentContent == POST_LIST_SEARCH) {
			String query = getIntent().getStringExtra(INTENT_VAR_Query);
			searchlistload(query);
		} else if (currentContent == POST_DETAIL) {
			postdetailload();
		} else if (currentContent == MAP_DETAIL) {
			mapdetailload();
		} else if (currentContent == CONTENT_DETAIL) {
			contentdetailload();
		} else if (currentContent == DOWNLOAD_LIST) {
			downloadlistload();
		} else if (currentContent == LOGIN) {
			Loginload();
		}
	}

	private void Loginload() {
		
	}

	private void mainlistload() {
		frmMainList = new MainListFragment();
		frmMainList.setMainActivity(this);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, frmMainList)
				// .addToBackStack(null)
				.commit();
		setTitle("Travel Helpers");
	}

	private void postlistload() {
		frmPostList = new PostListFragment();
		frmPostList.setMainActivity(this);
		Bundle mBundle = new Bundle();
		String type = getIntent().getStringExtra("type");
		type = type == null ? "1" : type;
		
		setTitle(getIntent().getStringExtra(INTENT_VAR_CateTITLE));
		mBundle.putString("cateid", getIntent().getStringExtra(INTENT_VAR_CateID));
		mBundle.putString("type",type);
		// mBundle.putString("order", getIntent().getStringExtra("order"));
		frmPostList.setArguments(mBundle);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, frmPostList).addToBackStack(null)
				.commit();
	}

	private void searchlistload(String query) {
		frmPostListSearch = new PostListSearchFragment();
		frmPostListSearch.setMainActivity(this);
		// setTitle(title);
		Bundle mBundle = new Bundle();
		mBundle.putString("query", query);
		frmPostListSearch.setArguments(mBundle);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, frmPostListSearch)
				.addToBackStack(null).commit();
		setTitle("Search: " + query);
	}

	private void downloadlistload() {
		frmDownList = new DownloadListFragment();
		frmDownList.setMainActivity(this);
		// setTitle(title);
		// Bundle mBundle = new Bundle();
		// mBundle.putString("query", query);
		// frmDownList.setArguments(mBundle);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, frmDownList).addToBackStack(null)
				.commit();
		setTitle("My Favorites");
	}

	private void postdetailload() {
		frmPostDetail = new PostDetailFragment();
		frmPostDetail.setMainActivity(this);

		Bundle mBundle = new Bundle();
		mBundle.putString("id", getIntent().getStringExtra("id"));
		mBundle.putString("title", getIntent().getStringExtra("title"));
		mBundle.putString("content", getIntent().getStringExtra("content"));

		mBundle.putString("image", getIntent().getStringExtra("image"));
		mBundle.putString("add", getIntent().getStringExtra("add"));
		mBundle.putString("time", getIntent().getStringExtra("time"));
		mBundle.putString("price", getIntent().getStringExtra("price"));
		mBundle.putString("rate", getIntent().getStringExtra("rate"));
		mBundle.putString("map", getIntent().getStringExtra("map"));
		mBundle.putString("phone", getIntent().getStringExtra("phone"));
		mBundle.putInt("fav", getIntent().getIntExtra("fav", 0));
		frmPostDetail.setArguments(mBundle);

		setTitle(getIntent().getStringExtra("title"));
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, frmPostDetail)
				// .addToBackStack(null)
				.commit();
	}

	private void mapdetailload() {
		frmMapDetail = new MapDetailFragment();
		frmMapDetail.setMainActivity(this);
		Log.i("type", getIntent().getStringExtra("type"));
		if (getIntent().getStringExtra("type").equals("1")) {
			Bundle mBundle = new Bundle();
			mBundle.putString("type", "1");
			frmMapDetail.setArguments(mBundle);
			setTitle(getIntent().getStringExtra("title"));
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, frmMapDetail)
					// .addToBackStack(null)
					.commit();
		} else if (getIntent().getStringExtra("type").equals("0")) {
			Bundle mBundle = new Bundle();
			mBundle.putString("title", getIntent().getStringExtra("title"));
			mBundle.putString("map", getIntent().getStringExtra("map"));
			mBundle.putString("add", getIntent().getStringExtra("add"));
			mBundle.putString("type", "0");
			frmMapDetail.setArguments(mBundle);

			setTitle(getIntent().getStringExtra("title"));
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, frmMapDetail)
					// .addToBackStack(null)
					.commit();
		}
	}

	private void contentdetailload() {
		frmContentDetail = new ContentDetailFragment();
		frmContentDetail.setMainActivity(this);
		Bundle mBundle = new Bundle();
		mBundle.putString("id", getIntent().getStringExtra("id"));
		frmContentDetail.setArguments(mBundle);

		setTitle(getIntent().getStringExtra("title"));
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, frmContentDetail)
				// .addToBackStack(null)
				.commit();
	}

	// ////////////////////////////////////Bottom Menu -
	// ImageEvent/////////////////////////////////////

	public void btnres(View view) {
		// postlistload(CATEID_RESTAURANT, CATETITLE_RESTAURANT, ORDER);
		Intent itMain = new Intent(this, MainActivity.class);
		itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
				MainActivity.POST_LIST);
		itMain.putExtra(INTENT_VAR_CateID, CATEID_RESTAURANT);
		itMain.putExtra(INTENT_VAR_CateTITLE, CATETITLE_RESTAURANT);
		// itMain.putExtra("order", ORDER);
		this.startActivityForResult(itMain, 1);
		
	}

	public void btnhotel(View view) {
		// postlistload(CATEID_HOTEL, CATETITLE_HOTEL, ORDER);
		Intent itMain = new Intent(this, MainActivity.class);
		itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
				MainActivity.POST_LIST);
		itMain.putExtra(INTENT_VAR_CateID, CATEID_HOTEL);
		itMain.putExtra(INTENT_VAR_CateTITLE, CATETITLE_HOTEL);
		// itMain.putExtra("order", ORDER);
		this.startActivityForResult(itMain, 1);
	}

	public void btnatt(View view) {
		// postlistload(CATEID_ATT, CATETITLE_ATT, ORDER);
		Intent itMain = new Intent(this, MainActivity.class);
		itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
				MainActivity.POST_LIST);
		itMain.putExtra(INTENT_VAR_CateID, CATEID_ATT);
		itMain.putExtra(INTENT_VAR_CateTITLE, CATETITLE_ATT);
		itMain.putExtra("type", "2");
		// itMain.putExtra("order", ORDER);
		this.startActivityForResult(itMain, 1);
	}

	public void btnnight(View view) {
		// postlistload(CATEID_BAR, CATETITLE_BAR, ORDER);
		Intent itMain = new Intent(this, MainActivity.class);
		itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
				MainActivity.POST_LIST);
		itMain.putExtra(INTENT_VAR_CateID, CATEID_BAR);
		itMain.putExtra(INTENT_VAR_CateTITLE, CATETITLE_BAR);
		// itMain.putExtra("order", ORDER);
		this.startActivityForResult(itMain, 1);
	}

	public void btnshop(View view) {
		// postlistload(CATEID_SHOP, CATETITLE_SHOP, ORDER);
		Intent itMain = new Intent(this, MainActivity.class);
		itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
				MainActivity.POST_LIST);
		itMain.putExtra(INTENT_VAR_CateID, CATEID_SHOP);
		itMain.putExtra(INTENT_VAR_CateTITLE, CATETITLE_SHOP);
		// itMain.putExtra("order", ORDER);
		this.startActivityForResult(itMain, 1);
	}

	public void btnticket(View view) {
		// postlistload(CATEID_TICKET, CATETITLE_TICKET, ORDER);
		Intent itMain = new Intent(this, MainActivity.class);
		itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
				MainActivity.POST_LIST);
		itMain.putExtra(INTENT_VAR_CateID, CATEID_TICKET);
		itMain.putExtra(INTENT_VAR_CateTITLE, CATETITLE_TICKET);
		itMain.putExtra("type", "2");
		// itMain.putExtra("order", ORDER);
		this.startActivityForResult(itMain, 1);
	}

	// ////////////////////////////////////LeftMenuList/////////////////////////////////////
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		frmMenu.createMainMenu();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// ////////////////////////////////////OptionMenu/////////////////////////////////////

	// ////////////////////////////////////CreateOptionMenu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (currentContent == POST_LIST || currentContent == MAIN_LIST
				|| currentContent == POST_DETAIL
				|| currentContent == DOWNLOAD_LIST) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
				// Create the search view
				SearchView searchView = new SearchView(getSupportActionBar()
						.getThemedContext());
				searchView
						.setQueryHint(getResources().getText(R.string.search));
				searchView.setOnQueryTextListener(this);
				String hint = "";
				getResources().getText(R.string.search);
				menu.add(hint)
						.setIcon(R.drawable.action_search)
						.setActionView(searchView)
						.setShowAsAction(
								MenuItem.SHOW_AS_ACTION_IF_ROOM
										| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

				menu.add(Menu.NONE, R.string.map, 2, R.string.map)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			} else {
				menu.add(Menu.NONE, R.string.search, 1, R.string.search)
						.setIcon(R.drawable.action_search)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

				menu.add(Menu.NONE, R.string.map, 2, R.string.map)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			}
		}

		if ((currentContent == POST_LIST && frmPostList != null)) {
			Log.i("frmPostList.isOrderDate()",
					Boolean.toString(frmPostList.isOrderDate()));
			if (frmPostList.isOrderDate()) {
				menu.add(Menu.NONE, R.string.order_a_to_z, 3,
						R.string.order_a_to_z).setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM);
			} else {
				menu.add(Menu.NONE, R.string.order_date, 3, R.string.order_date)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			}
		}

		if ((currentContent == DOWNLOAD_LIST && frmDownList != null)) {
			Log.i("frmDownList.isOrderDate()",
					Boolean.toString(frmDownList.isOrderDate()));
			if (frmDownList.isOrderDate()) {
				menu.add(Menu.NONE, R.string.order_a_to_z, 3,
						R.string.order_a_to_z).setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM);
			} else {
				menu.add(Menu.NONE, R.string.order_date, 3, R.string.order_date)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			}
		}
		return true;
	}

	// ////////////////////////////////////Menu Item
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(getMenuItem(item))) {
			return true;
		} else if (item.getItemId() == R.string.search) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle(R.string.abs__searchview_description_query);
			alert.setMessage(R.string.search);

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			alert.setView(input);
			alert.setNegativeButton(android.R.string.cancel, null);
			// if (currentContent == POST_LIST ) {
			alert.setPositiveButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String query = input.getText().toString().trim();
							searchlistload(query);
						}
					});
			// }
			alert.show();
		} else if (item.getItemId() == R.string.order_a_to_z
				|| item.getItemId() == R.string.order_date) {
			if (currentContent == POST_LIST) {
				frmPostList.changeOrder();
				supportInvalidateOptionsMenu();
			} else if (currentContent == DOWNLOAD_LIST) {
				frmDownList.changeOrder();
				supportInvalidateOptionsMenu();
			}
		} else if (item.getItemId() == R.string.map) {
			Intent itMain = new Intent(this, MainActivity.class);
			itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
					MainActivity.MAP_DETAIL);
			itMain.putExtra("type", "1");
			this.startActivityForResult(itMain, 1);
		}

		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// String query = arg0.trim();
		// Intent itMain = new Intent(this, MainActivity.class);
		// itMain.putExtra(INTENT_VAR_CURRENT_CONTENT, POST_LIST_SEARCH);
		// itMain.putExtra(INTENT_VAR_Query, query);
		// // itMain.putExtra(INTENT_VAR_URL, url);
		// startActivityForResult(itMain, 1);
		//
		// InputMethodManager im = (InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		// im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		String query = newText.trim();
		if (query.length() > 2) {
			searchlistload(query);
		}
		return true;
	}

	// ////////////////////////////////////Get Menu Item
	private android.view.MenuItem getMenuItem(final MenuItem item) {
		return new android.view.MenuItem() {
			@Override
			public int getItemId() {
				return item.getItemId();
			}

			public boolean isEnabled() {
				return true;
			}

			@Override
			public boolean collapseActionView() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean expandActionView() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public ActionProvider getActionProvider() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public View getActionView() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public char getAlphabeticShortcut() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getGroupId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Drawable getIcon() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Intent getIntent() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ContextMenuInfo getMenuInfo() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public char getNumericShortcut() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getOrder() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public SubMenu getSubMenu() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CharSequence getTitle() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CharSequence getTitleCondensed() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasSubMenu() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isActionViewExpanded() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isCheckable() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isChecked() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public android.view.MenuItem setActionProvider(
					ActionProvider actionProvider) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setActionView(View view) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setActionView(int resId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setAlphabeticShortcut(char alphaChar) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setCheckable(boolean checkable) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setChecked(boolean checked) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setEnabled(boolean enabled) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setIcon(Drawable icon) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setIcon(int iconRes) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setIntent(Intent intent) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setNumericShortcut(char numericChar) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setOnActionExpandListener(
					OnActionExpandListener listener) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setOnMenuItemClickListener(
					OnMenuItemClickListener menuItemClickListener) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setShortcut(char numericChar,
					char alphaChar) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setShowAsAction(int actionEnum) {
				// TODO Auto-generated method stub

			}

			@Override
			public android.view.MenuItem setShowAsActionFlags(int actionEnum) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setTitle(CharSequence title) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setTitle(int title) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setTitleCondensed(CharSequence title) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public android.view.MenuItem setVisible(boolean visible) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub

		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			Intent itMain = new Intent(this, MainActivity.class);
			itMain.putExtra(MainActivity.INTENT_VAR_CURRENT_CONTENT,
					MainActivity.MAIN_LIST);
			// itMain.putExtra("order", ORDER);
			this.startActivityForResult(itMain, 1);
			// mainlistload();
			// getFragmentManager().popBackStack(null,
			// FragmentManager.POP_BACK_STACK_INCLUSIVE);
		} else {
			super.onBackPressed();
		}

	}
	
	
}
