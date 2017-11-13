package ca.bcit.ass3.chu_stannus_liu;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by E on 2017-11-11.
 */

public class MainActivity extends AppCompatActivity {

    private DBHelper helper;
    private ListView lv;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        helper = new DBHelper(this);

        int eventID = 0;
        db = helper.getReadableDatabase();
        Cursor eventIdCursor = helper.getAllEvents(db);
        if (eventIdCursor.moveToFirst()) {
            eventID = eventIdCursor.getInt(0);
        }
        eventIdCursor.close();

        if(eventID == 0) {
            seedData();
        }

        final Button createEventButton = (Button) findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(intent);

            }
        });

        final Button eventListButton = (Button) findViewById(R.id.eventListButton);
        eventListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayEventListActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.searchEvent).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createEvent:
                Intent i = new Intent(this, CreateEventActivity.class);
                startActivity(i);
                return true;
            case R.id.goHome:
                Intent i3 = new Intent(this, MainActivity.class);
                startActivity(i3);
                return true;
            case R.id.aboutApp:
                Intent i4 = new Intent(this, AboutAppActivity.class);
                startActivity(i4);
                return true;
            case R.id.exitApp:
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void seedData() {

        SQLiteDatabase db;

        Event event = new Event("Christmas party", "December 25, 2017", "3:00pm");
        Item[] items = {
                new Item("Paper plates", "Pieces", 20),
                new Item("Paper cups", "Pieces", 30),
                new Item("Napkins", "Pieces", 100),
                new Item("Beer", "6 Packs", 5),
                new Item("Pop", "2 Liter bottles", 3),
                new Item("Pizza", "Large", 3),
                new Item("Peanuts", "Grams", 200)
        };

        db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            helper.insertEvent(db, event);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        addItemsToDB(event, items);
    }

    public void addItemsToDB(Event event, Item[] items) {

        SQLiteDatabase db;

        int eventID = 0;
        db = helper.getReadableDatabase();
        Cursor eventIdCursor = helper.retrieveEventId(db, event.getName());
        if (eventIdCursor.moveToFirst()) {
            eventID = eventIdCursor.getInt(0);
        }
        eventIdCursor.close();

            db = helper.getWritableDatabase();
            db.beginTransaction();
            try {
                for (Item item : items) {
                    helper.insertItemForEvent(db, eventID, item);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }

    }
}
