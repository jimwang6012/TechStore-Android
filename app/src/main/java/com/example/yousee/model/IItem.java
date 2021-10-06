package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public interface IItem {
    public long getId();
    public String getName();
    public String getDescription();
    public String getBrand();
    public double getPrice();
    public int getStock();
    public ArrayList<String> getImageUrls();
    public int getNumViewed();
    public int getNumSold();

    public ItemType getItemType();
    public HashMap<String, String> getSpecs();
}
