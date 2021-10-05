package com.example.yousee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yousee.Models.ICategory;
import com.example.yousee.Models.IItem;
import com.example.yousee.R;
import com.example.yousee.util.MockDataProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<IItem> items;
    ArrayList<ICategory> categories;
    TopPickAdaptor topPickAdaptor;
    CategoryAdaptor categoryAdaptor;
    EditText searchBtn;
    RecyclerView rvTopPickItems;
    RecyclerView rvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        getSupportActionBar().hide();

        initTopPickPanel();
        initCategoryPanel();
        initSearchBtn();

    }

    private void initSearchBtn() {
        searchBtn = (EditText) findViewById(R.id.search_button);
        searchBtn.setFocusable(false);
        searchBtn.setOnClickListener(view -> {
            System.out.println("Click on search btn");
            Toast.makeText(getApplicationContext(), "Click on search btn", Toast.LENGTH_SHORT).show();
        });
    }


    /**
     * initialise the Top Pick panel on the MainActivity
     */
    private void initTopPickPanel() {
        // Lookup the recyclerview in activity layout
        rvTopPickItems = (RecyclerView) findViewById(R.id.rvTopPick);

        // Initialize TopPickItems
        items = MockDataProvider.generateData();

        // Create adapter passing in the sample user data
        topPickAdaptor = new TopPickAdaptor(items);
        // Attach the adapter to the recyclerview to populate items
        rvTopPickItems.setAdapter(topPickAdaptor);

        // an Horizontal RecyclerView
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // Set layout manager to position the items
        rvTopPickItems.setLayoutManager(lm);
    }

    /**
     * initialise the Category panel on the MainActivity
     */
    private void initCategoryPanel() {
        // Lookup the recyclerview in activity layout
        rvCategories = (RecyclerView) findViewById(R.id.rvCategory);

        // Initialize Categories
        categories = MockDataProvider.generateCategoryData();

        // Create adapter passing in the sample user data
        System.out.println("Category sizeL: "+categories.size());
        categoryAdaptor = new CategoryAdaptor(categories);
        // Attach the adapter to the recyclerview to populate items
        rvCategories.setAdapter(categoryAdaptor);

        // an verticaL RecyclerView
        LinearLayoutManager lm = new LinearLayoutManager(this);

        // Set layout manager to position the items
        rvCategories.setLayoutManager(lm);
    }
}