package com.example.user.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;
    Button btnCancel;
    EditText etName;
    EditText etEmail;
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);

        Intent intent = getIntent();

        etName.setText(intent.getStringExtra("name"));
        etEmail.setText(intent.getStringExtra("email"));
        etPhone.setText(intent.getStringExtra("phone"));

        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        String newName;
        String newEmail;
        String newPhone;

        if (v.getId() == R.id.btn_cancel) {
            Intent prev = getIntent();
            newName = prev.getStringExtra("name");
            newEmail = prev.getStringExtra("email");
            newPhone = prev.getStringExtra("phone");
            Toast toast = Toast.makeText(getApplicationContext(), "Изменения отменены", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
            {
            newName = etName.getText().toString();
            newEmail = etEmail.getText().toString();
            newPhone = etPhone.getText().toString();
            Toast toast = Toast.makeText(getApplicationContext(),"Данные изменены", Toast.LENGTH_SHORT);
            toast.show();
        }

        intent.putExtra("name", newName);
        intent.putExtra("email", newEmail);
        intent.putExtra("phone", newPhone);

        startActivity(intent);
    }
}
