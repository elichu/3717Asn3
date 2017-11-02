package ca.bcit.ass3.chu_stannus_liu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by E on 2017-10-31.
 */

public class Event implements Parcelable {

    private String _name;
    private String _date;
    private String _time;
    private String _eventId;

    ArrayList<Item> items = new ArrayList<Item>();

    public Event(String name, String date, String time) {
        this._name = name;
        this._date = date;
        this._time = time;
        this._eventId = null;
    }

    protected Event(Parcel in) {
        _name = in.readString();
        _date = in.readString();
        _time = in.readString();
        _eventId = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public void addItem(Item item) {
        items.add(item);
    }

    public String [] getItemList() {
        return items.toArray(new String[items.size()]);
    }

    public void setEventId(String eventId) {
        this._eventId = eventId;
    }

    public String getName() { return _name; };
    public String getDate() { return _date; };
    public String getTime() { return _time; };
    public String getEventId() { return _eventId; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_name);
        parcel.writeString(_date);
        parcel.writeString(_time);
        parcel.writeString(_eventId);

    }

}
