package com.vinkas.reminders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import vinkas.app.NavigationDrawerActivity;
import vinkas.io.reminders.ListItem;
import vinkas.io.reminders.open.Database;
import vinkas.io.reminders.open.List;

/**
 * Created by Vinoth on 6-5-16.
 */
public class MainActivity extends NavigationDrawerActivity {

    public List getReminders() {
        return getDatabase().getReminders();
    }

    @Override
    public Application getApp() {
        return (Application) super.getApp();
    }

    @Override
    public Database getDatabase() {
        return (Database) super.getDatabase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
        setMenu(R.menu.activity_main);
        setNavigationMenu(R.menu.activity_main_drawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return super.onNavigationItemSelected(item);
    }

    private ListItem reminder;

    DatePickerDialog dpDialog;
    TimePickerDialog tpDialog;
    EditText etTitle, etDate, etTime;

    @Override
    public void setContent(View content) {
        super.setContent(content);
        popHolder = (RelativeLayout) getContent().findViewById(R.id.popHolder);
        etTitle = (EditText) getContent().findViewById(R.id.etTitle);
        etDate = (EditText) getContent().findViewById(R.id.etDate);
        etTime = (EditText) getContent().findViewById(R.id.etTime);
        etDate.setInputType(InputType.TYPE_NULL);
        etTime.setInputType(InputType.TYPE_NULL);
        dpDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.year = year;
                month = monthOfYear;
                day = dayOfMonth;
                etDate.setText(dayOfMonth + " / " + monthOfYear + " / " + year);
                tpDialog.show();
            }
        }, 2016, 10, 10);
        tpDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                etTime.setText(hourOfDay + " : " + minute);
            }
        }, 1, 1, false);
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dpDialog.show();
                }
            }
        });
        etTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    tpDialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.add) {
            addReminder(v);
        }
    }

    public void newReminder() {
        etTitle.setText("");
        etDate.setText("");
        etTime.setText("");
        year = month = day = hour = min = 0;
    }

    private int year, month, day, hour, min;

    public void addReminder(View v) {
        reminder = getReminders().create(etTitle.getText().toString(), day, month, year, hour, min);
        popHolder.setVisibility(View.GONE);
        getFab().setVisibility(View.VISIBLE);
    }

    RelativeLayout popHolder;

    @Override
    public void onFabClick(View v) {
        newReminder();
        popHolder.setVisibility(View.VISIBLE);
        v.setVisibility(View.GONE);
    }

}