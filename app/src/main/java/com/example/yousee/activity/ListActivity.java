package com.example.yousee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yousee.R;
import com.example.yousee.adapter.CategoryAdaptor;
import com.example.yousee.adapter.ListAdapter;
import com.example.yousee.model.ICategory;
import com.example.yousee.model.IItem;
import com.example.yousee.util.MockDataProvider;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private class ViewHolder {
        ImageView backButton;
        TextView category;
        TextView categoryDescription;
        RecyclerView items;

        public ViewHolder() {
            backButton = (ImageView) findViewById(R.id.image_back_button);
            category = (TextView) findViewById(R.id.text_category);
            categoryDescription = (TextView) findViewById((R.id.text_category_description));
            items = (RecyclerView) findViewById(R.id.rv_items);
        }
    }

    ViewHolder vh;
    ListAdapter listAdapter;
    ArrayList<IItem> items;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list);

        vh = new ViewHolder();
        initList();
        //Return to previous activity
        vh.backButton.setOnClickListener(view -> {
            finish();
        });

        intent = getIntent();
        vh.category.setText(intent.getStringExtra("category"));
        vh.categoryDescription.setText(intent.getStringExtra("description"));
    }

    private void initList () {


        // Initialize Categories
        items = MockDataProvider.generateData();

        // Create adapter passing in the sample user data
        System.out.println("Item list size: "+items.size());
        listAdapter = new ListAdapter(items);
        // Attach the adapter to the recyclerview to populate items
        vh.items.setAdapter(listAdapter);

        // an verticaL RecyclerView
        LinearLayoutManager lm = new LinearLayoutManager(this);

        // Set layout manager to position the items
        vh.items.setLayoutManager(lm);
    }
}