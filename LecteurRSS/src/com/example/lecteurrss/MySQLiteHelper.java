/**
 * This class is responsible for creating the database.
 * @author Timothée Nguyen & Etienne Nguyen
 */

package com.example.lecteurrss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	// Database name
	private static final String DATABASE_NAME = "Feeds.db";
	// Table name
	private static final String TABLE_NAME = "feed";
	// Database version
	private static final int DATABASE_VERSION = 1;
	// Database columns
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_LINK = "link";
	// Create table statement
	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NAME + " TEXT NOT NULL, " + COLUMN_LINK
			+ " TEXT NOT NULL);";
	// Insert some RSSfeed
	private static final String INSERT_TABLE = "INSERT INTO feed (name, link) VALUES ('CNN','http://rss.cnn.com/rss/edition.rss'), ('ESPN','http://sports.espn.go.com/espn/rss/news'), ('Le Figaro', 'http://rss.lefigaro.fr/lefigaro/laune'), ('NBA', 'http://www.nba.com/rss/nba_rss.xml'), ('BFM TV', 'http://www.bfmtv.com/rss/planete.rss'), ('XDA Developers','http://feeds.feedburner.com/xda-developers/ShsH');";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		db.execSQL(INSERT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(db);
	}
}
