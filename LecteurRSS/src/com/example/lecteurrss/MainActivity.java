/**
 * This is the MainActivity class.
 * @author Timothée Nguyen && Etienne Nguyen
 */

package com.example.lecteurrss;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {
	private Dialog dialog;
	private EditText rssName, rssUrl;
	private Button saveBtnDialog, canBtn;
	private SqlHandler sqlHandler;
	private ListView lv;
	private ArrayList<RSSFeeds> feedList;
	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.listView1);
		sqlHandler = new SqlHandler(this);
		showList();
		lv.setOnItemClickListener(this);

	}

/**
 * Decide what happens when clicking on an item of the listview. Open the second activity and parse the articles
 */
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		ArrayList<RSSFeeds> feedList = getFeedList();
		String feedName = feedList.get(position).getFeedName();
		String feedLink = feedList.get(position).getFeedLink();
		Intent showFeeds = new Intent(MainActivity.this,
				FeedItemListActivity.class);
		showFeeds.putExtra("name", feedName);
		showFeeds.putExtra("link", feedLink);
		startActivity(showFeeds);
	}
	
/**
 * Create the context menu items
 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		feedList = getFeedList();
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle(feedList.get(info.position).toString());
		String[] menuItems = getResources().getStringArray(R.array.menuItems);
		for (int i = 0; i < menuItems.length; i++) {
			menu.add(0,
					Integer.parseInt(feedList.get(info.position).getFeedId()),
					0, menuItems[i]);
		}

	}
/**
 * Decide what to do when an item of the context menu is clicked
 */
	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		// AdapterView.AdapterContextMenuInfo info =
		// (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		String[] menuItems = getResources().getStringArray(R.array.menuItems);
		
		// Update the name of the feed
		if (item.getTitle().equals(menuItems[0])) {
//			Log.d("edit", "edit item number " + item.getItemId());
			dialog = new Dialog(MainActivity.this);
			dialog.setTitle("Edit name");
			dialog.setContentView(R.layout.dialog_edit_name);
			rssName = (EditText) dialog.findViewById(R.id.editTextName);
			saveBtnDialog = (Button) dialog.findViewById(R.id.saveBtnDialog);
			saveBtnDialog.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// Gather input data into variables
					String rssNameText = rssName.getText().toString();

					if (!rssNameText.isEmpty()) {
						query = "UPDATE feed SET name='" + rssNameText
								+ "' WHERE id=" + item.getItemId() + ";";

						sqlHandler.executeQuery(query);
						showList();
						dialog.dismiss();
					} else {
						rssName.setHint("Required field");
					}
				}
			});

			dialog.show();
		}
		
		//Update the link of the feed
		if (item.getTitle().equals(menuItems[1])) {
			Log.d("edit", "edit item number " + item.getItemId());
			dialog = new Dialog(MainActivity.this);
			dialog.setTitle("Edit url");
			dialog.setContentView(R.layout.dialog_edit_url);
			rssUrl = (EditText) dialog.findViewById(R.id.editTextUrl);
			saveBtnDialog = (Button) dialog.findViewById(R.id.saveBtnDialog);
			saveBtnDialog.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// Gather input data into variables
					String rssUrlText = rssUrl.getText().toString();
					if (!rssUrlText.isEmpty()) {
						if(!rssUrlText.contains("http://")){
							rssUrlText = "http://" + rssUrlText;
						}

						query = "UPDATE feed SET link='" + rssUrlText
								+ "' WHERE id=" + item.getItemId() + ";";

						sqlHandler.executeQuery(query);
						showList();
						dialog.dismiss();
					} else {
						rssName.setHint("Required field");
					}

				}
			});

			dialog.show();

		}
		// Delete the feed from the database
		if (item.getTitle().equals(menuItems[2])) {
			// Log.d("delete", "delete item number " + item.getItemId());
			query = "DELETE FROM feed WHERE id=" + item.getItemId() + ";";
			sqlHandler.executeQuery(query);
			showList();
		}
		return true;
	}
/**
 * Display the list of feed on the screen
 */
	private void showList() {
		FeedListAdapter feedListAdapter = new FeedListAdapter(
				MainActivity.this, getFeedList());
		lv.setAdapter(feedListAdapter);
		registerForContextMenu(lv);
	}
/**
 * Get the list of saved feeds in the database
 * @return the list of feeds
 */
	public ArrayList<RSSFeeds> getFeedList() {
		feedList = new ArrayList<RSSFeeds>();
		feedList.clear();
		query = "SELECT * FROM feed";
		Cursor c1 = sqlHandler.selectQuery(query);
		if (c1 != null && c1.getCount() != 0) {
			if (c1.moveToFirst()) {
				do {
					RSSFeeds feedListItems = new RSSFeeds();

					feedListItems.setFeedId(c1.getString(c1
							.getColumnIndex("id")));
					feedListItems.setFeedName(c1.getString(c1
							.getColumnIndex("name")));
					feedListItems.setFeedLink(c1.getString(c1
							.getColumnIndex("link")));
					feedList.add(feedListItems);

				} while (c1.moveToNext());
			}
		}
		c1.close();
		return feedList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Handle what happens when clicking on action bar's buttons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_add:
			// Create a dialog window
			dialog = new Dialog(MainActivity.this);

			// Place the dialog's UI
			dialog.setContentView(R.layout.dialog);
			rssName = (EditText) dialog.findViewById(R.id.rssName);
			rssUrl = (EditText) dialog.findViewById(R.id.rssUrl);
			saveBtnDialog = (Button) dialog.findViewById(R.id.saveBtnDialog);
			canBtn = (Button) dialog.findViewById(R.id.canBtnDialog);
			dialog.setTitle("New RSS feed - link to XML file");

			// Decide actions to do when clicking on the save button
			saveBtnDialog.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Gather input data into variables
					String rssNameText = rssName.getText().toString();
					String rssUrlText = rssUrl.getText().toString()
							.toLowerCase();

					if (!rssNameText.isEmpty() && !rssUrlText.isEmpty()) {
						// Add http:// if the url does not contain it
						if (!rssUrlText.contains("http://")) {
							String newUrl = "http://" + rssUrlText;
							query = "INSERT INTO feed (name, link) VALUES ('"
									+ rssNameText + "','" + newUrl + "');";
						} else {
							query = "INSERT INTO feed (name, link) VALUES ('"
									+ rssNameText + "','" + rssUrlText + "');";
						}
						sqlHandler.executeQuery(query);
						showList();
						dialog.dismiss();
					} else {
						rssName.setHint("Required field");
						rssUrl.setHint("Required field");
					}

				}
			});

			// Decide actions to do when clicking on the cancel button
			canBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Make the dialog window disappear
					dialog.dismiss();
				}
			});
			// Show the dialog window
			dialog.show();
			break;

		case R.id.action_settings:
			String query = "DELETE FROM feed;";
			sqlHandler.executeQuery(query);
			showList();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
