package ca.bcit.ass3.chu_stannus_liu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by E on 2017-10-31.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "potluck";
    private static final int DB_VERSION = 1;
    private Context context;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
       // this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createEventTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getEvent(SQLiteDatabase db, Event event) {
        Cursor cursor = db.rawQuery("select * from EVENT WHERE name = ?", new String[] {"asdf"});
        return cursor;
    }

//    public void insertItemForEvent(SQLiteDatabase db, Event event, Item item) {
//        ContentValues values = new ContentValues();
//        values.put("NAME", event.getName());
//        values.put("UNIT", event.getDate());
//        values.put("QUANTITY", event.getTime());
//
//        db.insert("EVENT", null, values);
//    }

    public void insertEvent(final SQLiteDatabase db, Event event) {
        ContentValues values = new ContentValues();
        values.put("NAME", event.getName());
        values.put("DATE", event.getDate());
        values.put("TIME", event.getTime());

        db.insert("EVENT", null, values);
    }

    public String createEventTable() {
        String sql = "";
        sql += "CREATE TABLE EVENT (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "NAME TEXT, ";
        sql += "DATE TEXT, ";
        sql += "TIME INTEGER);";

        return sql;
    }
}
