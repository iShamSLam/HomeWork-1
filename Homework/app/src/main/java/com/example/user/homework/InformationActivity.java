package com.example.user.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    public TextView name;
    public TextView surname;
    public ImageView pict;
    public TextView height;
    String frontName;
    String surname1;
    int heightP;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Intent intent = getIntent();
        frontName = intent.getStringExtra("name");
        surname1 = intent.getStringExtra("surname");
        heightP = intent.getIntExtra("height", 0);
        id = intent.getIntExtra("id", 0);
        name = findViewById(R.id.tv_name);
        surname = findViewById(R.id.tv_surname);
        pict = findViewById(R.id.pic);
        height = findViewById(R.id.height);
        height.setText("Height: " + heightP + " cм");
        name.setText("Name: " + frontName);
        surname.setText("Surname: " + surname1);
        pict.setImageResource(id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
