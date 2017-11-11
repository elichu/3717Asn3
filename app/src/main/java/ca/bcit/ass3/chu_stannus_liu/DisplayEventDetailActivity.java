package ca.bcit.ass3.chu_stannus_liu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by E on 2017-11-10.
 */

public class DisplayEventDetailActivity extends AppCompatActivity {


    private DBHelper helper;
    private ListView lv;
    private SQLiteDatabase db;
    int eventNum;
    String eventName;
    int eventID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        helper = new DBHelper(this);

        Intent intent = getIntent();
        eventNum = intent.getIntExtra("event", 0);
        eventName = intent.getStringExtra("eventName");

        db = helper.getReadableDatabase();
       // db.beginTransaction();
        Cursor eventIdCursor = helper.retrieveEventId(db, eventName);
        if (eventIdCursor.moveToFirst()) {
            eventID = eventIdCursor.getInt(0);
        }
        eventIdCursor.close();

        if (intent.getExtras().containsKey("changeEventName") ||
                intent.getExtras().containsKey("changeEventDate") ||
                intent.getExtras().containsKey("changeEventTime")) {
            String changeEventName = intent.getStringExtra("changeEventName");
            String changeEventDate = intent.getStringExtra("changeEventDate");
            String changeEventTime = intent.getStringExtra("changeEventTime");

            Event event = new Event(changeEventName, changeEventDate, changeEventTime);

            db = helper.getWritableDatabase();
            db.beginTransaction();
            try {
                helper.updateEvent(db, event, eventID);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        final TextView eventName = (TextView) findViewById(R.id.eventName);
        final TextView eventDate = (TextView) findViewById(R.id.eventDate);
        final TextView eventTime = (TextView) findViewById(R.id.eventTime);

        //helper = new DBHelper(this);

        db = helper.getReadableDatabase();

        Cursor cursor = helper.getEvent(db, eventID);
        String dbName = null;
        String dbDate = null;
        String dbTime = null;
        if (cursor.moveToFirst() ) {
            dbName = cursor.getString(1);
            dbDate = cursor.getString(2);
            dbTime = cursor.getString(3);
        }
        cursor.close();

        eventName.setText(dbName);
        eventDate.setText(dbDate);
        eventTime.setText(dbTime);

        final Button getItemButton = (Button) findViewById(R.id.getItemButton);
        getItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Item item = getItemFromInputs();
                //event.addItem(item);
                //addItemToDB(event, item);

                Intent intent = new Intent(DisplayEventDetailActivity.this, DisplayItemListActivity.class);
                intent.putExtra("event", eventID);

//                intent.putExtra("itemName", item.getName());
//                intent.putExtra("itemUnit", item.getUnit());
//                intent.putExtra("itemQuantity", item.getQuantity());

                startActivity(intent);
            }
        });

        final Button addItemButton = (Button) findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayEventDetailActivity.this, AddItemToEventActivity.class);
                intent.putExtra("event", eventID);
                startActivity(intent);
            }
        });

        final Button editEventButton = (Button) findViewById(R.id.editEventButton);
        editEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DisplayEventDetailActivity.this, EditEventActivity.class);
                intent.putExtra("event", eventID);
                intent.putExtra("eventName", eventName.getText().toString());
                intent.putExtra("eventDate", eventDate.getText().toString());
                intent.putExtra("eventTime", eventTime.getText().toString());

                startActivity(intent);
            }
        });

        final Button deleteEventButton = (Button) findViewById(R.id.deleteEventButton);
        deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DisplayEventDetailActivity.this, DeleteEventActivity.class);
                intent.putExtra("event", eventID);
                startActivity(intent);
            }
        });

    }
}
