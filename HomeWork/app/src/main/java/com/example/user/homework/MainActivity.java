package com.example.user.homework;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEdit;
    Button btnBrowser;
    TextView tvName;
    TextView tvEmail;
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPhone = findViewById(R.id.tv_phone);

        Intent intent = getIntent();

        tvName.setText(intent.getStringExtra("name"));
        tvEmail.setText(intent.getStringExtra("email"));
        tvPhone.setText(intent.getStringExtra("phone"));

        btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);

        btnBrowser = findViewById(R.id.btn_browser);
        btnBrowser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()!=R.id.btn_browser) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("name", tvName.getText().toString());
            intent.putExtra("email", tvEmail.getText().toString());
            intent.putExtra("phone", tvPhone.getText().toString());
            startActivity(intent);
        } else {
            Uri webpage = Uri.parse("https://yandex.ru/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        }
    }
}
