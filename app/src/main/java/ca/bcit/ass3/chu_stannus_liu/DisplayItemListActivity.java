package ca.bcit.ass3.chu_stannus_liu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class DisplayItemListActivity extends AppCompatActivity {

    private DBHelper helper;
    private ListView lv;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        Intent intent = getIntent();
        String event1String = intent.getStringExtra("event");

        String itemName = intent.getStringExtra("itemName");
        String itemUnit = intent.getStringExtra("itemUnit");
        int itemQuantity = intent.getIntExtra("itemQuantity", 0);

        //Button button = (Button) findViewById(R.id.button);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float inputDecimal = 0;
//                EditText eName = (EditText) findViewById(R.id.addEventName);
//                EditText eDate = (EditText) findViewById(R.id.addEventDate);
//                EditText eTime = (EditText) findViewById(R.id.addEventTime);
//
//                if(eName.getText().toString().isEmpty() || eDate.getText().toString().isEmpty()
//                        || eTime.getText().toString().isEmpty()) {
//                    return;
//                }
//
//                String eventName = eName.getText().toString();
//                String eventDate = eDate.getText().toString();
//                String eventTime = eTime.getText().toString();

//                final String eventDetails [][] = {
//                        {"Halloween party", "Oct 30, 2017", "6:30 PM"},
//                        {"Christmas Party", "December 20, 2017", "12:30 PM"},
//                        {"New Year Eve", "December 31, 2017", "8:00PM"}
//                };

//        ArrayList events = new ArrayList();
        Event event1 = new Event("Halloween party", "Oct 30, 2017", "6:30 PM");
        Event event2 = new Event("Christmas Party", "December 20, 2017", "12:30 PM");
        Event event3 = new Event("New Year Eve", "December 31, 2017", "8:00PM");
//
//        events.add(event1);
//        events.add(event2);
//        events.add(event3);


//        ArrayList items = new ArrayList();
//        Item item1 = new Item("coca cola", "6 pack", 5);
//        Item item2 = new Item("pizza", "large", 3);
//        Item item3 = new Item("potato chips", "large bag", 5);
//        Item item4 = new Item("napkins", "pieces", 100);
//
//
//        items.add(item1);
//        items.add(item2);


        helper = new DBHelper(this);

        db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            helper.insertEvent(db, event1);
            helper.insertEvent(db, event2);
            helper.insertEvent(db, event3);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        Item item1 = new Item(itemName, itemUnit, itemQuantity);

        db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            helper.insertItemForEvent(db, event1String, item1);
//            helper.insertItemForEvent(db, event1String, item2);
//            helper.insertItemForEvent(db, event1String, item3);
//            helper.insertItemForEvent(db, event1String, item4);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db = helper.getReadableDatabase();
        int id = 0;
        Cursor eventIdCursor = helper.retrieveEventId(db, event1String);
        if (eventIdCursor.moveToFirst()) {
            id = eventIdCursor.getInt(0);
        }
        eventIdCursor.close();
        //db.endTransaction();

//                TextView nameField = (TextView) findViewById(R.id.outputName);
//                TextView dateField = (TextView) findViewById(R.id.outputDate);
//                TextView timeField = (TextView) findViewById(R.id.outputTime);

        lv = (ListView) findViewById(R.id.list1);

//        EventAdapter adapter = new EventAdapter(DisplayItemListActivity.this, helper.getAllEvents(db));
//        lv.setAdapter(adapter);

        EventAdapter adapter2 = new EventAdapter(DisplayItemListActivity.this, helper.getAllItems(db, String.valueOf(id)));
        lv.setAdapter(adapter2);

//
//                Cursor cursor = helper.getEvent(db, event1);
//                String dbName = null;
//                String dbDate = null;
//                String dbTime = null;
//                if (cursor.moveToFirst() ) {
//                    dbName = cursor.getString(1);
//                    dbDate = cursor.getString(2);
//                    dbTime = cursor.getString(3);
//                }
//                cursor.close();
//
//                nameField.setText(dbName);
//                dateField.setText(dbDate);
//                timeField.setText(dbTime);

//                Event event = new Event(eventName, eventDate, eventTime);

        //Intent intent = new Intent(DisplayItemListActivity.this, AddItemToEventActivity.class);
        //intent.putParcelableArrayListExtra(eventDetails);
//                intent.putExtra("eventName", eventName);
//                intent.putExtra("eventDate", eventDate);
//                intent.putExtra("eventTime", eventTime);

        //startActivity(intent);
    }

    // });

}
