package com.example.diewithme;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {

    private Intent intent;
    private IntentFilter intentFilter;
    private Intent batteryStatus;
    private int battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = registerReceiver(null, intentFilter);
        battery = batteryStatus.getIntExtra("level", -1);

        if (battery <= 100) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(this, Stop.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
