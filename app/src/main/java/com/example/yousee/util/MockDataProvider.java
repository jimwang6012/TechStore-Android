package com.example.yousee.util;

import com.example.yousee.Models.MockCategory;
import com.example.yousee.Models.MockGUI;
import com.example.yousee.Models.ICategory;
import com.example.yousee.Models.IItem;

import java.util.ArrayList;

public class MockDataProvider{

    public static ArrayList<IItem> generateData() {
        ArrayList<IItem> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            result.add(new MockGUI("GTX-"+i,"img"));
        }
        return result;
    }

    public static ArrayList<ICategory> generateCategoryData() {
        ArrayList<ICategory> result = new ArrayList<>();
        result.add(new MockCategory("GPU","img"));
        result.add(new MockCategory("CPU","img_1"));
        result.add(new MockCategory("RAM","img"));
        return result;
    }
}
