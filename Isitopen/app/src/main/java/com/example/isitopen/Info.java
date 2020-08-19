package com.example.isitopen;

import java.io.Serializable;
import java.util.ArrayList;

public class Info implements Serializable {
    public String type; // 가게종류
    public String store; // 가게이름
    public int[] date = new int[3]; // 검색하려는날짜배열 (년,월,일)
    //public String date; // 검색하려는 날짜
    public int[] timeStart = new int[2]; // 오픈시간(검색시간)
    public int[] timeEnd = new int[2]; // 마감시간
    public String loc; // 가게 위치

    public Info(){} // 생성자

    Info(String typeN, String storeName, int[] dat, int[] ts, int[] te, String locate)
    {
        type = typeN;
        store = storeName;
        for(int i=0 ; i <3 ; i++)
        {
            date[i] = dat[i];
        }
        for(int i=0 ; i <2 ; i++)
        {
            timeStart[i] = ts[i];
        }
        for(int i=0 ; i <2 ; i++)
        {
            timeEnd[i] = te[i];
        }
        loc = locate;
    }

}
