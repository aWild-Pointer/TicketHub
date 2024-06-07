// MainActivity.java
package com.example.tickethub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tickethub.UI.TicketsFragment;

public class EventInfo extends AppCompatActivity {

    private RadioGroup ticketOptions;
    private Button buyButton;
    private TextView eventDetails, eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        ticketOptions = findViewById(R.id.options);
        buyButton = findViewById(R.id.buybutton);
        eventDetails = findViewById(R.id.details);
        eventDescription = findViewById(R.id.details);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = ticketOptions.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                if (selectedRadioButton != null) {
                    String ticketPrice = selectedRadioButton.getText().toString();
                    Toast.makeText(EventInfo.this, "已选： " + ticketPrice, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EventInfo.this, NavigationBar.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EventInfo.this, "请选择一个票档", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
