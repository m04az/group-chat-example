package com.m0aaz.group_chat_example;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by MOaaZ on 8/1/16.. moaaz.elshazli@gmail.com
 */

// make application class to get done before other components
public class ApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}