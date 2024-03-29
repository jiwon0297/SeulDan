package com.example.myapplication.mate;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MateWriteData {

    @SerializedName("title")
    String title;

    @SerializedName("nickname")
    String nickname;

    @SerializedName("content")
    String content;

    @SerializedName("cate")
    String cate;

    @SerializedName("campus")
    String campus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public MateWriteData(String title, String nickname, String content, String cate, String campus) {
        this.title = title;
        this.nickname = nickname;
        this.content = content;
        this.cate = cate;
        this.campus = campus;
    }
}
