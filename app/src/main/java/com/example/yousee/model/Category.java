package com.example.yousee.model;

public class Category implements ICategory {

    private long _id;
    private ItemType _type;
    private String _description;
    private String _imageUrl;

    public Category(){}

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

    public void setId(long id) {
        this._id = id;
    }



    public void setType(String type) {
        if(type.equals("GPU")) this._type = ItemType.GPU;
        if(type.equals("CPU")) this._type = ItemType.CPU;
        if(type.equals("RAM")) this._type = ItemType.RAM;
    }


    public void setDescription(String description) {
        this._description = description;
    }

    public void setImageUrl(String imageUrl) {
        this._imageUrl = imageUrl;
    }
}
