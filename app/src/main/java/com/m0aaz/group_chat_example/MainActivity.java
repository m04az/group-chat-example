package com.m0aaz.group_chat_example;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ListActivity {

    SharedPreferences prefs ;
    private String userName;
    private TextView wel;
    private Firebase firebase;
    // TODO : change the next line with firebase link
     private static final String FIREBASE_URL = "FIREBASELINK";

    ChatListAdapter listAdapter;
    ListView listView;
    private ValueEventListener ConnectedListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firebase = new Firebase(FIREBASE_URL).child("group_chat_example");


        prefs = getApplication().getSharedPreferences("chatPrefs", 0);
         final Intent intent = new Intent(MainActivity.this, Login.class );

        //get name of the user and save it to username
        getName();

        // if no user name open login activity
        if(userName==null ){
            startActivity(intent);


        }else{


            // sending messages
            EditText editText = (EditText) findViewById(R.id.messageInput);
            findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendIt();


                }
            });

            // if clear button pressed ; clear name and open login activity
        findViewById(R.id.changename).setOnClickListener(new View.OnClickListener() {
         @Override
           public void onClick(View v) {

             //remove name from sharedprefs
             prefs.edit().remove("username").commit();
             startActivity(intent);


         }
});


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        //when it comes from login activity get the new name
        getName();
    }

    @Override
    protected void onStart() {

        super.onStart();

        // in a activity that extends ListActivity getListView(); mean to set the list to the list that have the id "@android:id/list"
         listView = getListView();
        //set the adapter to the view and fire base with limit 100
        listAdapter = new ChatListAdapter(firebase.limit(100), this, R.layout.message_left, userName);
        //set the adapter to the list
        listView.setAdapter(listAdapter);


        listAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(listAdapter.getCount() - 1);

            }
        });

        ConnectedListener=firebase.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }
    @Override
    public void onStop() {
        super.onStop();
        firebase.getRoot().child(".info/connected").removeEventListener(ConnectedListener);
        listAdapter.cleanup();
    }


    //get the name from sharedpref
    public String getName(){
        userName = prefs.getString("username", null);
        return userName;

    }

    //send the data to firebase
    public void SendIt(){

        EditText editText= (EditText) findViewById(R.id.messageInput);
        String inputmsg = editText.getText().toString();
        if (!inputmsg.equals("")) {
            sendItToFireBase(inputmsg);
            editText.setText("");
        }

        }

    public void sendItToFireBase(String msg){
        //set the date form
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String date = sdf.format(new Date());
        //make a msg model of
         MsgModel msgM = new MsgModel(msg, userName, date);
        //push the model to firebase
        firebase.push().setValue(msgM);


    }

}
