package ca.bcit.ass3.chu_stannus_liu;

/**
 * Created by E on 2017-10-31.
 */

public class Item {

    private String _name;
    private String _unit;
    private int _quantity;

    public Item(String name, String unit, int quantity) {
        this._name = name;
        this._unit = unit;
        this._quantity = quantity;
    }

    public String getName() { return _name; };
    public String getUnit() { return _unit; };
    public int getQuantity() { return _quantity; };


}
