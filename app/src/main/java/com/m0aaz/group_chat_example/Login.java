package com.m0aaz.group_chat_example;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    String userName;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getApplication().getSharedPreferences("chatPrefs", 0);

        setContentView(R.layout.activity_login);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userIn = (EditText) findViewById(R.id.userinput);
                //get the text of user name
                userName= String.valueOf(userIn.getText());
                // save it to sharedpref
                prefs.edit().putString("username", userName).commit();
                //finish the activity and return to the main
                finish();

            }
        });
    }
}
