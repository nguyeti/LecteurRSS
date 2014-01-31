/**
 * This class handles SQL query.
 * @author Timothée Nguyen & Etienne Nguyen
 */

package com.example.lecteurrss;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlHandler {
	Context context;
	SQLiteDatabase sqlDatabase;
	MySQLiteHelper dbHelper;

	public SqlHandler(Context context) {
		dbHelper = new MySQLiteHelper(context);
		sqlDatabase = dbHelper.getWritableDatabase();
	}

	public void executeQuery(String query) {
		try {

			if (sqlDatabase.isOpen()) {
				sqlDatabase.close();
			}

			sqlDatabase = dbHelper.getWritableDatabase();
			sqlDatabase.execSQL(query);

		} catch (Exception e) {
			System.out.println("DATABASE ERROR " + e);
		}

	}

	public Cursor selectQuery(String query) {
		Cursor c1 = null;
		try {

			if (sqlDatabase.isOpen()) {
				sqlDatabase.close();
			}
			sqlDatabase = dbHelper.getWritableDatabase();
			c1 = sqlDatabase.rawQuery(query, null);

		} catch (Exception e) {
			System.out.println("DATABASE ERROR " + e);
		}
		return c1;

	}
}
