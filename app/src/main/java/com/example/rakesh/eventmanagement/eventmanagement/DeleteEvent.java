package com.example.rakesh.eventmanagement.eventmanagement;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by rakesh on 29/12/15.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;

import com.example.rakesh.eventmanagement.eventitem.EventItems;
import com.example.rakesh.eventmanagement.R;
import com.example.rakesh.eventmanagement.HomeEventManagement;
import com.example.rakesh.eventmanagement.adapter.RecyclerViewEvent;
import com.example.rakesh.eventmanagement.database.dbhelper.DataBaseHelper;

import java.util.ArrayList;

public class DeleteEvent extends AppCompatActivity {

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
        mDataBaseHelper = new DataBaseHelper(DeleteEvent.this);
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

        mRecyclerViewEvent = new RecyclerViewEvent(DeleteEvent.this,mArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DeleteEvent.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewEvent);

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // callback for drag-n-drop, false to skip this feature
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DeleteEvent.this);
                alertDialogBuilder.setIcon(R.drawable.ic_delete_black_24dp);
                alertDialogBuilder.setTitle("Are you sure that you want to remove it?");

                alertDialogBuilder.setPositiveButton("remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        mDataBaseHelper.deleteRow(mArrayList.get(position).getId());
                        mArrayList.remove(position);
                        mRecyclerViewEvent.notifyDataSetChanged();
                    }
                });
                alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearView(mRecyclerView, viewHolder);
                        mRecyclerViewEvent.notifyDataSetChanged();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mRecyclerViewEvent);
        Log.i("Event Items", "size = " +mRecyclerViewEvent.getItemCount());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, HomeEventManagement.class);
        startActivity(intent);
        finish();

        return super.onOptionsItemSelected(item);
    }
}
