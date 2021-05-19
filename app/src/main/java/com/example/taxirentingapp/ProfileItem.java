package com.example.taxirentingapp;

import android.graphics.drawable.Drawable;

public class ProfileItem {
    private int id;
    private Drawable itemImage;
    private Drawable itemState;
    private String itemName;
    private String itemButtonTxt;

    public Drawable getItemImage() {
        return itemImage;
    }

    public void setItemImage(Drawable itemImage) {
        this.itemImage = itemImage;
    }

    public Drawable getItemState() {
        return itemState;
    }

    public void setItemState(Drawable itemState) {
        this.itemState = itemState;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemButtonTxt() {
        return itemButtonTxt;
    }

    public void setItemButtonTxt(String itemButtonTxt) {
        this.itemButtonTxt = itemButtonTxt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
