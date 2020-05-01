package com.example.splashscreenwithlogin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener{
    private TextView textViewUsername,textViewUserEmail,textViewUserNumber;
    private Button logout;

    public AccountFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(!SharedPrefManager.getInstance(getContext()).isLoggedIn()){
            startActivity(new Intent(getContext(),MainActivity.class));
        }
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        textViewUsername=v.findViewById(R.id.textViewUsername);
        textViewUserEmail=v.findViewById(R.id.textViewUserEmail);
        textViewUserNumber=v.findViewById(R.id.textViewUserNumber);
        logout=v.findViewById(R.id.buttonlogout);
        textViewUsername.setText(SharedPrefManager.getInstance(getContext()).getUsername());
        textViewUserEmail.setText(SharedPrefManager.getInstance(getContext()).getUserEmail());
        textViewUserNumber.setText(SharedPrefManager.getInstance(getContext()).getUserNumber());
        logout.setOnClickListener(this);
        // Inflate the layout for this fragment
        return v;
    }
    @Override
    public void onClick(View v) {
        SharedPrefManager.getInstance(getContext()).logout();
        startActivity(new Intent(getContext(),MainActivity.class));
    }

}
