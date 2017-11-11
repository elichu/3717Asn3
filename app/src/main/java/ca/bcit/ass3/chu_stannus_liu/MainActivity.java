package ca.bcit.ass3.chu_stannus_liu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

        final Button eventSearchButton = (Button) findViewById(R.id.eventSearchButton);
        eventSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                startActivity(intent);

            }
        });
    }


}
