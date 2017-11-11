package ca.bcit.ass3.chu_stannus_liu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by E on 2017-10-31.
 */

public class AddItemToEventActivity extends Activity {

    private SQLiteDatabase db;
    private DBHelper helper;
    private Cursor cursor;
    private Event event;
    int event1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        Intent intent = getIntent();
        event1 = intent.getIntExtra("event", 0);

        //event1 = intent.getStringExtra("event");

//        String eventName = intent.getStringExtra("eventName");
//        String eventDate = intent.getStringExtra("eventDate");
//        String eventTime = intent.getStringExtra("eventTime");
//
//        event = new Event(eventName, eventDate, eventTime);
//        addEventToDB(event);
//
//        TextView nameField = (TextView) findViewById(R.id.outputName);
//        TextView dateField = (TextView) findViewById(R.id.outputDate);
//        TextView timeField = (TextView) findViewById(R.id.outputTime);
//
//        Cursor cursor = helper.getEvent(db, event);
//        String dbName = null;
//        String dbDate = null;
//        String dbTime = null;
//        if (cursor.moveToFirst() ) {
//            dbName = cursor.getString(1);
//            dbDate = cursor.getString(2);
//            dbTime = cursor.getString(3);
//        }
//        cursor.close();
//
//        nameField.setText(dbName);
//        dateField.setText(dbDate);
//        timeField.setText(dbTime);

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = getItemFromInputs();
                //event.addItem(item);
                //addItemToDB(event, item);

                Intent intent = new Intent(AddItemToEventActivity.this, DisplayItemListActivity.class);
                intent.putExtra("event", event1);

                intent.putExtra("itemName", item.getName());
                intent.putExtra("itemUnit", item.getUnit());
                intent.putExtra("itemQuantity", item.getQuantity());

                startActivity(intent);
            }
        });
    }

//    public void makeListview() {
//
//        helper = new DBHelper(this);
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        DisplayItemListAdapter adapter = new DisplayItemListAdapter(this, helper.getAllItems(db, event.getEventId()));
//        setListAdapter(adapter);
//    }

    public void addItemToDB(Event event, Item item) {

        SQLiteDatabase db;

        db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            helper.insertItemForEvent(db, event, item);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
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

        attachEventId(event);
    }

    public void attachEventId(Event event) {
        Cursor cursor = helper.retrieveEventId(db, event);
        if (cursor.moveToFirst() ) {
            event.setEventId(cursor.getString(0));
        }
        cursor.close();
    }

    public Item getItemFromInputs() {

        Item item = null;

        EditText eItem = (EditText) findViewById(R.id.addItem);
        EditText eUnit = (EditText) findViewById(R.id.addUnit);
        EditText eQuantity = (EditText) findViewById(R.id.addQuantity);

        if(eItem.getText().toString().isEmpty() || eUnit.getText().toString().isEmpty()
                || eQuantity.getText().toString().isEmpty()) {
            return item;
        }

        String itemName = eItem.getText().toString();
        String itemUnit = eUnit.getText().toString();
        int itemQuantity = Integer.parseInt(eQuantity.getText().toString());

        item = new Item(itemName, itemUnit, itemQuantity);

        return item;

    }

//    public void addItemToEvent() {
//        Item item = getItemFromInputs();
//        event.addItem(item);
//    }
}
