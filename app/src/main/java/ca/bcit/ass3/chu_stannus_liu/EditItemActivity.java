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
 * Created by E on 2017-11-11.
 */

public class EditItemActivity extends Activity {

    private SQLiteDatabase db;
    private DBHelper helper;
    private Cursor cursor;
    private Event event;
    //int event1;
    EditText changeItemName;
    EditText changeUnit;
    EditText changeQuantity;
    int itemID = 0;
    String itemName;
    String itemUnit;
    int itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        helper = new DBHelper(this);

        Intent intent = getIntent();
        //itemID = intent.getIntExtra("itemID", 0);
        final int eventNum = intent.getIntExtra("event", 0);
        final String itemName = intent.getStringExtra("itemName");
        String itemUnit = intent.getStringExtra("itemUnit");
        int itemQuantity = intent.getIntExtra("itemQuantity", 0);

        db = helper.getReadableDatabase();
        // db.beginTransaction();
        Cursor itemIdCursor = helper.retrieveItemId(db, eventNum, itemName);
        if (itemIdCursor.moveToFirst()) {
            itemID = itemIdCursor.getInt(0);
        }
        itemIdCursor.close();

        changeItemName = (EditText) findViewById(R.id.changeItemName);
        changeUnit = (EditText) findViewById(R.id.changeUnit);
        changeQuantity = (EditText) findViewById(R.id.changeQuantity);

        changeItemName.setText(itemName);
        changeUnit.setText(itemUnit);
        changeQuantity.setText(Integer.toString(itemQuantity));

        final Button updateItemButton = (Button) findViewById(R.id.updateItemButton);
        updateItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditItemActivity.this, DisplayItemListActivity.class);
                intent.putExtra("event", eventNum);
                intent.putExtra("itemID", itemID);

                intent.putExtra("changeItemName", changeItemName.getText().toString());
                intent.putExtra("changeItemUnit", changeUnit.getText().toString());
                intent.putExtra("changeItemQuantity", Integer
                        .parseInt(changeQuantity.getText().toString()));

                startActivity(intent);
            }
        });

        final Button deleteItemButton = (Button) findViewById(R.id.deleteItemButton);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.beginTransaction();
                try {
                    helper.deleteItem(db, eventNum, itemID);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                Intent intent = new Intent(EditItemActivity.this, DisplayItemListActivity.class);
                intent.putExtra("event", eventNum);
                intent.putExtra("itemID", itemID);
                startActivity(intent);
            }
        });
    }
}
