package ca.bcit.ass3.chu_stannus_liu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by E on 2017-11-01.
 */

public class DisplayItemActivity extends Activity {

    private SQLiteDatabase db;
    private DBHelper helper;
    private Cursor cursor;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String itemUnit = intent.getStringExtra("itemUnit");
        int itemQuantity = intent.getIntExtra("itemQuantity", 0);

        item = new Item(itemName, itemUnit, itemQuantity);

        TextView outItemName = (TextView) findViewById(R.id.outItemName);
        TextView outItemUnit = (TextView) findViewById(R.id.outItemUnit);
        TextView outItemQuantity = (TextView) findViewById(R.id.outItemQuantity);

        Cursor cursor = helper.getItem(db, item);
        String dbName = null;
        String dbUnit = null;
        String dbQuant = null;
        if (cursor.moveToFirst() ) {
            dbName = cursor.getString(1);
            dbUnit = cursor.getString(2);
            dbQuant = cursor.getString(3);
        }
        cursor.close();

        outItemName.setText(dbName);
        outItemUnit.setText(dbUnit);
        outItemQuantity.setText(dbQuant);
    }

}
