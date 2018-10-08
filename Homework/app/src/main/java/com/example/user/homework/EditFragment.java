package com.example.user.homework;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditFragment extends DialogFragment implements View.OnClickListener {

    EditText et_login;
    EditText et_email;
    Button btn_submit;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_, null);
        et_login = view.findViewById(R.id.et_login);
        et_login.setText(MainActivity.nh_log.getText());
        et_email = view.findViewById(R.id.et_email);
        et_email.setText(MainActivity.nh_email.getText());
        btn_submit = view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(view)
                .create();
    }

    @Override
    public void onClick(View view) {
        MainActivity.nh_email.setText(et_email.getText());
        MainActivity.nh_log.setText(et_login.getText());
        ProfileFragment.edit.dismiss();
        FragmentManager fr = getFragmentManager();
        fr.popBackStack();
        FragmentTransaction frg = fr.beginTransaction();
        frg.replace(R.id.fragm, new ProfileFragment());
        frg.commit();
    }
}

