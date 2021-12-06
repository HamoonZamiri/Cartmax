package com.example.b07_final_project;

import android.app.Activity;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public abstract class DataManager<T> {
    protected ArrayList<T> data;
    protected DatabaseReference ref;
    protected Activity activity;
    protected String UID;

    public abstract void updateDatabase(ArrayList<T> data);
    public abstract ArrayList<T> getData();

}
