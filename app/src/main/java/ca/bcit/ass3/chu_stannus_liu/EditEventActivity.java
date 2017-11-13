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

/**
 * Created by E on 2017-11-10.
 */

public class EditEventActivity extends AppCompatActivity {

        private SQLiteDatabase db;
        private DBHelper helper;
        private Cursor cursor;
        int eventNum;
        String eventName;

        EditText changeEventName;
        EditText changeEventDate;
        EditText changeEventTime;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edit_event);

            helper = new DBHelper(this);

            Intent intent = getIntent();
            eventNum = intent.getIntExtra("event", 0);

            eventName = intent.getStringExtra("eventName");
            String eventDate = intent.getStringExtra("eventDate");
            String eventTime = intent.getStringExtra("eventTime");

            changeEventName = (EditText) findViewById(R.id.changeEventName);
            changeEventDate = (EditText) findViewById(R.id.changeEventDate);
            changeEventTime = (EditText) findViewById(R.id.changeEventTime);

            changeEventName.setText(eventName);
            changeEventDate.setText(eventDate);
            changeEventTime.setText(eventTime);

            final Button updateEventButton = (Button) findViewById(R.id.updateEventButton);
            updateEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (changeEventName.getText().toString().isEmpty() || changeEventDate.getText().toString().isEmpty()
                            || changeEventTime.getText().toString().isEmpty()) {
                        return;
                    }

                    Intent intent = new Intent(EditEventActivity.this, DisplayEventDetailActivity.class);
                    intent.putExtra("event", eventNum);
                    intent.putExtra("eventName", eventName);

                    intent.putExtra("changeEventName", changeEventName.getText().toString());
                    intent.putExtra("changeEventDate", changeEventDate.getText().toString());
                    intent.putExtra("changeEventTime", changeEventTime.getText().toString());

                    startActivity(intent);
                }
            });
        }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditEventActivity.this, DisplayEventDetailActivity.class);
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
