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
    private static final int DB_VERSION = 6; //increment version number to invoke onUpgrade method
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS EVENT_MASTER");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS EVENT_DETAIL");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CONTRIBUTION");

        onCreate(sqLiteDatabase);
    }

    public Cursor searchEvent(SQLiteDatabase db, String keyword) {
        Cursor cursor = db.rawQuery("select * from EVENT_MASTER WHERE EVENT_MASTER MATCH ?",
                new String[] {keyword});
        return cursor;
    }

    public Cursor getEvent(SQLiteDatabase db, int event) {
        Cursor cursor = db.rawQuery("select * from EVENT_MASTER WHERE _eventid = ?", new String[] {Integer.toString(event)});
        return cursor;
    }

    public Cursor searchEvents(SQLiteDatabase db, String keyword) {

        String[] keywords = keyword.split(" ");
        String[] wildcards = new String[keywords.length];
        String query = "select _eventId _id, Name, Date, Time from EVENT_MASTER WHERE name LIKE ?";
        String likeQuery = " OR name LIKE ?";

        int i = 0;
        if(keywords.length > 1) {

            for (String word : keywords) {
                query = query + likeQuery;
                wildcards[i++] = "%"+word+"%";
            }
        } else {
            wildcards[0] = "%"+keyword+"%";
        }

        Cursor cursor = db.rawQuery(query,
                wildcards);
        return cursor;
    }

    public Cursor getAllItems(SQLiteDatabase db, String eventId) {
        Cursor cursor = db.rawQuery("select _detailId _id, ItemName, ItemUnit, ItemQuantity from EVENT_DETAIL WHERE eventId = ?",
                new String[] {eventId});
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getAllEvents(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select _eventId _id, Name, Date, Time from EVENT_MASTER", null);
        cursor.moveToFirst();
        return cursor;
    }

    public void updateItem(final SQLiteDatabase db, Item item, int eventID, int itemID) {
        ContentValues values = new ContentValues();
        values.put("ItemName", item.getName());
        values.put("ItemUnit", item.getUnit());
        values.put("ItemQuantity", item.getQuantity());

        db.update("EVENT_DETAIL", values, "eventId=? AND _detailID=?",
                new String[] {Integer.toString(eventID), Integer.toString(itemID)});
    }

    public void insertItemForEvent(SQLiteDatabase db, int eventID, Item item) {

        String sql;
        sql = "INSERT INTO EVENT_DETAIL (ItemName, ItemUnit, ItemQuantity, eventId) " +
                "VALUES ('" + item.getName() + "', '" + item.getUnit() + "', '" +
                item.getQuantity() + "', '" + eventID + "');";
        db.execSQL(sql);
    }

    public void deleteItem(SQLiteDatabase db, int eventID, int itemID) {
        db.delete("EVENT_DETAIL", "eventId=? AND _detailId=?",
                new String[] {Integer.toString(eventID), Integer.toString(itemID)});
    }

    public void deleteEvent(SQLiteDatabase db, int eventID) {
        db.delete("EVENT_MASTER", "_eventId=?", new String[] {Integer.toString(eventID)});
    }

    public void updateEvent(final SQLiteDatabase db, Event event, int eventID) {
        ContentValues values = new ContentValues();
        values.put("Name", event.getName());
        values.put("Date", event.getDate());
        values.put("Time", event.getTime());

        db.update("EVENT_MASTER", values, "_eventId=?", new String[] {Integer.toString(eventID)});
    }

    public void insertEvent(final SQLiteDatabase db, Event event) {
        ContentValues values = new ContentValues();
        values.put("Name", event.getName());
        values.put("Date", event.getDate());
        values.put("Time", event.getTime());

        db.insert("EVENT_MASTER", null, values);
    }

    public Cursor retrieveItemId(SQLiteDatabase db, int eventID, String itemName) {
        Cursor cursor = db.rawQuery("select _detailId _id from EVENT_DETAIL WHERE ItemName = ?"
                +"AND eventId = ?", new String[] {itemName, Integer.toString(eventID)});
        return cursor;
    }

    public Cursor retrieveEventId(SQLiteDatabase db, String event) {
        Cursor cursor = db.rawQuery("select _eventId _id from EVENT_MASTER WHERE Name = ?", new String[] {event});
        return cursor;
    }

    public Cursor retrieveEventId(SQLiteDatabase db, Event event) {
        Cursor cursor = db.rawQuery("select _eventId _id from EVENT_MASTER WHERE Name = ?", new String[] {event.getName()});
        return cursor;
    }

    public String createEventTable() {
        String sql = "";
        sql += "CREATE TABLE EVENT_MASTER (";
        sql += "_eventId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT, ";
        sql += "Date TEXT, ";
        sql += "Time NUMERIC);"; //USES TEXT TYPE

        return sql;
    }

    public String createEventDetailTable() {

        String sql = "";
        sql += "CREATE TABLE EVENT_DETAIL (";
        sql += "_detailId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "ItemName TEXT, ";
        sql += "ItemUnit TEXT, ";
        sql += "ItemQuantity INTEGER, ";
        sql += "eventId INTEGER, ";
        sql += "FOREIGN KEY(eventId) REFERENCES EVENT_MASTER(_eventId)); ";

        return sql;
    }

}
