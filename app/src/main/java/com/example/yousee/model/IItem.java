package com.example.yousee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public interface IItem extends Serializable {
    long getId();
    String getName();
    String getDescription();
    String getBrand();
    double getPrice();
    int getStock();
    ArrayList<String> getImageUrls();
    int getNumViewed();
    int getNumSold();

    ItemType getItemType();
    HashMap<String, String> listSpecs();
}
