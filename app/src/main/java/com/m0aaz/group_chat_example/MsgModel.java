package com.m0aaz.group_chat_example;

/**
 * Created by MOaaZ on 8/1/16.. moaaz.elshazli@gmail.com
 */

// message model
public class MsgModel {
    private String mtext;
    private String mSender;
    private String mDate;

    public MsgModel(String mtext, String mSender, String  mDate) {
        this.mtext = mtext;
        this.mSender = mSender;
        this.mDate = mDate;
    }

    public MsgModel() {
    }

    public String getMtext() {
        return mtext;
    }

    public void setMtext(String mtext) {
        this.mtext = mtext;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}