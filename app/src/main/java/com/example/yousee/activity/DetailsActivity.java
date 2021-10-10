package com.example.yousee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yousee.R;
import com.example.yousee.adapter.ViewPagerAdapter;
import com.example.yousee.model.GPU;
import com.example.yousee.model.IItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DetailsActivity extends AppCompatActivity {

    ImageView backButton;
    TextView itemName;
    TextView itemCategory;
    TextView itemPrice;
    TextView itemInfo;
    Button descButton;
    Button specButton;
    TextView details;

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;

    IItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //hide action bar
        getSupportActionBar().hide();

        backButton = (ImageView) findViewById(R.id.image_back_button);
        viewPager = (ViewPager) findViewById(R.id.vp_images);
        tabLayout = (TabLayout) findViewById(R.id.tl_image_tracker);
        itemName = (TextView) findViewById(R.id.tv_item_name);
        itemCategory = (TextView) findViewById(R.id.tv_category);
        itemPrice = (TextView) findViewById(R.id.tv_price);
        itemInfo = (TextView) findViewById(R.id.tv_item_info);
        descButton = (Button) findViewById(R.id.btn_desc);
        specButton = (Button) findViewById(R.id.btn_spec);
        details = (TextView) findViewById(R.id.tv_details);

        int[] images = {R.drawable.arrow_back, R.drawable.arrow_back, R.drawable.arrow_back};

        //Initializing ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter(DetailsActivity.this, images);

        //Adding the Adapter to the ViewPager
        viewPager.setAdapter(viewPagerAdapter);

        //Connect TabLayout tracker to ViewPager
        tabLayout.setupWithViewPager(viewPager, true);

        //Placeholder
        ArrayList<String> urls = new ArrayList<>();
        urls.add("url here");
        item = new GPU(1, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, urls, 10000, 300,
                12, 1775, 1837);

        //Return to previous activity
        backButton.setOnClickListener(view -> {
            finish();
        });

        initItemInfo();
        initDetailsButton();
    }

    private void initItemInfo() {
        itemName.setText(item.getName());
        itemCategory.setText(item.getItemType().name());
        itemPrice.setText("$" + String.valueOf(item.getPrice()));

        String info = "";
        info += "Brand: " + item.getBrand();
        info += "\nStock: " + item.getStock();
        info += "\nSold: " + item.getNumSold();
        info += "\nViews: " + item.getNumViewed();

        itemInfo.setText(info);
    }

    private void initDetailsButton() {
        descButton.setText("Description");
        specButton.setText("Specification");

        details.setText(item.getDescription());

        descButton.setOnClickListener(view -> {
            details.setText(item.getDescription());
        });
        specButton.setOnClickListener(view -> {
            details.setText(convertMapToString(item.listSpecs()));
        });
    }

    private String convertMapToString(Map<String, ?> map) {
        StringBuilder mapString = new StringBuilder();
        for (String key : map.keySet()) {
            mapString.append(key + ": " + map.get(key) + "\n");
        }
        mapString.delete(mapString.length() - 1, mapString.length());
        return mapString.toString();
    }

}