package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public class CPU extends Item{

    private int _coreCount;
    private String _socketType;
    private int _clockSpeedBase;
    private int _clockSpeedBoost;

    public CPU(long id, String name, String description, String brand, double price,
               int stock, ArrayList<String> imageUrls, int numViewed, int numSold,
               int coreCount, String socketType, int clockSpeedBase, int clockSpeedBoost) {

        super(id, name, description, brand, price, stock, imageUrls, numViewed, numSold);

        this._coreCount = coreCount;
        this._socketType = socketType;
        this._clockSpeedBase = clockSpeedBase;
        this._clockSpeedBoost = clockSpeedBoost;
    }

    public ItemType getItemType() {
        ItemType type = ItemType.CPU;
        return type;
    }

    public HashMap<String, String> getSpecs() {
        HashMap<String, String> specs = new HashMap<>();
        specs.put("coreCount", String.valueOf(_coreCount));
        specs.put("socketType", _socketType);
        specs.put("clockSpeedBase", String.valueOf(_clockSpeedBase));
        specs.put("clockSpeedBoost", String.valueOf(_clockSpeedBoost));
        return specs;
    }

}
