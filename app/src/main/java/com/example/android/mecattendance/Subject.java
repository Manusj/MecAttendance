package com.example.android.mecattendance;

/**
 * Created by srj on 28/1/18.
 */

public class Subject {
    String Subject_name;
    int bunkable;
    float attnper;
    String lastmodify;
    public Subject(String s,int b,float a,String lm)
    {
        Subject_name=s;
        bunkable=b;
        attnper=a;
        lastmodify=lm;
    }
    public String getSubject_name()
    {
        return Subject_name;
    }
    public int getBunkable()
    {
        return bunkable;
    }
    public float getAttnper()
    {
        return attnper;
    }
    public String getLastmodify()
    {
        return lastmodify;
    }




}
