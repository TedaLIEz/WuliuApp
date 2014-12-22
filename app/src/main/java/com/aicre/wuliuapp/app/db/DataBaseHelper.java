package com.aicre.wuliuapp.app.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aicre.wuliuapp.app.dao.Contacts;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DB_NAME = "Wuliu.db";
	private static final int DB_VSERSION = 1;
	private Dao<Contacts, Integer> contactsDao = null;
	private Context mContext;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VSERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTable(connectionSource, Contacts.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {

	}

	@Override
	public void close() {
		super.close();
		contactsDao = null;
	}

	public Dao<Contacts, Integer> getHelloDataDao()
			throws SQLException {
		if (contactsDao == null) {
		    contactsDao = getDao(Contacts.class);
		}
		return contactsDao;
	}

}
