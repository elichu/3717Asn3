package ca.bcit.ass3.chu_stannus_liu;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by E on 2017-11-11.
 */

public class DeleteEventActivity extends Activity {


    private DBHelper helper;
    private ListView lv;
    private SQLiteDatabase db;
    int eventNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_event);

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        Intent intent = getIntent();
        eventNum = intent.getIntExtra("event", 0);

        db.beginTransaction();
        try {
            helper.deleteEvent(db, eventNum);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        final Button returnToHome = (Button) findViewById(R.id.returnToHome);
        returnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteEventActivity.this, MainActivity.class);
                //intent.putExtra("event", eventNum);
                startActivity(intent);
            }
        });

    }
}
