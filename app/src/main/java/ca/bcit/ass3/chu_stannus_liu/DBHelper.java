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
    private static final int DB_VERSION = 2; //increment version number to invoke onUpgrade method
    private Context context;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
       // this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createEventTable());
        sqLiteDatabase.execSQL(createEventDetailTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS EVENT");
        onCreate(sqLiteDatabase);
    }

    public Cursor getEvent(SQLiteDatabase db, Event event) {
        Cursor cursor = db.rawQuery("select * from EVENT_MASTER WHERE name = ?", new String[] {event.getName()});
        return cursor;
    }


    public void insertItemForEvent(SQLiteDatabase db, Event event, Item item) {

        String sql;
        sql = "INSERT INTO EVENT_DETAIL (NAME, UNIT, QUANTITY, EVENTID) " +
                "VALUES ('" + item.getName() + "', '" + item.getUnit() + "', '" +
                item.getQuantity() + "', (SELECT _id from EVENT_MASTER WHERE NAME = '" +
                event.getName() + "'));";
        db.execSQL(sql);
//
//        ContentValues values = new ContentValues();
//        values.put("NAME", item.getName());
//        values.put("UNIT", item.getUnit());
//        values.put("QUANTITY", item.getQuantity());
//        values.
//
//        db.insert("EVENT_DETAIL", null, values);
    }

    public void insertEvent(final SQLiteDatabase db, Event event) {
        ContentValues values = new ContentValues();
        values.put("NAME", event.getName());
        values.put("DATE", event.getDate());
        values.put("TIME", event.getTime());

        db.insert("EVENT_MASTER", null, values);
    }

    public String createEventTable() {
        String sql = "";
        sql += "CREATE TABLE EVENT_MASTER (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "NAME TEXT, ";
        sql += "DATE TEXT, ";
        sql += "TIME NUMERIC);"; //USES TEXT TYPE

        return sql;
    }

    public String createEventDetailTable() {

        String sql = "";
        sql += "CREATE TABLE EVENT_DETAIL (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "NAME TEXT, ";
        sql += "UNIT TEXT, ";
        sql += "QUANTITY INTEGER, ";
        sql += "EVENTID INTEGER, ";
        sql += "FOREIGN KEY(EVENTID) REFERENCES EVENT_MASTER(_id)); ";

        return sql;
    }
}
