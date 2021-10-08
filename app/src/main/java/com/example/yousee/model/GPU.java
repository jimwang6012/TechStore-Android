package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public class GPU extends Item{

    private int _memorySize;
    private int _clockSpeedBase;
    private int _clockSpeedBoost;

    public int getMemorySize() {
        return _memorySize;
    }

    public int getClockSpeedBase() {
        return _clockSpeedBase;
    }

    public int getClockSpeedBoost() {
        return _clockSpeedBoost;
    }

    public void setMemorySize(int _memorySize) {
        this._memorySize = _memorySize;
    }

    public void setClockSpeedBase(int _clockSpeedBase) {
        this._clockSpeedBase = _clockSpeedBase;
    }

    public void setClockSpeedBoost(int _clockSpeedBoost) {
        this._clockSpeedBoost = _clockSpeedBoost;
    }

    public GPU(){
        super();
    }

    public GPU(long id, String name, String description, String brand, double price,
               int stock, ArrayList<String> imageUrls, int numViewed, int numSold,
               int memorySize, int clockSpeedBase, int clockSpeedBoost) {

        super(id, name, description, brand, price, stock, imageUrls, numViewed, numSold);

        this._memorySize = memorySize;
        this._clockSpeedBase = clockSpeedBase;
        this._clockSpeedBoost = clockSpeedBoost;
    }

    public ItemType getItemType() {
        ItemType type = ItemType.GPU;
        return type;
    }

    public HashMap<String, String> listSpecs() {
        HashMap<String, String> specs = new HashMap<>();
        specs.put("memorySize", String.valueOf(_memorySize));
        specs.put("clockSpeedBase", String.valueOf(_clockSpeedBase));
        specs.put("clockSpeedBoost", String.valueOf(_clockSpeedBoost));
        return specs;
    }

}
