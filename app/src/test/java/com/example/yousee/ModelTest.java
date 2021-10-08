package com.example.yousee;

import com.example.yousee.model.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelTest {

    @Test
    public void testGPU() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("url here");

        IItem item = new GPU(1, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, urls, 10000, 300,
                12, 1775, 1837);

        assertEquals(1, item.getId());
        assertEquals("RTX 3060", item.getName());
        assertEquals("good description here", item.getDescription());
        assertEquals("Gigabyte", item.getBrand());
        assertEquals(948.99, item.getPrice(), 1e-15);
        assertEquals(4, item.getStock());
        assertEquals(urls, item.getImageUrls());
        assertEquals(10000, item.getNumViewed());
        assertEquals(300, item.getNumSold());

        assertEquals(ItemType.GPU, item.getItemType());

        HashMap<String, String> specs = new HashMap<>();
        specs.put("memorySize", "12");
        specs.put("clockSpeedBase", "1775");
        specs.put("clockSpeedBoost", "1837");

        assertEquals(specs, item.listSpecs());
    }

}
