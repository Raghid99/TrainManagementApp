package com.example.login18april;

import android.util.Log;

import java.util.Date;

public class Chat {
    String from;
    String to;
    Date dateOfsend;
    String chatData;
    int chatID;

    //CONSTRUCTOR CALLED WHEN THE DATA IS LOADED
    public Chat(String from,String to, String chatData, int chatID) {
        this.from = from;
        this.to = to;
        this.chatData = chatData;
        this.chatID = chatID;
    }

    //CONSTRUCTOR CALLED WHEN THE DATA IS SEND
    public Chat(String from,String to, Date dateOfsend, String chatData) {
        this.from = from;
        this.to = to;
        this.dateOfsend = dateOfsend;
        this.chatData = chatData;
        this.chatID = validateChatID(System.currentTimeMillis());

    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDateOfsend() {
        return dateOfsend;
    }

    public void setDateOfsend(Date dateOfsend) {
        this.dateOfsend = dateOfsend;
    }

    public String getChatData() {
        return chatData;
    }

    public void setChatData(String chatData) {
        this.chatData = chatData;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }


    public int validateChatID(Long chatID){
        String y = Long.toString(chatID);

        char [] c = new char[y.length() -1];

        for (int i = 0;i<y.length() - 1;i++) {
            c[i] = y.charAt(i);
        }


        String d = "";
        for (int i = 4;i<c.length;i++) {
            d += Character.toString(c[i]);
        }

        int r = Integer.parseInt(d);
        return r;

    }
}
