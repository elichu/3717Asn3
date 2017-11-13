package ca.bcit.ass3.chu_stannus_liu;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DisplayItemListActivity extends AppCompatActivity {

    private DBHelper helper;
    private ListView lv;
    private SQLiteDatabase db;
    int eventNum;
    String eventName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        helper = new DBHelper(this);

        Intent intent = getIntent();
        eventNum = intent.getIntExtra("event", 0);
        eventName = intent.getStringExtra("eventName");

        final int itemID = intent.getIntExtra("itemID", 0);

        if (intent.getExtras().containsKey("changeItemName") ||
                intent.getExtras().containsKey("changeItemUnit") ||
                intent.getExtras().containsKey("changeItemQuantity") //||
                ) {
            String itemName = intent.getStringExtra("changeItemName");
            String itemUnit = intent.getStringExtra("changeItemUnit");
            int itemQuantity = intent.getIntExtra("changeItemQuantity", 0);

            Item item1 = new Item(itemName, itemUnit, itemQuantity);

            db = helper.getWritableDatabase();
            db.beginTransaction();
            try {
                helper.updateItem(db, item1, eventNum, itemID);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        lv = (ListView) findViewById(R.id.list1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = (Cursor) adapterView.getAdapter().getItem(i);
                c.moveToPosition(i);
                Intent intent = new Intent(DisplayItemListActivity.this, EditItemActivity.class);

                intent.putExtra("event", eventNum);
                intent.putExtra("eventName", eventName);

                intent.putExtra("itemName", c.getString(1));
                intent.putExtra("itemUnit", c.getString(2));
                intent.putExtra("itemQuantity", Integer.parseInt(c.getString(3)));

                startActivity(intent);

            }
        });

        db = helper.getReadableDatabase();
        DisplayItemListAdapter adapter2 = new DisplayItemListAdapter(DisplayItemListActivity.this,
                helper.getAllItems(db, String.valueOf(eventNum)));
        lv.setAdapter(adapter2);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayItemListActivity.this, DisplayEventDetailActivity.class);
        intent.putExtra("event", eventNum);
        intent.putExtra("eventName", eventName);
        startActivity(intent);
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
}
