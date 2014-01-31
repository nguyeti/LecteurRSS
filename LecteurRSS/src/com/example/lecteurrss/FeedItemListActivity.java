/**
 * FeedItemListActivity is the activity that shows the list of articles
 * @author Timothée Nguyen & Etienne Nguyen
 */

package com.example.lecteurrss;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FeedItemListActivity extends ListActivity {
	private List<FeedItem> feedItemList = null;
	private ProgressDialog progress;
	private URL url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Enable the return arrow to the parent activity
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// Getting the intent extra data
		Intent receivedIntent = getIntent();
		String urlReceived = receivedIntent.getStringExtra("link");
		String feedNameReceived = receivedIntent.getStringExtra("name");
		// Changing the activity name with name of the feed displayed
		setTitle(feedNameReceived);
			
		try {
			url = new URL(urlReceived);
			new LoadFeedTask().execute(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();			
		}
	}
	
/**
 * The asynctask class to parse the xml file and display the content on the screen
 */
	private class LoadFeedTask extends AsyncTask<URL, Void, List<FeedItem>> {

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(FeedItemListActivity.this,
					"Please wait...", "Loading");
			progress.setIcon(R.drawable.loading);
		}

		@Override
		protected List<FeedItem> doInBackground(URL... params) {
			XmlParser parser = new XmlParser();
			try {
				feedItemList = parser.parse(params[0].openStream());
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return feedItemList;
		}

		@Override
		protected void onPostExecute(List<FeedItem> result) {
			ArrayAdapter<FeedItem> adapter = new ArrayAdapter<FeedItem>(
					getBaseContext(), android.R.layout.simple_list_item_1,
					result);
			setListAdapter(adapter);
			progress.dismiss();
			
		}
	}
	
/**
 * Open the chosen article in the browser
 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String content = feedItemList.get(position).getLink();
		Intent openFeed = new Intent(Intent.ACTION_VIEW);
		openFeed.setData(Uri.parse(content));
		startActivity(openFeed);
	}
}
