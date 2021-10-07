package com.example.yousee.util;

import com.example.yousee.model.GPU;
import com.example.yousee.model.ItemType;
import com.example.yousee.model.MockCategory;
import com.example.yousee.model.MockGUI;
import com.example.yousee.model.ICategory;
import com.example.yousee.model.IItem;

import java.util.ArrayList;
import java.util.Arrays;

public class MockDataProvider{

    public static ArrayList<IItem> generateData() {
        ArrayList<IItem> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            result.add(new GPU(1,"GoodGPU","A mock good gpu description","mock bran",100.0,10,new ArrayList<String>(Arrays.asList("img","img","img")),10,2,2,3,4));
        }
        return result;
    }

    public static ArrayList<ICategory> generateCategoryData() {
        ArrayList<ICategory> result = new ArrayList<>();
        result.add(new MockCategory(ItemType.GPU,"img"));
        result.add(new MockCategory(ItemType.CPU,"img_1"));
        result.add(new MockCategory(ItemType.RAM,"img"));
        return result;
    }
}
