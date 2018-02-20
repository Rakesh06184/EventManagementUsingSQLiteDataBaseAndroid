package com.example.rakesh.eventmanagement.eventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakesh.eventmanagement.R;
import com.example.rakesh.eventmanagement.HomeEventManagement;
import com.example.rakesh.eventmanagement.database.dbhelper.DataBaseHelper;

public class UpdateEvent extends AppCompatActivity {

    private Button mSave,mClear;
    private EditText mEditOldName,mEditNewName;
    private DataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDataBaseHelper = new DataBaseHelper(this);
        mClear = (Button)findViewById(R.id.update_clear);
        mSave = (Button)findViewById(R.id.update_save);
        mEditNewName = (EditText)findViewById(R.id.new_edit_name);
        mEditOldName = (EditText)findViewById(R.id.old_edit_name);
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditOldName.setText("");
                mEditNewName.setText("");
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldName = mEditOldName.getText().toString();
                String newName = mEditNewName.getText().toString();
                mDataBaseHelper.updateName(oldName,newName);
                mEditOldName.setText("");
                mEditNewName.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this,HomeEventManagement.class));
        return super.onOptionsItemSelected(item);
    }
}
