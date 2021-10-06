package com.example.yousee.model;

import java.util.ArrayList;

public class CPU extends Item{

    private int _coreCount;
    private String _socketType;
    private int _clockSpeedBase;
    private int _clockSpeedBoost;

    public CPU(long id, String name, String description, String brand, float price,
               int stock, ArrayList<String> imageUrls, int numViewed, int numSold,
               int coreCount, String socketType, int clockSpeedBase, int clockSpeedBoost) {

        super(id, name, description, brand, price, stock, imageUrls, numViewed, numSold);

        this._coreCount = coreCount;
        this._socketType = socketType;
        this._clockSpeedBase = clockSpeedBase;
        this._clockSpeedBoost = clockSpeedBoost;
    }

    public void getItemType() {

    }

    public void getSpecs() {

    }

}
