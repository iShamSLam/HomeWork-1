package com.example.user.homework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Information extends AppCompatActivity {

    public TextView name;
    public TextView surname;
    public ImageView pict;
    String frontName;
    String surname1;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_information);
        Intent intent = getIntent();
        frontName = intent.getStringExtra("name");
        surname1 = intent.getStringExtra("surname");
        id = intent.getIntExtra("id",0);
        name = findViewById(R.id.tv_name);
        surname = findViewById(R.id.tv_surname);
        pict = findViewById(R.id.pic);
        name.setText("Name: " + frontName);
        surname.setText("Surname: " + surname1);
        pict.setImageResource(id);


    }
}

