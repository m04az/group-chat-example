package com.m0aaz.group_chat_example;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Query;

/**
 * @author greg
 * @since 6/21/13
 *
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class ChatListAdapter extends FirebaseListAdapter<MsgModel> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;
    private int layout;

    SharedPreferences prefs;
    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, MsgModel.class, layout, activity);
        this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void populateView(View view, MsgModel chat) {
        // Map a Chat object to an entry in our listview
        prefs = view.getContext().getSharedPreferences("chatPrefs", 0);

        String author = chat.getmSender();
        TextView authorText = (TextView) view.findViewById(R.id.txtSender);
        authorText.setText(author + ": ");
        TextView date = (TextView) view.findViewById(R.id.txtDate);
        String mdate=chat.getmDate();
        date.setText(mdate);
        date.setTextColor(Color.WHITE);
        TextView m = (TextView) view.findViewById(R.id.txtMessage);
        View l = view.findViewById(R.id.msglayout);
        LinearLayout l2 = (LinearLayout) view.findViewById(R.id.thething);


        // If the message was sent by this
        // user, color it differently
        if (author != null && author.equals( prefs.getString("username", null))) {
             authorText.setTextColor(Color.BLACK);
                    m.setTextColor(Color.WHITE);
            l.setBackgroundColor(Color.BLACK);
            l2.setGravity(Gravity.RIGHT);




        } else {
            authorText.setTextColor(Color.BLUE);
            l2.setGravity(Gravity.LEFT);
            m.setTextColor(Color.BLACK);

            l.setBackgroundColor(view.getContext().getColor(R.color.colorPrimaryDark));


        }
        ((TextView) view.findViewById(R.id.txtMessage)).setText(chat.getMtext());
    }
}
