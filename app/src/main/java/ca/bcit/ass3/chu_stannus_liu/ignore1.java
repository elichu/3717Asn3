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
