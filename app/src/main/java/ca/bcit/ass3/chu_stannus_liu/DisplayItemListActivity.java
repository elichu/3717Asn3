package ca.bcit.ass3.chu_stannus_liu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DisplayItemListActivity extends AppCompatActivity {

    private DBHelper helper;
    private ListView lv;
    private SQLiteDatabase db;
    int itemID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        helper = new DBHelper(this);

        Intent intent = getIntent();
        final int eventNum = intent.getIntExtra("event", 0);
        final int itemID = intent.getIntExtra("itemID", 0);

        if (intent.getExtras().containsKey("changeItemName") ||
                intent.getExtras().containsKey("changeItemUnit") ||
                intent.getExtras().containsKey("changeItemQuantity") //||
                ) {
            String itemName = intent.getStringExtra("changeItemName");
            String itemUnit = intent.getStringExtra("changeItemUnit");
            int itemQuantity = intent.getIntExtra("changeItemQuantity", 0);

            Item item1 = new Item(itemName, itemUnit, itemQuantity);

            db = helper.getWritableDatabase();
            db.beginTransaction();
            try {
                helper.updateItem(db, item1, eventNum, itemID);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        lv = (ListView) findViewById(R.id.list1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = (Cursor) adapterView.getAdapter().getItem(i);
                c.moveToPosition(i);
                Intent intent = new Intent(DisplayItemListActivity.this, EditItemActivity.class);
                //intent.putExtra("itemID", i+1);
                intent.putExtra("event", eventNum);
                intent.putExtra("itemName", c.getString(1));
                intent.putExtra("itemUnit", c.getString(2));
                intent.putExtra("itemQuantity", Integer.parseInt(c.getString(3)));

                startActivity(intent);

            }
        });

        db = helper.getReadableDatabase();
        DisplayItemListAdapter adapter2 = new DisplayItemListAdapter(DisplayItemListActivity.this,
                helper.getAllItems(db, String.valueOf(eventNum)));
        lv.setAdapter(adapter2);
    }
}
