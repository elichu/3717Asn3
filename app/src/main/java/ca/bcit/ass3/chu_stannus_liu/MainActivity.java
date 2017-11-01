package ca.bcit.ass3.chu_stannus_liu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float inputDecimal = 0;
                EditText eName = (EditText) findViewById(R.id.addEventName);
                EditText eDate = (EditText) findViewById(R.id.addEventDate);
                EditText eTime = (EditText) findViewById(R.id.addEventTime);

                if(eName.getText().toString().isEmpty() || eDate.getText().toString().isEmpty()
                        || eTime.getText().toString().isEmpty()) {
                    return;
                }

                String eventName = eName.getText().toString();
                String eventDate = eDate.getText().toString();
                int eventTime = Integer.parseInt(eTime.getText().toString());

//                Event event = new Event(eventName, eventDate, eventTime);

                Intent intent = new Intent(MainActivity.this, DisplayEventActivity.class);
                intent.putExtra("eventName", eventName);
                intent.putExtra("eventDate", eventDate);
                intent.putExtra("eventTime", eventTime);

                startActivity(intent);
            }

        });


    }
}
