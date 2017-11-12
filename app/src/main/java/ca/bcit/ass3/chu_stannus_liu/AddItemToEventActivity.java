package ca.bcit.ass3.chu_stannus_liu;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by E on 2017-10-31.
 */

public class AddItemToEventActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DBHelper helper;
    private Cursor cursor;
    private Event event;
    int eventNum;
    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        Intent intent = getIntent();
        eventNum = intent.getIntExtra("event", 0);
        eventName = intent.getStringExtra("eventName");

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = getItemFromInputs();
                addItemToDB(item);

                Intent intent = new Intent(AddItemToEventActivity.this, DisplayItemListActivity.class);
                intent.putExtra("event", eventNum);
                intent.putExtra("eventName", eventName);

                intent.putExtra("itemName", item.getName());
                intent.putExtra("itemUnit", item.getUnit());
                intent.putExtra("itemQuantity", item.getQuantity());

                startActivity(intent);
            }
        });
    }

    public void addItemToDB(Item item) {

        SQLiteDatabase db;

        db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            helper.insertItemForEvent(db, eventNum, item);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddItemToEventActivity.this,
                DisplayEventDetailActivity.class);
        intent.putExtra("event", eventNum);
        intent.putExtra("eventName", eventName);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
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
}
