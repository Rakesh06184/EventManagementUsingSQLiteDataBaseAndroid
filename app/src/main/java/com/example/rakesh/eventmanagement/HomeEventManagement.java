package com.example.rakesh.eventmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.rakesh.eventmanagement.eventmanagement.AddEvent;
import com.example.rakesh.eventmanagement.eventmanagement.DeleteEvent;
import com.example.rakesh.eventmanagement.eventmanagement.GetAllEvent;
import com.example.rakesh.eventmanagement.eventmanagement.UpdateEvent;

/**
 * Created by rakesh on 30/12/15.
 */
public class HomeEventManagement extends AppCompatActivity  {

    private Button mInsertEvent,mDeleteEvent,mGetAllEvent,mUpdateEvent,mFacebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mInsertEvent = (Button)findViewById(R.id.insert_event);
        mDeleteEvent = (Button)findViewById(R.id.delete_event);
        mGetAllEvent = (Button)findViewById(R.id.get_all_event);
        mUpdateEvent = (Button)findViewById(R.id.update_event);
        mFacebookButton = (Button)findViewById(R.id.facebook);

        mInsertEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEventManagement.this,AddEvent.class);
                startActivity(intent);
            }
        });

        mUpdateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEventManagement.this,UpdateEvent.class);
                startActivity(intent);
            }
        });

        mGetAllEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEventManagement.this, GetAllEvent.class);
                startActivity(intent);
            }
        });

        mDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEventManagement.this,DeleteEvent.class);
                startActivity(intent);
            }
        });

        //implemented opened url and action send
        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.facebook.com"));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

}
