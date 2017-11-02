package ca.bcit.ass3.chu_stannus_liu;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by E on 2017-11-02.
 */

public class ItemsAdapter extends CursorAdapter {

    public ItemsAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.items_list, viewGroup, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView itemName = (TextView) view.findViewById(R.id.itemName);
        itemName.setText(cursor.getString(1));

        TextView itemUnit = (TextView) view.findViewById(R.id.itemUnit);
        itemUnit.setText(cursor.getString(2));

        TextView itemQuantity = (TextView) view.findViewById(R.id.itemQuantity);
        itemQuantity.setText(cursor.getString(3));

    }
}
