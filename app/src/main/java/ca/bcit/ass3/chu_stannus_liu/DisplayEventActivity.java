package ca.bcit.ass3.chu_stannus_liu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by E on 2017-10-31.
 */

public class DisplayEventActivity extends Activity {

    private SQLiteDatabase db;
    private DBHelper helper;
    private Cursor cursor;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_output);

        helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        String eventDate = intent.getStringExtra("eventDate");
        int eventTime = intent.getIntExtra("eventTime", 0);

        event = new Event(eventName, eventDate, eventTime);
        //addEventToDB(event);

        TextView nameField = (TextView) findViewById(R.id.outputName);
        TextView dateField = (TextView) findViewById(R.id.outputDate);
        TextView timeField = (TextView) findViewById(R.id.outputTime);


        Cursor cursor = helper.getEvent(db, event);
        String dbName = null;
        String dbDate = null;
        int dbTime = 0;
        if (cursor.moveToFirst() ) {
            dbName = cursor.getString(1);
            dbDate = cursor.getString(2);
            dbTime = Integer.parseInt(cursor.getString(3));
        }
        cursor.close();

        nameField.setText(dbName);
        dateField.setText(dbDate);
        timeField.setText(dbTime);
    }

    public void addEventToDB(Event event) {

        SQLiteDatabase db;

        db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            helper.insertEvent(db, event);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }
}
