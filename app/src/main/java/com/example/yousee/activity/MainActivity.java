package com.example.yousee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yousee.adapter.CategoryAdaptor;
import com.example.yousee.adapter.ListAdapter;
import com.example.yousee.model.ICategory;
import com.example.yousee.model.IItem;
import com.example.yousee.R;
import com.example.yousee.util.DataProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<IItem> items;
    ArrayList<ICategory> categories;
    ListAdapter topPickAdaptor;
    ListAdapter topSellAdaptor;
    CategoryAdaptor categoryAdaptor;
    EditText searchBtn;
    RecyclerView rvTopItems;
    RecyclerView rvCategories;

    LinearLayout switchPanel;
    Button txTopSell;
    Button txTopPick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // populate database
        DataProvider.addDataToFirestore();

        //hide action bar
        getSupportActionBar().hide();

        initTopPickPanel();
        initCategoryPanel();
        initSearchBtn();

    }

    @Override
    protected void onResume() {
        super.onResume();
        txTopSell = (Button) findViewById(R.id.textTopSell);
        txTopPick = (Button) findViewById(R.id.textTopPick);
        txTopSell.setEnabled(true);
        txTopPick.setEnabled(false);
        txTopSell.setBackground(getDrawable(R.drawable.black_bg_shadow));
        txTopPick.setBackground(getDrawable(R.drawable.grey_bg_shadow));
        initTopPickPanel();
    }

    private void initSearchBtn() {
        searchBtn = (EditText) findViewById(R.id.search_button);
        searchBtn.setFocusable(false);
        searchBtn.setOnClickListener(view -> {
            System.out.println("Click on search btn");
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }


    /**
     * initialise the Top Sell panel on the MainActivity
     */
    private void initTopSellPanel() {
        // Lookup the recyclerview in activity layout
        rvTopItems = (RecyclerView) findViewById(R.id.rvTop);
        DataProvider.getTopSellingItems(res -> populateTopSellAdaptor(res),15);
    }



    /**
     * initialise the Top Pick panel on the MainActivity
     */
    private void initTopPickPanel() {
        // Lookup the recyclerview in activity layout
        rvTopItems = (RecyclerView) findViewById(R.id.rvTop);
        DataProvider.getTopViewedItems(res -> populateTopPickAdaptor(res),15);
    }

    private void populateTopPickAdaptor(ArrayList<IItem> items) {

        // Create adapter passing in the sample user data
        topPickAdaptor = new ListAdapter(items);
        // Attach the adapter to the recyclerview to populate items
        rvTopItems.setAdapter(topPickAdaptor);

        // an Horizontal RecyclerView
        LinearLayoutManager lm = new LinearLayoutManager(this);

        // Set layout manager to position the items
        rvTopItems.setLayoutManager(lm);
    }


    private void populateTopSellAdaptor(ArrayList<IItem> items) {

        // Create adapter passing in the sample user data
        topSellAdaptor = new ListAdapter(items);
        // Attach the adapter to the recyclerview to populate items
        rvTopItems.setAdapter(topSellAdaptor);

        // an Horizontal RecyclerView
        LinearLayoutManager lm = new LinearLayoutManager(this);

        // Set layout manager to position the items
        rvTopItems.setLayoutManager(lm);
    }



    /**
     * initialise the Category panel on the MainActivity
     */
    private void initCategoryPanel() {
        // Lookup the recyclerview in activity layout
        rvCategories = (RecyclerView) findViewById(R.id.rvCategory);

        // Initialize Categories
        DataProvider.getCategories(res -> populateAdaptor(res));
    }

    private void populateAdaptor(ArrayList<ICategory> res) {
        categories = res;
        System.out.println(categories);


        // Create adapter passing in the sample user data
        System.out.println("Category sizeL: " + categories.size());
        categoryAdaptor = new CategoryAdaptor(categories);
        // Attach the adapter to the recyclerview to populate items
        rvCategories.setAdapter(categoryAdaptor);

        // an verticaL RecyclerView
        LinearLayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);

        // Set layout manager to position the items
        rvCategories.setLayoutManager(lm);
    }

    public void toTopPick(View view) {
        txTopSell = (Button) findViewById(R.id.textTopSell);
        txTopPick = (Button) findViewById(R.id.textTopPick);
        txTopSell.setEnabled(true);
        txTopPick.setEnabled(false);

        txTopSell.setBackground(getDrawable(R.drawable.black_bg_shadow));
        txTopPick.setBackground(getDrawable(R.drawable.grey_bg_shadow));
        initTopPickPanel();
    }


    public void toTopSell(View view) {
        txTopSell = (Button) findViewById(R.id.textTopSell);
        txTopPick = (Button) findViewById(R.id.textTopPick);

        txTopSell.setEnabled(false);
        txTopPick.setEnabled(true);

        txTopPick.setBackground(getDrawable(R.drawable.black_bg_shadow));
        txTopSell.setBackground(getDrawable(R.drawable.grey_bg_shadow));

        initTopSellPanel();
    }

}