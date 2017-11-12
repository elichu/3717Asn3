package ca.bcit.ass3.chu_stannus_liu;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
    int eventNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        if( getIntent().getBooleanExtra("Exit", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
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

//        final Button eventSearchButton = (Button) findViewById(R.id.eventSearchButton);
//        eventSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(searchEvent.getText().toString().isEmpty()){
//                    return;
//                }
//
//                Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
//                intent.putExtra("keywords", searchEvent.getText().toString());
//                startActivity(intent);
//
//            }
//        });
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