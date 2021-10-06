package com.example.yousee.model;

public class MockCategory implements ICategory{
    private ItemType type;
    private String imageUrl;

    public MockCategory(ItemType type, String imageUrl) {
        this.type = type;
        this.imageUrl = imageUrl;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
