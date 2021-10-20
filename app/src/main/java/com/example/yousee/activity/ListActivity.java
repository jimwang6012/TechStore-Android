package com.example.yousee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yousee.R;
import com.example.yousee.adapter.ListAdapter;
import com.example.yousee.model.ICategory;
import com.example.yousee.model.IItem;
import com.example.yousee.model.ItemType;
import com.example.yousee.util.DataProvider;
import com.example.yousee.util.IDataProvider;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private class ViewHolder {
        ImageButton backButton;
        TextView category;
        TextView categoryDescription;
        RecyclerView items;

        public ViewHolder() {
            backButton = (ImageButton) findViewById(R.id.image_back_button);
            category = (TextView) findViewById(R.id.text_category);
            categoryDescription = (TextView) findViewById((R.id.text_category_description));
            items = (RecyclerView) findViewById(R.id.rv_items);
        }
    }

    ViewHolder vh;
    ListAdapter listAdapter;
    ArrayList<IItem> items;
    Intent intent;

    IDataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataProvider = new DataProvider();

        //hide action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list);

        vh = new ViewHolder();

        //Return to previous activity
        vh.backButton.setOnClickListener(view -> {
            finish();
        });

        intent = getIntent();
        ICategory category = (ICategory) intent.getSerializableExtra("category");
        ItemType type = category.getType();
        vh.category.setText(type.toString());
        vh.categoryDescription.setText(category.getDescription());
        dataProvider.getItemsByCategory(res -> initList(res), type);
    }

    private void initList(ArrayList<IItem> res) {
        // Initialize Categories
        items = res;

        // Create adapter passing in the sample user data
        listAdapter = new ListAdapter(items);
        // Attach the adapter to the recyclerview to populate items
        vh.items.setAdapter(listAdapter);

        // an verticaL RecyclerView
        LinearLayoutManager lm = new LinearLayoutManager(this);

        // Set layout manager to position the items
        vh.items.setLayoutManager(lm);
    }
}