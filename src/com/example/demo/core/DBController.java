package com.example.demo.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.demo.bean.CategoriesBean;
import com.example.demo.bean.PostBean;
import com.example.demo.bean.SyncBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController extends SQLiteOpenHelper {

	// Database Name
	private static final String DATABASE_NAME = "db_iedc";
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Settings table name
	private static final String TABLE_CATE = "categories";
	private static final String TABLE_POST = "post";
	// private static final String TABLE_META = "meta";
	private static final String TABLE_SYNC = "datesync";

	// Common Table Columns names
	private static final String KEY_ID = "Id";

	// Cate Table Columns names
	private static final String KEY_NAME = "Name";
	private static final String KEY_DATE = "Datesync";
	// private static final String KEY_SLUG = "Slug";

	// Post Table Columns names
	private static final String KEY_Cate_ID = "CateID";
	private static final String KEY_TITLE = "Title";
	private static final String KEY_CONTENT = "Content";
	private static final String KEY_ADDRESS = "Address";
	private static final String KEY_PRICE = "Price";
	private static final String KEY_TIME = "Time";
	private static final String KEY_RATE = "Rating";
	private static final String KEY_IMG = "Image";
	private static final String KEY_MAP = "Map";
	private static final String KEY_PHONE = "Phone";
	private static final String KEY_FAV = "Favorites";

	// Meta Table Columns names
	// private static final String POST_ID = "PostID";
	// private static final String KEY_METAKEY = "key";
	// private static final String KEY_METAVALUE = "value";

	// Sync Table Columns names
	private static final String KEY_CATE = "CateName";

	// private static final String Datesync = "datesync";

	public DBController(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_TABLE_CATE = "CREATE TABLE " + TABLE_CATE + "(" + KEY_ID
				+ " TEXT PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_DATE
				+ " DATETIME" + ")";

		String CREATE_TABLE_POST = "CREATE TABLE " + TABLE_POST + "(" 
				+ KEY_ID+ " TEXT PRIMARY KEY," 
				+ KEY_TITLE + " TEXT," 
				+ KEY_CONTENT + " TEXT," 
				+ KEY_Cate_ID + " TEXT,"
				+ KEY_ADDRESS + " TEXT," 
				+ KEY_PRICE + " TEXT," 
				+ KEY_TIME + " TEXT," 
				+ KEY_RATE + " TEXT," 
				+ KEY_IMG + " TEXT," 
				+ KEY_MAP+ " TEXT," 
				+ KEY_PHONE + " TEXT," 
				+ KEY_FAV + " INTEGER DEFAULT 0" + ")";

		// String CREATE_TABLE_META = "CREATE TABLE " + TABLE_META + "("
		// + KEY_ID + " TEXT PRIMARY KEY ,"
		// + POST_ID + " TEXT,"
		// + KEY_METAKEY + " TEXT,"
		// + KEY_METAVALUE + " TEXT"
		// + ")";

		String CREATE_TABLE_SYNC = "CREATE TABLE " + TABLE_SYNC + "(" + KEY_ID
				+ " TEXT PRIMARY KEY," + KEY_CATE + " TEXT," + KEY_DATE
				+ " DATETIME" + ")";

		try {
			db.execSQL(CREATE_TABLE_CATE);
			db.execSQL(CREATE_TABLE_POST);
			db.execSQL(CREATE_TABLE_SYNC);
			// db.execSQL(CREATE_TABLE_META);
		} catch (SQLException se) {
			Log.e("SQLException: ", se.toString());
		}
	}

	public void onDrop(SQLiteDatabase db) {
		// Drop older table if existed
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYNC);
			// db.execSQL("DROP TABLE IF EXISTS " + TABLE_META);
		} catch (SQLException se) {
			Log.e("SQLException: ", se.toString());
		}
		// Create tables again
		onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYNC);
			// db.execSQL("DROP TABLE IF EXISTS " + TABLE_META);
		} catch (SQLException se) {
			Log.e("SQLException: ", se.toString());
		}
		// Create tables again
		onCreate(db);
	}

	// //////////////////////////////////Sync
	// Date////////////////////////////////////
	public List<SyncBean> getAllSync() {
		List<SyncBean> syncs = new ArrayList<SyncBean>();
		String selectQuery = "SELECT * FROM " + TABLE_SYNC;
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		// Log.i("Cursor", cursor.toString());
		if (cursor.moveToFirst()) {
			do {
				SyncBean sync = new SyncBean();
				sync.set_ID(cursor.getString(0));
				sync.set_CateName(cursor.getString(1));
				sync.set_DateSync(cursor.getString(2));
				syncs.add(sync);
			} while (cursor.moveToNext());
		}
		// database.close();
		return syncs;
	}

	public List<SyncBean> getDateSync(String tablename) {
		List<SyncBean> syncs = new ArrayList<SyncBean>();
		String selectQuery = "SELECT * FROM " + TABLE_SYNC + " where " + KEY_ID
				+ "=" + "'" + tablename + "'";

		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		// Log.i("Cursor", cursor.toString());
		if (cursor.moveToFirst()) {
			do {
				SyncBean sync = new SyncBean();
				sync.set_ID(cursor.getString(0));
				sync.set_CateName(cursor.getString(1));
				sync.set_DateSync(cursor.getString(2));
				syncs.add(sync);
			} while (cursor.moveToNext());
		}
		// database.close();
		return syncs;
	}

	public void insertSync(SyncBean sync) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();

			ContentValues values = new ContentValues();

			values.put(KEY_ID, sync.get_ID());
			values.put(KEY_CATE, sync.get_CateName());
			values.put(KEY_DATE, sync.get_DateSync());
			database.insert(TABLE_SYNC, null, values);
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
	}

	public void updateSync(String id, String time) {
		SQLiteDatabase db = this.getWritableDatabase();
		String strFilter = KEY_ID + " = " + id;
		ContentValues values = new ContentValues();
		values.put(KEY_DATE, time);

		db.update(TABLE_SYNC, values, strFilter, null);
		// updating row
		// return db.update(TABLE_SYNC, values, KEY_ID + " = ?",
		// new String[] { String.valueOf(contact.getID()) });

	}

	// //////////////////////////////////CATEGORIES////////////////////////////////////

	public List<CategoriesBean> getAllCate() {
		List<CategoriesBean> cate = new ArrayList<CategoriesBean>();
		String selectQuery = "SELECT * FROM " + TABLE_CATE;
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		// Log.i("Cursor", cursor.toString());
		if (cursor.moveToFirst()) {
			do {
				CategoriesBean cat = new CategoriesBean();
				cat.set_ID(cursor.getString(0));
				cat.set_Name(cursor.getString(1));
				cate.add(cat);
			} while (cursor.moveToNext());
		}
		// database.close();
		return cate;
	}

	public void insertCate(CategoriesBean cate) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_ID, cate.get_ID());
			values.put(KEY_NAME, cate.get_Name());
			values.put(KEY_DATE, cate.get_DateSync());
			database.insert(TABLE_CATE, null, values);
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
	}

	// //////////////////////////////////POST////////////////////////////////////

	public List<PostBean> getAllPost() {
		List<PostBean> postlist = new ArrayList<PostBean>();
		String selectQuery = "SELECT * FROM " + TABLE_POST;
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				PostBean post = new PostBean();
				post.set_ID(cursor.getString(0));
				post.set_TITLE(cursor.getString(1));
				post.set_CONTENT(cursor.getString(2));
				post.set_Cate_ID(cursor.getString(3));

				post.set_ADDRESS(cursor.getString(4));
				post.set_PRICE(cursor.getString(5));
				post.set_TIME(cursor.getString(6));
				post.set_RATE(cursor.getString(7));
				post.set_IMG(cursor.getString(8));
				post.set_MAP(cursor.getString(9));
				post.set_PHONE(cursor.getString(10));
				post.set_FAV(cursor.getInt(11));

				postlist.add(post);
			} while (cursor.moveToNext());
		}
		// database.close();
		return postlist;
	}

	public List<PostBean> getPostsByCate(String query, Boolean order) {

		List<PostBean> postlist = new ArrayList<PostBean>();
		String selectQuery = "";
		if (order == true) {

			selectQuery = "SELECT * FROM " + TABLE_POST + " WHERE "
					+ KEY_Cate_ID + "=" + "'" + query + "'" + " ORDER BY "
					+ KEY_TITLE + " ASC";
		} else {
			selectQuery = "SELECT * FROM " + TABLE_POST + " WHERE "
					+ KEY_Cate_ID + "=" + "'" + query + "'" + " ORDER BY "
					+ KEY_ID + " DESC";
		}
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				PostBean post = new PostBean();
				post.set_ID(cursor.getString(0));
				post.set_TITLE(cursor.getString(1));
				post.set_CONTENT(cursor.getString(2));
				post.set_Cate_ID(cursor.getString(3));

				post.set_ADDRESS(cursor.getString(4));
				post.set_PRICE(cursor.getString(5));
				post.set_TIME(cursor.getString(6));
				post.set_RATE(cursor.getString(7));
				post.set_IMG(cursor.getString(8));
				post.set_MAP(cursor.getString(9));
				post.set_PHONE(cursor.getString(10));
				post.set_FAV(cursor.getInt(11));
				postlist.add(post);
			} while (cursor.moveToNext());
		}
		// database.close();
		return postlist;
	}

	public List<PostBean> getPostsByKw(String query) {
		List<PostBean> postlist = new ArrayList<PostBean>();
		String selectQuery = "SELECT * FROM " + TABLE_POST + " WHERE "
				+ KEY_TITLE + " LIKE '%" + query + "%' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				PostBean post = new PostBean();
				post.set_ID(cursor.getString(0));
				post.set_TITLE(cursor.getString(1));
				post.set_CONTENT(cursor.getString(2));
				post.set_Cate_ID(cursor.getString(3));

				post.set_ADDRESS(cursor.getString(4));
				post.set_PRICE(cursor.getString(5));
				post.set_TIME(cursor.getString(6));
				post.set_RATE(cursor.getString(7));
				post.set_IMG(cursor.getString(8));
				post.set_MAP(cursor.getString(9));
				post.set_PHONE(cursor.getString(10));
				post.set_FAV(cursor.getInt(11));
				postlist.add(post);
			} while (cursor.moveToNext());
		}
		// database.close();
		return postlist;
	}

	public void insertPost(PostBean postbean) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_ID, postbean.get_ID());
			values.put(KEY_Cate_ID, postbean.get_Cate_ID());
			values.put(KEY_TITLE, postbean.get_TITLE());
			values.put(KEY_CONTENT, postbean.get_CONTENT());

			values.put(KEY_ADDRESS, postbean.get_ADDRESS());
			values.put(KEY_PRICE, postbean.get_IMG());
			values.put(KEY_TIME, postbean.get_MAP());
			values.put(KEY_RATE, postbean.get_PHONE());
			values.put(KEY_IMG, postbean.get_PRICE());
			values.put(KEY_MAP, postbean.get_RATE());
			values.put(KEY_PHONE, postbean.get_TIME());

			database.insert(TABLE_POST, null, values);
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
	}

	public void downloadPost(String id, int i) {
		if (i == 1) {
			SQLiteDatabase db = this.getWritableDatabase();
			String strFilter = KEY_ID + " = " + id;
			ContentValues values = new ContentValues();
			values.put(KEY_FAV, 1);
			db.update(TABLE_POST, values, strFilter, null);
		} else if (i == 0) {
			SQLiteDatabase db = this.getWritableDatabase();
			String strFilter = KEY_ID + " = " + id;
			ContentValues values = new ContentValues();
			values.put(KEY_FAV, 0);
			db.update(TABLE_POST, values, strFilter, null);
		}
	}

	public List<PostBean> getAllDownload(Boolean order) {
		List<PostBean> postlist = new ArrayList<PostBean>();
		
		String selectQuery = "";
		if (order == true) {

			selectQuery = "SELECT * FROM " + TABLE_POST + " WHERE "
					+ KEY_FAV + "=" + 1 + " ORDER BY "
					+ KEY_TITLE + " ASC";
		} else {
			selectQuery = "SELECT * FROM " + TABLE_POST + " WHERE "
					+ KEY_FAV + "=" + 1 + " ORDER BY "
					+ KEY_TITLE + " DESC";
		}
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				PostBean post = new PostBean();
				post.set_ID(cursor.getString(0));
				post.set_TITLE(cursor.getString(1));
				post.set_CONTENT(cursor.getString(2));
				post.set_Cate_ID(cursor.getString(3));

				post.set_ADDRESS(cursor.getString(4));
				post.set_PRICE(cursor.getString(5));
				post.set_TIME(cursor.getString(6));
				post.set_RATE(cursor.getString(7));
				post.set_IMG(cursor.getString(8));
				post.set_MAP(cursor.getString(9));
				post.set_PHONE(cursor.getString(10));
				post.set_FAV(cursor.getInt(11));
				postlist.add(post);
			} while (cursor.moveToNext());
		}
		// database.close();
		return postlist;
	}
	
	// //////////////////////////////////POST
	// META////////////////////////////////////
	public int insertMeta(HashMap<String, String> queryValues) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_ADDRESS, queryValues.get("Address"));
			values.put(KEY_IMG, queryValues.get("Time"));
			values.put(KEY_MAP, queryValues.get("Rate"));
			values.put(KEY_PHONE, queryValues.get("Price"));
			values.put(KEY_PRICE, queryValues.get("IMG"));
			values.put(KEY_RATE, queryValues.get("Phone"));
			values.put(KEY_TIME, queryValues.get("Map"));
			return database.update(TABLE_POST, values, KEY_ID + " = ?",
					new String[] { queryValues.get("ID") });
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
		return 1;
	}

	public int insertMetaTime(HashMap<String, String> queryValues) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_TIME, queryValues.get("Time"));
			return database.update(TABLE_POST, values, KEY_ID + " = ?",
					new String[] { queryValues.get("ID") });
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
		return 1;
	}

	public int insertMetaRate(HashMap<String, String> queryValues) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_RATE, queryValues.get("Rate"));
			return database.update(TABLE_POST, values, KEY_ID + " = ?",
					new String[] { queryValues.get("ID") });
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
		return 1;
	}

	public int insertMetaPrice(HashMap<String, String> queryValues) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_PRICE, queryValues.get("Price"));
			return database.update(TABLE_POST, values, KEY_ID + " = ?",
					new String[] { queryValues.get("ID") });
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
		return 1;
	}

	public int insertMetaIMG(HashMap<String, String> queryValues) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_IMG, queryValues.get("IMG"));
			return database.update(TABLE_POST, values, KEY_ID + " = ?",
					new String[] { queryValues.get("ID") });
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
		return 1;
	}

	public int insertMetaPhone(HashMap<String, String> queryValues) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_PHONE, queryValues.get("Phone"));
			return database.update(TABLE_POST, values, KEY_ID + " = ?",
					new String[] { queryValues.get("ID") });
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
		return 1;
	}

	public int insertMetaAdd(HashMap<String, String> queryValues) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_ADDRESS, queryValues.get("Address"));
			return database.update(TABLE_POST, values, KEY_ID + " = ?",
					new String[] { queryValues.get("ID") });
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
		return 1;
	}

	public int insertMetaMap(HashMap<String, String> queryValues) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_MAP, queryValues.get("Map"));
			return database.update(TABLE_POST, values, KEY_ID + " = ?",
					new String[] { queryValues.get("ID") });
			// database.close();
		} catch (SQLException ex) {
			Log.e("SQLException: ", ex.toString());
		}
		return 1;
	}
}
