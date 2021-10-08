package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public class MockGUI implements IItem{
    private double price;
    private ArrayList<String> imageUrl;

    public MockGUI(double price, ArrayList<String> imageUrl) {
        this.price =price;
        this.imageUrl = imageUrl;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getBrand() {
        return null;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getStock() {
        return 0;
    }

    @Override
    public ArrayList<String> getImageUrls() {
        return null;
    }

    @Override
    public int getNumViewed() {
        return 0;
    }

    @Override
    public int getNumSold() {
        return 0;
    }

    @Override
    public ItemType getItemType() {
        return null;
    }

    @Override
    public HashMap<String, String> listSpecs() {
        return null;
    }

}
