package ca.bcit.ass3.chu_stannus_liu;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by E on 2017-11-11.
 */

public class SearchResultsAdapter extends CursorAdapter {
    public SearchResultsAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.event_name, viewGroup, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView eventEntry = (TextView) view.findViewById(R.id.eventName);
        eventEntry.setText(cursor.getString(1));
    }
}
