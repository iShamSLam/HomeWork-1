package com.example.user.homework;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    public static DialogFragment edit = new EditFragment();
    Button btn_edit;
    TextView tv_login;
    TextView tv_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_fragment,
                container, false);
        tv_email = view.findViewById(R.id.tv_email);
        tv_login = view.findViewById(R.id.tv_login);
        tv_email.setText(MainActivity.nh_email.getText());
        tv_login.setText(MainActivity.nh_log.getText());
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.show(getFragmentManager(),"edit");
            }
        });
        return view;
    }
}
