package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public class CPU extends Item{

    private int _coreCount;
    private String _socketType;
    private int _clockSpeedBase;
    private int _clockSpeedBoost;

    public int getCoreCount() {
        return _coreCount;
    }

    public String getSocketType() {
        return _socketType;
    }

    public int getClockSpeedBase() {
        return _clockSpeedBase;
    }

    public int getClockSpeedBoost() {
        return _clockSpeedBoost;
    }

    public void setCoreCount(int _coreCount) {
        this._coreCount = _coreCount;
    }

    public void setSocketType(String _socketType) {
        this._socketType = _socketType;
    }

    public void setClockSpeedBase(int _clockSpeedBase) {
        this._clockSpeedBase = _clockSpeedBase;
    }

    public void setClockSpeedBoost(int _clockSpeedBoost) {
        this._clockSpeedBoost = _clockSpeedBoost;
    }

    public CPU(){super();}

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

    public HashMap<String, String> listSpecs() {
        HashMap<String, String> specs = new HashMap<>();
        specs.put("Core Count", _coreCount + " Cores");
        specs.put("Socket Type", _socketType);
        specs.put("Clock Speed Base", _clockSpeedBase / 1000 + " GHz");
        specs.put("Clock Speed Boost", _clockSpeedBoost / 1000 + " GHz");
        return specs;
    }

}
