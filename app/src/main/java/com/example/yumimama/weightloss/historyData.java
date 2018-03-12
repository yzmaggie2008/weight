package com.example.yumimama.weightloss;

import java.io.Serializable;

/**
 * Created by yumimama on 1/18/18.
 */

public class historyData implements Serializable {
    private int id;
    private String date;
    private String weight;

    public historyData(){}
    public historyData(int id, String date, String weight) {
        this.id = id;
        this.date = date;
        this.weight = weight;
    }

    public historyData(String date, String weight) {
        this.date = date;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getWeight() {
        return weight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}