package com.example.demo.adapter;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.adapter.PostListAdapter.ViewHolder;
import com.example.demo.bean.PostBean;
import com.example.demo.core.DBController;
import com.example.demo.halong.R;
import com.example.demo.util.Constants;

public class PostListAdapter2 extends BaseAdapter {

	private LayoutInflater mInflater;
	List<PostBean> postList;
	private List items = new ArrayList();
	DBController controller;

	public PostListAdapter2(Context context, List items) {
		mInflater = LayoutInflater.from(context);
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		PostBean s = (PostBean) items.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_post_list_demo, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

			holder.tvAddtt = (TextView) convertView.findViewById(R.id.tvAddtt);
			holder.tvAdd = (TextView) convertView.findViewById(R.id.tvAdd);

			holder.tvDes = (TextView) convertView.findViewById(R.id.tvDes);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
			holder.ivPage = (ImageView) convertView.findViewById(R.id.ivPage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvTitle.setText(s.get_TITLE());

//		if (s.get_ADDRESS().equals("")) {
			holder.tvAdd.setVisibility(View.GONE);
			holder.tvAddtt.setVisibility(View.GONE);
//		} else {
//			holder.tvAdd.setText(s.get_ADDRESS());
//		}
//
//		if (s.get_PRICE().equals("") && s.get_TIME().equals("")) {
			holder.tvDes.setVisibility(View.GONE);
//		}
//
//		if (s.get_TIME().equals("")) {
			holder.tvTime.setVisibility(View.GONE);
//		} else {
//			holder.tvTime.setText(s.get_TIME());
//		}
//
//		if (s.get_PRICE().equals("")) {
			holder.tvPrice.setVisibility(View.GONE);
//		} else {
//			holder.tvPrice.setText(s.get_PRICE());
//		}

		File imgFile = new File(Environment.getExternalStorageDirectory()
				+ Constants.CACHE_DIRECTORY + "/" + s.get_IMG());
		if (imgFile.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());
			holder.ivPage.setImageBitmap(myBitmap);
		} else {
			Log.i("imgFile", "NOT exists");
		}

		return convertView;
	}

	// Load direct from Internet // NOT USED
	public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

		private String url;
		private ImageView imageView;

		public ImageLoadTask(String url, ImageView imageView) {
			this.url = url;
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				URL urlConnection = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlConnection
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				return myBitmap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			imageView.setImageBitmap(result);
		}

	}

	static class ViewHolder {
		TextView tvTitle, tvAddtt, tvAdd, tvDes, tvTime, tvPrice;
		ImageView ivPage;
	}

}
