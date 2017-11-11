package ca.bcit.ass3.chu_stannus_liu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by E on 2017-11-10.
 */

public class EditEventActivity extends Activity {

        private SQLiteDatabase db;
        private DBHelper helper;
        private Cursor cursor;
        private Event event;
        int event1;
        EditText changeEventName;
        EditText changeEventDate;
        EditText changeEventTime;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edit_event);

            helper = new DBHelper(this);

            Intent intent = getIntent();
            event1 = intent.getIntExtra("event", 0);
            final String eventName = intent.getStringExtra("eventName");
            String eventDate = intent.getStringExtra("eventDate");
            String eventTime = intent.getStringExtra("eventTime");

            changeEventName = (EditText) findViewById(R.id.changeEventName);
            changeEventDate = (EditText) findViewById(R.id.changeEventDate);
            changeEventTime = (EditText) findViewById(R.id.changeEventTime);

            changeEventName.setText(eventName);
            changeEventDate.setText(eventDate);
            changeEventTime.setText(eventTime);

//            if (changeEventName.getText().toString().isEmpty() || changeEventDate.getText().toString().isEmpty()
//                    || changeEventTime.getText().toString().isEmpty()) {
//                return;
//            }7

            final Button updateEventButton = (Button) findViewById(R.id.updateEventButton);
            updateEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(EditEventActivity.this, DisplayEventDetailActivity.class);
                    intent.putExtra("event", event1);
                    intent.putExtra("eventName", eventName);

                    intent.putExtra("changeEventName", changeEventName.getText().toString());
                    intent.putExtra("changeEventDate", changeEventDate.getText().toString());
                    intent.putExtra("changeEventTime", changeEventTime.getText().toString());

                    startActivity(intent);
                }
            });
        }
}
