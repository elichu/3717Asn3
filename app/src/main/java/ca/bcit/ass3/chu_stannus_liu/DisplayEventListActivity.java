package ca.bcit.ass3.chu_stannus_liu;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

        String event1 = "Halloween party";
        String event2 = "Christmas Party";
        String event3 = "New Year Eve";

        eventlist.add(event1);
        eventlist.add(event2);
        eventlist.add(event3);


        lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(DisplayEventListActivity.this, AddItemToEventActivity.class);
                    intent.putExtra("event", "Halloween party");
                    startActivity(intent);
                }
                if (i == 1) {
                    Intent intent = new Intent(DisplayEventListActivity.this, AddItemToEventActivity.class);
                    intent.putExtra("event", "Christmas Party");
                    startActivity(intent);
                }
                if (i == 2) {
                    Intent intent = new Intent(DisplayEventListActivity.this, AddItemToEventActivity.class);
                    intent.putExtra("event", "New Year Eve");
                    startActivity(intent);
                }
            }
        });

        DisplayEventListAdapter adapter = new DisplayEventListAdapter(DisplayEventListActivity.this, eventlist);

        lv.setAdapter(adapter);
    }

}
