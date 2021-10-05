package com.example.yousee.util;

import com.example.yousee.Models.ItemType;
import com.example.yousee.Models.MockCategory;
import com.example.yousee.Models.MockGUI;
import com.example.yousee.Models.ICategory;
import com.example.yousee.Models.IItem;

import java.util.ArrayList;

public class MockDataProvider{

    public static ArrayList<IItem> generateData() {
        ArrayList<IItem> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            result.add(new MockGUI(88888.0,"img"));
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
