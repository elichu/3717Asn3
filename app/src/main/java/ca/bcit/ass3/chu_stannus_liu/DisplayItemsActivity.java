package ca.bcit.ass3.chu_stannus_liu;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * Created by E on 2017-11-02.
 */

public class DisplayItemsActivity extends ListActivity {

    private DBHelper dbHelper;
    private SimpleCursorAdapter adapter;
    private String[][] items;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final LoaderManager manager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_output);

        Intent intent = getIntent();
        final Event event = intent.getParcelableExtra("event");

       // lv = (ListView)findViewById(R.id.list);
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ItemsAdapter adapter = new ItemsAdapter(this, dbHelper.getAllItems(db, event.getEventId()));
        setListAdapter(adapter);

    }
}
