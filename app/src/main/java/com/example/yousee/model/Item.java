package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Item {

    private long _id;
    private String _name;
    private String _description;
    private String _brand;
    private float _price;
    private int _stock;
    private ArrayList<String> _imageUrls;
    private int _numViewed;
    private int _numSold;

    public Item(long id, String name, String description, String brand, float price,
                int stock, ArrayList<String> imageUrls, int numViewed, int numSold) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._brand = brand;
        this._price = price;
        this._stock = stock;
        this._imageUrls = imageUrls;
        this._numViewed = numViewed;
        this._numSold = numSold;
    }

    public long getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    public String getBrand() {
        return _brand;
    }

    public float getPrice() {
        return _price;
    }

    public int getStock() {
        return _stock;
    }

    public ArrayList<String> getImageUrls() {
        return _imageUrls;
    }

    public int getNumViewed() {
        return _numViewed;
    }

    public int getNumSold() {
        return _numSold;
    }

    public abstract ItemType getItemType();

    public abstract HashMap<String, String> getSpecs();

}
