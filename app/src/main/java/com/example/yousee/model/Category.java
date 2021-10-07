package com.example.yousee.model;

public class Category implements ICategory {

    private long _id;
    private ItemType _type;
    private String _description;
    private String _imageUrl;

    public Category(long id, ItemType type, String description, String imageUrl) {
        this._id = id;
        this._type = type;
        this._description = description;
        this._imageUrl = imageUrl;
    }

    public long getId() {
        return _id;
    }

    public ItemType getType() {
        return _type;
    }

    public String getDescription() {
        return _description;
    }

    public String getImageUrl() {
        return _imageUrl;
    }

}
