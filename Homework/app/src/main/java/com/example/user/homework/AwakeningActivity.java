package com.example.user.homework;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AwakeningActivity extends Activity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awakening);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.tv_fire);
        button.setOnClickListener(v -> {
            AlarmReceiverActivity.ringtone.stop();
            Toast.makeText(this, "Надеюсь ты не заснешь", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
