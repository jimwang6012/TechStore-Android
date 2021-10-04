package com.example.yousee.Models;

public class MockGUI implements IItem{
    private String name;
    private String imageUrl;

    public MockGUI(String name, String imageUrl) {
        this.name=name;
        this.imageUrl = imageUrl;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
