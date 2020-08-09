package com.example.isitopen;

public class Info {
    public String store; // 가게이름
    public String date; // 검색하려는 날짜
    public String timeStart; // 오픈시간(검색시간)
    public String timeEnd; // 마감시간
    public String loc; // 가게 위치

    public Info(){} // 생성자

    Info(String storeName, String dat, String ts, String te, String locate)
    {
        store = storeName;
        date = dat;
        timeStart = ts;
        timeEnd = te;
        loc = locate;
    }

}
