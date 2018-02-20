package com.example.rakesh.eventmanagement.eventmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.rakesh.eventmanagement.eventitem.EventItems;
import com.example.rakesh.eventmanagement.R;
import com.example.rakesh.eventmanagement.HomeEventManagement;
import com.example.rakesh.eventmanagement.adapter.RecyclerViewEvent;
import com.example.rakesh.eventmanagement.database.dbhelper.DataBaseHelper;

import java.util.ArrayList;

public class GetAllEvent extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<EventItems> mArrayList;
    private RecyclerViewEvent mRecyclerViewEvent;
    private Cursor mCursor;
    private DataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycleview);
        mArrayList = new ArrayList<EventItems>();
        mDataBaseHelper = new DataBaseHelper(GetAllEvent.this);
        mCursor = mDataBaseHelper.getAllData();

        int i = 0;
        mCursor.moveToFirst();
        Log.i("tag", "" + mCursor.getCount());

        while (!mCursor.isAfterLast()){
            EventItems eventItems = new EventItems();

            eventItems.setId(mCursor.getString(mCursor.getColumnIndexOrThrow(DataBaseHelper.DataBaseAdapter.UID)));
            eventItems.setEventName(mCursor.getString(mCursor.getColumnIndexOrThrow(DataBaseHelper.DataBaseAdapter.NAME)));
            eventItems.setEventLocation(mCursor.getString(mCursor.getColumnIndexOrThrow(DataBaseHelper.DataBaseAdapter.LOCATION)));
            eventItems.setEventDescription(mCursor.getString(mCursor.getColumnIndexOrThrow(DataBaseHelper.DataBaseAdapter.DESCRIPTION)));
            eventItems.setEventStartDate(mCursor.getString(mCursor.getColumnIndexOrThrow(DataBaseHelper.DataBaseAdapter.START_DATE)));
            eventItems.setEventEndDate(mCursor.getString(mCursor.getColumnIndexOrThrow(DataBaseHelper.DataBaseAdapter.END_DATE)));
            eventItems.setEventTime(mCursor.getString(mCursor.getColumnIndexOrThrow(DataBaseHelper.DataBaseAdapter.TIME)));

            mArrayList.add(i,eventItems);
            i++;
            mCursor.moveToNext();
        }
        mRecyclerViewEvent = new RecyclerViewEvent(GetAllEvent.this,mArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(GetAllEvent.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewEvent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, HomeEventManagement.class);
        startActivity(intent);
        finish();

        return super.onOptionsItemSelected(item);
    }
}
