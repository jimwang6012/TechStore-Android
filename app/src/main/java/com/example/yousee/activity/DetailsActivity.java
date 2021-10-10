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

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ImageView backButton;
    TextView info;
    Button descButton;
    Button specButton;
    TextView details;

    ViewPager viewPager;
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
        info = (TextView) findViewById(R.id.tv_info);
        descButton = (Button) findViewById(R.id.btn_desc);
        specButton = (Button) findViewById(R.id.btn_spec);
        details = (TextView) findViewById(R.id.tv_details);

        int[] images = {R.drawable.arrow_back, R.drawable.arrow_back, R.drawable.arrow_back};

        //Initializing ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter(DetailsActivity.this, images);

        //Adding the Adapter to the ViewPager
        viewPager.setAdapter(viewPagerAdapter);

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
    }
}