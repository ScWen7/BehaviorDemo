package com.xxh.coordinatorlayoutdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 解晓辉 on 2017/6/22.
 * 作用：
 */

public class Data {
    private String name;
    private int imgId;

    public Data(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public static List<Data> provideDatas() {
        List<Data> dateList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dateList.add(new Data(i + "", R.mipmap.avatar));
        }
        return dateList;
    }
}
