package com.example.rakesh.eventmanagement.eventmanagement;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.rakesh.eventmanagement.alarm.AlarmReceiverActivity;
import com.example.rakesh.eventmanagement.R;
import com.example.rakesh.eventmanagement.HomeEventManagement;
import com.example.rakesh.eventmanagement.database.dbhelper.DataBaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEvent extends AppCompatActivity implements View.OnClickListener {

    private EditText mInsertName, mInsertLocation, mInsertDescription;
    private Button mClear, mSave;
    private TextView mInsertStartDate, mInsertEndDate, mInsertTime;

    private android.support.v7.app.ActionBar mActionBar;
    private DataBaseHelper mDataBaseHelper;
    private SimpleDateFormat mSimpleDateFormat;
    private DatePickerDialog mFromDatePickerDialog, mToDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mInsertName = (EditText) findViewById(R.id.insert_name);
        mInsertLocation = (EditText) findViewById(R.id.insert_location);
        mInsertDescription = (EditText) findViewById(R.id.insert_details);
        mInsertStartDate = (TextView) findViewById(R.id.insert_start_date);
        mInsertEndDate = (TextView) findViewById(R.id.insert_end_date);
        mInsertTime = (TextView) findViewById(R.id.insert_time);

        mInsertStartDate.setInputType(InputType.TYPE_NULL);
        mInsertStartDate.requestFocus();
        mInsertEndDate.setInputType(InputType.TYPE_NULL);
        mInsertEndDate.requestFocus();

        mSave = (Button) findViewById(R.id.save);
        mClear = (Button) findViewById(R.id.clear);
        mDataBaseHelper = new DataBaseHelper(this);
        mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        setDateTimeField();
        clear();
        save();
        mInsertTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mInsertTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == mInsertStartDate) {
            mFromDatePickerDialog.show();
        } else if (view == mInsertEndDate) {
            mToDatePickerDialog.show();
        }
    }
    private void setDateTimeField() {
        mInsertStartDate.setOnClickListener(this);
        mInsertEndDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        mFromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                String date = dayOfMonth+"-" + (monthOfYear + 1)  + "-" + year;

               // mInsertStartDate.setText(mSimpleDateFormat.format(newDate.getTime()));

                mInsertStartDate.setText(date);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mToDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mInsertEndDate.setText(mSimpleDateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    public void clear() {
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInsertName.setText("");
                mInsertLocation.setText("");
                mInsertDescription.setText("");
                mInsertStartDate.setText("");
                mInsertEndDate.setText("");
                mInsertTime.setText("");
            }
        });
    }
    public void save() {

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eventName = mInsertName.getText().toString();
                String eventLocation = mInsertLocation.getText().toString();
                String eventDescription = mInsertDescription.getText().toString();
                String eventEndDate = mInsertEndDate.getText().toString();
                String eventStartDate = mInsertStartDate.getText().toString();
                String eventTime = mInsertTime.getText().toString();

                long id = mDataBaseHelper.insertElement(eventName, eventLocation, eventDescription, eventStartDate, eventEndDate, eventTime);

                if (id < 0) {
                    Toast.makeText(AddEvent.this, "Not successful insert", Toast.LENGTH_LONG).show();
                } else {
                    /*Toast.makeText(AddEvent.this, "successful insert " + "\n" + "name  " + eventName.toString() + "\n" + "Description is  " + eventDescription.toString() + "\n" +
                                    "Location is  " + eventLocation.toString() + "\n" + "Start date is  " + eventStartDate.toString() + "\n" +
                                    "End date  is  " + eventEndDate.toString() + "\n" +
                                    "time  is  " + eventTime.toString(),
                            Toast.LENGTH_LONG).show();*/

                    mInsertName.setText("");
                    mInsertLocation.setText("");
                    mInsertDescription.setText("");
                    mInsertStartDate.setText("");
                    mInsertEndDate.setText("");
                    mInsertTime.setText("");
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.layout), "Suucessfully", Snackbar.LENGTH_LONG).setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    snackbar.show();
                }
                if (id >= 0) {
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm");
                        String date = eventStartDate + " " + eventTime;
                        long setDate = simpleDateFormat.parse(date).getTime();

                        Calendar cal = Calendar.getInstance();
                        String currentDate = simpleDateFormat.format(cal.getTime());
                        long currDate = simpleDateFormat.parse(currentDate).getTime();

                        long AlarmTime = setDate - currDate;
                        Log.d("TAG", AlarmTime / (1000 * 60) + "");

                        Intent intent = new Intent(AddEvent.this, AlarmReceiverActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(AddEvent.this, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmTime, pendingIntent);

                    } catch (NumberFormatException ee) {

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, HomeEventManagement.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}








