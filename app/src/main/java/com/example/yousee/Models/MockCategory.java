package com.example.yousee.Models;

public class MockCategory implements ICategory{
    private String name;
    private String imageUrl;

    public MockCategory(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
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
