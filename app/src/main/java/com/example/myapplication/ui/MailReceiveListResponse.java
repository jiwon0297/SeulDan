package com.example.myapplication.ui;

import com.example.myapplication.mate.MateCommentResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MailReceiveListResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @SerializedName("result")
    @Expose
    private List<MailReceiveListResponse> result;

    public List<MailReceiveListResponse> getResult() {
        return result;
    }

    private int number;
    private String sender;
    private String recipient;
    private Date date;
    private String content;

    public int getNumber() { return number;}
    public String getSender() { return sender;}
    public String getRecipient() { return recipient;}
    public Date getDate() { return date; }
    public String getContent() {return content;}
}
