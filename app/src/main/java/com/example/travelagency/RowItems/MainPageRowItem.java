package com.example.travelagency.RowItems;

public class MainPageRowItem {

    private String country_name;
    private int country_pic_id;
    private String action_title;

    public MainPageRowItem(String country_name, int country_pic_id, String action_title) {

        this.country_name = country_name;
        this.country_pic_id = country_pic_id;
        this.action_title = action_title;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public int getCountry_pic_id() {
        return country_pic_id;
    }

    public void setCountry_pic_id(int country_pic_id) {
        this.country_pic_id = country_pic_id;
    }

    public String getAction_title() {
        return action_title;
    }

    public void setAction_title(String action_title) {
        this.action_title = action_title;
    }
}
