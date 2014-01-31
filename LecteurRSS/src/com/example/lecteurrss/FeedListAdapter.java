/**
 * This is the custom adapter to display the list of registered feed in the main activity.
 * @author Timothée Nguyen & Etienne Nguyen
 */
package com.example.lecteurrss;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedListAdapter extends BaseAdapter {
	Context context;
	ArrayList<RSSFeeds> feedList;
	
	public FeedListAdapter(Context context, ArrayList<RSSFeeds> list){
		this.context = context;
		feedList = list;
	}
	@Override
	public int getCount() {
		return feedList.size();
	}

	@Override
	public Object getItem(int position) {
		return feedList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RSSFeeds feedItem = feedList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.feed_list_row, null);

		}

		TextView name = (TextView) convertView.findViewById(R.id.name_tv);
		name.setText(feedItem.getFeedName());
		TextView url = (TextView) convertView.findViewById(R.id.url_tv);
		url.setText(feedItem.getFeedLink());
		return convertView;
	}

	
}
