package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public class RAM extends Item{

    private String _memoryType;
    private int _capacity;
    private int _speed;
    private String _latency;

    public RAM(long id, String name, String description, String brand, float price,
               int stock, ArrayList<String> imageUrls, int numViewed, int numSold,
               String memoryType, int capacity, int speed, String latency) {

        super(id, name, description, brand, price, stock, imageUrls, numViewed, numSold);

        this._memoryType = memoryType;
        this._capacity = capacity;
        this._speed = speed;
        this._latency = latency;
    }

    public ItemType getItemType() {
        ItemType type = ItemType.RAM;
        return type;
    }

    public HashMap<String, String> getSpecs() {
        HashMap<String, String> specs = new HashMap<>();
        specs.put("memoryType", _memoryType);
        specs.put("capacity", String.valueOf(_capacity));
        specs.put("speed", String.valueOf(_speed));
        specs.put("latency", _latency);
        return specs;
    }

}
