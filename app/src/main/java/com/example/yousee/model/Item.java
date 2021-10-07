package com.example.yousee.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Item implements IItem{

    private long _id;
    private String _name;
    private String _description;
    private String _brand;
    private double _price;
    private int _stock;
    private ArrayList<String> _imageUrls;
    private int _numViewed;
    private int _numSold;

    public Item(){}

    public Item(long id, String name, String description, String brand, double price,
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

    public double getPrice() {
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

    public abstract HashMap<String, String> listSpecs();

    public void setId(long id) {
        this._id = id;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public void setBrand(String brand) {
        this._brand = brand;
    }

    public void setPrice(double price) {
        this._price = price;
    }

    public void setStock(int stock) {
        this._stock = stock;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this._imageUrls = imageUrls;
    }

    public void setNumViewed(int numViewed) {
        this._numViewed = numViewed;
    }

    public void setNumSold(int numSold) {
        this._numSold = numSold;
    }
}
