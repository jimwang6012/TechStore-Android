package com.example.yousee.util;

import com.example.yousee.model.ItemType;
import com.example.yousee.model.MockCategory;
import com.example.yousee.model.MockGUI;
import com.example.yousee.model.ICategory;
import com.example.yousee.model.IItem;

import java.util.ArrayList;

public class MockDataProvider{

    public static ArrayList<IItem> generateData() {
        ArrayList<IItem> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            result.add(new MockGUI(100000.0+i*1.0,"img"));
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
