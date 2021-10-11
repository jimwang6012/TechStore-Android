package com.example.yousee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yousee.R;
import com.example.yousee.adapter.ListAdapter;
import com.example.yousee.model.IItem;
import com.example.yousee.util.DataProvider;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private class ViewHolder {
        LinearLayout linearLayout;
        ImageButton backButton;
        EditText searchField;
        RecyclerView items;
        TextView message;

        public ViewHolder() {
            linearLayout = findViewById(R.id.ll_search);
            backButton = findViewById(R.id.image_back_button);
            searchField = findViewById(R.id.search_button);
            items = findViewById(R.id.rv_items);
            message = findViewById(R.id.text_message);
        }
    }

    ViewHolder vh;
    ListAdapter listAdapter;
    ArrayList<IItem> items;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //hide action bar
        getSupportActionBar().hide();

        vh = new ViewHolder();

        //Return to previous activity
        vh.backButton.setOnClickListener(view -> {
            finish();
        });

        vh.linearLayout.setOnClickListener(view -> {
            hideKeyboard();
        });

        vh.searchField.requestFocus();
        showKeyboard();

        //perform search when submit
        vh.searchField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                DataProvider.getItemsByName(res -> initList(res),v.getText().toString());
                hideKeyboard();
                return true;
            }
            return false;
        });

    }

    private void initList(ArrayList<IItem> res) {

        if (res != null) {
            // Initialize Items
            items = res;

            vh.message.setVisibility(View.GONE);

            vh.items.setVisibility(View.VISIBLE);

            // Create adapter passing in the sample user data
            listAdapter = new ListAdapter(items);
            // Attach the adapter to the recyclerview to populate items
            vh.items.setAdapter(listAdapter);

            // an verticaL RecyclerView
            LinearLayoutManager lm = new LinearLayoutManager(this);

            // Set layout manager to position the items
            vh.items.setLayoutManager(lm);
        }
        if (res.isEmpty()){
            vh.items.setVisibility(View.GONE);

            vh.message.setVisibility(View.VISIBLE);
        }
    }

    private void showKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        view.clearFocus();
    }
}