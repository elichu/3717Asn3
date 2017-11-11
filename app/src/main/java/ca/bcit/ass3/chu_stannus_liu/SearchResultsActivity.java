package ca.bcit.ass3.chu_stannus_liu;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by E on 2017-11-11.
 */

public class SearchResultsActivity extends AppCompatActivity {

    private DBHelper helper;
    private ListView lv;
    private SQLiteDatabase db;

    ArrayList<String> eventlist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final LoaderManager manager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        helper = new DBHelper(this);

        lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = (Cursor) adapterView.getAdapter().getItem(i);
                c.moveToPosition(i);
                Intent intent = new Intent(SearchResultsActivity.this, DisplayEventDetailActivity.class);
                intent.putExtra("event", i+1);
                intent.putExtra("eventName", c.getString(1));
                startActivity(intent);

            }
        });
        db = helper.getReadableDatabase();

        DisplayEventListAdapter adapter = new DisplayEventListAdapter(SearchResultsActivity.this
                , helper.getAllEvents(db));

        lv.setAdapter(adapter);
    }
}
