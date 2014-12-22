package com.aicre.wuliuapp.app.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by wei on 14-12-22.
 */
@DatabaseTable
public class Contacts {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int mid;
    @DatabaseField
    private String name;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Contacts(int mid, String name, String phone, String time) {
        this.mid = mid;
        this.name = name;
        this.phone = phone;
        this.time = time;
    }

    public Contacts() {
    }
}
