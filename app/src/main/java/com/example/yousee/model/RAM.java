package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public class RAM extends Item{

    private String _memoryType;
    private int _capacity;
    private int _speed;
    private String _latency;

    public String getMemoryType() {
        return _memoryType;
    }

    public int getCapacity() {
        return _capacity;
    }

    public int getSpeed() {
        return _speed;
    }

    public String getLatency() {
        return _latency;
    }

    public void setMemoryType(String memoryType) {
        this._memoryType = memoryType;
    }

    public void setCapacity(int capacity) {
        this._capacity = capacity;
    }

    public void setSpeed(int _speed) {
        this._speed = _speed;
    }

    public void setLatency(String _latency) {
        this._latency = _latency;
    }

    public RAM(){super();}

    public RAM(long id, String name, String description, String brand, double price,
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

    public HashMap<String, String> listSpecs() {
        HashMap<String, String> specs = new HashMap<>();
        specs.put("Memory Type", _memoryType);
        specs.put("Capacity", _capacity + " GB");
        specs.put("Speed", _speed + " MHz");
        specs.put("Latency", _latency);
        return specs;
    }

}
