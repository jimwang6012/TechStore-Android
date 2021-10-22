package com.example.yousee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yousee.R;
import com.example.yousee.adapter.ViewPagerAdapter;
import com.example.yousee.model.IItem;
import com.example.yousee.util.DataProvider;
import com.example.yousee.util.IDataProvider;
import com.google.android.material.tabs.TabLayout;

import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    ImageView backButton;
    TextView itemName;
    TextView itemCategory;
    TextView itemPrice;
    TextView itemInfoName;
    TextView itemInfoValue;
    Button descButton;
    Button specButton;
    TextView detailsDesc;
    TextView detailsSpec;

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;

    IItem item;
    IDataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataProvider = new DataProvider();

        setContentView(R.layout.activity_details);
        //hide action bar
        getSupportActionBar().hide();
        backButton = (ImageView) findViewById(R.id.image_back_button);
        viewPager = (ViewPager) findViewById(R.id.vp_images);
        tabLayout = (TabLayout) findViewById(R.id.tl_image_tracker);
        itemName = (TextView) findViewById(R.id.tv_item_name);
        itemCategory = (TextView) findViewById(R.id.tv_category);
        itemPrice = (TextView) findViewById(R.id.tv_price);
        itemInfoName = (TextView) findViewById(R.id.tv_item_info_name);
        itemInfoValue = (TextView) findViewById(R.id.tv_item_info_value);
        descButton = (Button) findViewById(R.id.btn_desc);
        specButton = (Button) findViewById(R.id.btn_spec);
        detailsDesc = (TextView) findViewById(R.id.tv_details_desc);
        detailsSpec = (TextView) findViewById(R.id.tv_details_spec);

        //Get Item from Intent
        Intent intent = getIntent();
        item = (IItem) intent.getSerializableExtra("item");

        dataProvider.incrementItemNumViewed(item);

        int[] images = new int[item.getImageUrls().size()];

        int counter = 0;

        for (String url : item.getImageUrls()) {
            int i = getResources().getIdentifier(
                    url, "drawable",
                    getPackageName());
            images[counter] = i;
            counter++;
        }

        //Initializing ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter(DetailsActivity.this, images);

        //Adding the Adapter to the ViewPager
        viewPager.setAdapter(viewPagerAdapter);

        //Connect TabLayout tracker to ViewPager
        tabLayout.setupWithViewPager(viewPager, true);

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

        String infoName = "";
        infoName += "Brand: ";
        infoName += "\nStock: ";
        infoName += "\nSold: ";
        infoName += "\nViews: ";

        String infoValue = "";
        infoValue += item.getBrand();
        infoValue += "\n" + item.getStock();
        infoValue += "\n" + item.getNumSold();
        infoValue += "\n" + item.getNumViewed();

        itemInfoName.setText(infoName);
        itemInfoValue.setText(infoValue);
    }

    private void initDetailsButton() {
        descButton.setText("Description");
        specButton.setText("Specification");

        detailsDesc.setText(item.getDescription());
        detailsSpec.setVisibility(View.GONE);

        descButton.setOnClickListener(view -> {
            detailsDesc.setText(item.getDescription());
            descButton.setBackground(getDrawable(R.drawable.tab_bg));
            detailsSpec.setVisibility(View.GONE);

            int pL = specButton.getPaddingLeft();
            int pT = specButton.getPaddingTop();
            int pR = specButton.getPaddingRight();
            int pB = specButton.getPaddingBottom();

            specButton.setBackgroundColor(getResources().getColor(R.color.navy_dark));
            specButton.setPadding(pL, pT, pR, pB);
            descButton.setPadding(pL, pT, pR, pB);
        });
        specButton.setOnClickListener(view -> {
            detailsDesc.setText(convertMapToStringName(item.listSpecs()));
            detailsSpec.setText(convertMapToStringValue(item.listSpecs()));
            specButton.setBackground(getDrawable(R.drawable.tab_bg));
            detailsSpec.setVisibility(View.VISIBLE);

            int pL = descButton.getPaddingLeft();
            int pT = descButton.getPaddingTop();
            int pR = descButton.getPaddingRight();
            int pB = descButton.getPaddingBottom();

            descButton.setBackgroundColor(getResources().getColor(R.color.navy_dark));
            specButton.setPadding(pL, pT, pR, pB);
            descButton.setPadding(pL, pT, pR, pB);
        });
    }

    private String convertMapToStringName(Map<String, ?> map) {
        StringBuilder mapString = new StringBuilder();
        for (String key : map.keySet()) {
            mapString.append(key + ": \n");
        }
        mapString.delete(mapString.length() - 1, mapString.length());
        return mapString.toString();
    }

    private String convertMapToStringValue(Map<String, ?> map) {
        StringBuilder mapString = new StringBuilder();
        for (String key : map.keySet()) {
            mapString.append(map.get(key) + "\n");
        }
        mapString.delete(mapString.length() - 1, mapString.length());
        return mapString.toString();
    }

}