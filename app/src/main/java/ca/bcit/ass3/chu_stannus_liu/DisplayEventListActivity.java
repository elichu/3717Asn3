package ca.bcit.ass3.chu_stannus_liu;

import android.app.LoaderManager;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * Created by E on 2017-11-09.
 */

public class DisplayEventListActivity extends AppCompatActivity {

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
                    Intent intent = new Intent(DisplayEventListActivity.this, DisplayEventDetailActivity.class);
                    intent.putExtra("event", i+1);
                    intent.putExtra("eventName", c.getString(1));
                    startActivity(intent);

            }
        });
        db = helper.getReadableDatabase();

        DisplayEventListAdapter adapter = new DisplayEventListAdapter(DisplayEventListActivity.this
                , helper.getAllEvents(db));

        lv.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayEventListActivity.this,
                MainActivity.class);
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
