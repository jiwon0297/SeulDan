package com.example.myapplication.mate;

public class SampleData {
    private String title;
    private String writer;
    private String date;

    public SampleData(String title, String writer, String date){
        this.title = title;
        this.writer = writer;
        this.date = date;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getWriter()
    {
        return this.writer;
    }

    public String getDate()
    {
        return this.date;
    }
}