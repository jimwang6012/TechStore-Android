package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

interface IItem {
    public long getId();
    public String getName();
    public String getDescription();
    public String getBrand();
    public float getPrice();
    public int getStock();
    public ArrayList<String> getImageUrls();
    public int getNumViewed();
    public int getNumSold();

    public ItemType getItemType();
    public HashMap<String, String> getSpecs();
}
