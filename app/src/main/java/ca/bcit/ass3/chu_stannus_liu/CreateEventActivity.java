package ca.bcit.ass3.chu_stannus_liu;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by E on 2017-11-11.
 */

public class CreateEventActivity extends Activity{

    private DBHelper helper;
    private ListView lv;
    private SQLiteDatabase db;
    int eventNum;
    String eventName;
    int eventID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        helper = new DBHelper(this);

        Button button = (Button) findViewById(R.id.createEventButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float inputDecimal = 0;
                EditText eName = (EditText) findViewById(R.id.addName);
                EditText eDate = (EditText) findViewById(R.id.addDate);
                EditText eTime = (EditText) findViewById(R.id.addTime);

                if (eName.getText().toString().isEmpty() || eDate.getText().toString().isEmpty()
                        || eTime.getText().toString().isEmpty()) {
                    return;
                }

                String eventName = eName.getText().toString();
                String eventDate = eDate.getText().toString();
                String eventTime = eTime.getText().toString();

                Event event = new Event(eventName, eventDate, eventTime);

                db = helper.getWritableDatabase();
                db.beginTransaction();
                try {
                    helper.insertEvent(db, event);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                Intent intent = new Intent(CreateEventActivity.this, DisplayEventListActivity.class);
                startActivity(intent);

            }
        });
    }
}