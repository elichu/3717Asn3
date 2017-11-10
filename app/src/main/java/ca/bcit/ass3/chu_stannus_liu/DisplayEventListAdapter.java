package ca.bcit.ass3.chu_stannus_liu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by E on 2017-11-09.
 */

public class DisplayEventListAdapter extends ArrayAdapter<String> {
    Context _context;
    public DisplayEventListAdapter(Context context, ArrayList<String> regions) {
        super(context, 0, regions);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        String eventName = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_name, parent, false);
        }
        TextView eventEntry = (TextView) convertView.findViewById(R.id.eventName);
        eventEntry.setText(eventName);

        return convertView;
    }
}
