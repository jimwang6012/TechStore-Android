package com.example.yousee.model;

import java.util.ArrayList;

public class GPU extends Item{

    private int _memorySize;
    private int _clockSpeedBase;
    private int _clockSpeedBoost;

    public GPU(long id, String name, String description, String brand, float price,
               int stock, ArrayList<String> imageUrls, int numViewed, int numSold,
               int memorySize, int clockSpeedBase, int clockSpeedBoost) {

        super(id, name, description, brand, price, stock, imageUrls, numViewed, numSold);

        this._memorySize = memorySize;
        this._clockSpeedBase = clockSpeedBase;
        this._clockSpeedBoost = clockSpeedBoost;
    }

    public void getItemType() {

    }

    public void getSpecs() {

    }

}
