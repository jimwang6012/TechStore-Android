package com.example.yousee.model;

import java.io.Serializable;

public interface ICategory extends Serializable {

    long getId();
    ItemType getType();
    String getDescription();
    String getImageUrl();

}
