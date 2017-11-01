package ca.bcit.ass3.chu_stannus_liu;

import java.util.ArrayList;

/**
 * Created by E on 2017-10-31.
 */

public class Event {

    private String _name;
    private String _date;
    private int _time;

    ArrayList<Item> items = new ArrayList<Item>();

    public Event(String name, String date, int time) {
        this._name = name;
        this._date = date;
        this._time = time;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String [] getItemList() {
        return items.toArray(new String[items.size()]);
    }

    public String getName() { return _name; };
    public String getDate() { return _date; };
    public int getTime() { return _time; };

}
