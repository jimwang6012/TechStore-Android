package com.example.yousee.Models;

public class MockGUI implements IItem{
    private double price;
    private String imageUrl;

    public MockGUI(double price, String imageUrl) {
        this.price =price;
        this.imageUrl = imageUrl;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
