package ca.bcit.ass3.chu_stannus_liu;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by E on 2017-11-02.
 */

public class ignore1 extends ListActivity {

    private DBHelper dbHelper;
    private SimpleCursorAdapter adapter;
    private String[][] items;
    private String TAG = DisplayItemListActivity.class.getSimpleName();
    private ListView lv;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final LoaderManager manager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.zignore_output);

        Intent intent = getIntent();
        final Event event = intent.getParcelableExtra("event");

       // lv = (ListView)findViewById(R.id.list);
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ignore2 adapter = new ignore2(this, dbHelper.getAllItems(db, event.getEventId()));
        setListAdapter(adapter);

    }
}


//        ArrayList events = new ArrayList();
//        Event event1 = new Event("Halloween party", "Oct 30, 2017", "6:30 PM");
//        Event event2 = new Event("Christmas Party", "December 20, 2017", "12:30 PM");
//        Event event3 = new Event("New Year Eve", "December 31, 2017", "8:00PM");
//
//        events.add(event1);
//        events.add(event2);
//        events.add(event3);

//        ArrayList items = new ArrayList();
//        Item item1 = new Item("coca cola", "6 pack", 5);
//        Item item2 = new Item("pizza", "large", 3);
//        Item item3 = new Item("potato chips", "large bag", 5);
//        Item item4 = new Item("napkins", "pieces", 100);
//        items.add(item1);
//        items.add(item2);

//        int id = 0;
//        Cursor eventIdCursor = helper.retrieveEventId(db, event1String);
//        if (eventIdCursor.moveToFirst()) {
//            id = eventIdCursor.getInt(0);
//        }
//        eventIdCursor.close();
//db.endTransaction();

//                TextView nameField = (TextView) findViewById(R.id.outputName);
//                TextView dateField = (TextView) findViewById(R.id.outputDate);
//                TextView timeField = (TextView) findViewById(R.id.outputTime);


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