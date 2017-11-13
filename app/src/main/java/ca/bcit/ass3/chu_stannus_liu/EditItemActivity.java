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
import android.widget.TextView;

/**
 * Created by E on 2017-11-11.
 */

public class EditItemActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DBHelper helper;

    int eventNum;
    String eventName;

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

        eventNum = intent.getIntExtra("event", 0);
        eventName = intent.getStringExtra("eventName");

        itemName = intent.getStringExtra("itemName");
        itemUnit = intent.getStringExtra("itemUnit");
        itemQuantity = intent.getIntExtra("itemQuantity", 0);

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

                if (changeItemName.getText().toString().isEmpty() || changeUnit.getText().toString().isEmpty()
                        || changeQuantity.getText().toString().isEmpty()) {
                    return;
                }

                Intent intent = new Intent(EditItemActivity.this, DisplayItemListActivity.class);
                intent.putExtra("event", eventNum);
                intent.putExtra("eventName", eventName);

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
                intent.putExtra("eventName", eventName);

                intent.putExtra("itemID", itemID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditItemActivity.this, DisplayItemListActivity.class);
        intent.putExtra("event", eventNum);
        intent.putExtra("eventName", eventName);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

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
