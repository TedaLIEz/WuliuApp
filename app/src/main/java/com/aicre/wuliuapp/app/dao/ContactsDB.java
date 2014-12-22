package com.aicre.wuliuapp.app.dao;

import android.content.Context;

import com.aicre.wuliuapp.app.db.DataBaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * Created by wei on 14-12-22.
 */
public class ContactsDB {
    private DataBaseHelper dataBaseHelper = null;

    public ContactsDB(Context ctx){
        if(dataBaseHelper == null){
            dataBaseHelper = OpenHelperManager.getHelper(ctx,DataBaseHelper.class);
        }
    }

    public void add(Contacts contact){
        RuntimeExceptionDao<Contacts,Integer> dao = dataBaseHelper.getRuntimeExceptionDao(Contacts.class);
        dao.create(contact);
    }

    public void deleteAll(){
        RuntimeExceptionDao<Contacts,Integer> dao = dataBaseHelper.getRuntimeExceptionDao(Contacts.class);

        dao.delete(getAll());
    }

    public List<Contacts> getAll(){
        RuntimeExceptionDao<Contacts,Integer> dao = dataBaseHelper.getRuntimeExceptionDao(Contacts.class);
        return dao.queryForAll();
    }

    protected void releaseDataHelper() {
		/*
		 * 释放资源
		 */
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }
}
