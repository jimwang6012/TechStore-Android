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

        int[] images = {R.drawable.img, R.drawable.img_1, R.drawable.img};

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

        details.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec quis arcu sed diam luctus eleifend ut quis lacus. Praesent a leo vehicula, ornare sem tincidunt, porta ex. Sed vitae mi et justo accumsan laoreet nec id nunc. Pellentesque orci metus, porttitor id est at, ultrices iaculis nunc. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pharetra augue vel scelerisque. Maecenas id tellus pharetra sem suscipit aliquam.\n" +
                "\n" +
                "Maecenas eget neque semper, varius lorem sit amet, luctus quam. Vestibulum feugiat ipsum at varius commodo. Aliquam erat volutpat. Nam rhoncus at lorem vel ultrices. Maecenas aliquam rutrum est id congue. Proin tempor leo sed nulla lacinia volutpat. Proin dapibus nisi eu sapien condimentum, at lobortis odio accumsan. Nullam at massa vel eros consequat dignissim ac a leo. Pellentesque euismod ipsum at elit pharetra, vitae sollicitudin justo eleifend. Quisque pellentesque nisl vitae massa ultricies, sit amet euismod metus porttitor. Pellentesque bibendum felis neque. Curabitur consequat mauris ut eros gravida, vel rhoncus ex sodales. Suspendisse potenti. Praesent porta elit sit amet lectus scelerisque pulvinar.\n" +
                "\n" +
                "Pellentesque venenatis nunc sed orci egestas, sit amet aliquam arcu condimentum. Mauris posuere turpis tincidunt lectus cursus vehicula. Mauris nec magna justo. Fusce feugiat sit amet sapien at maximus. In ut massa bibendum neque luctus faucibus. Nullam sed ante id felis dictum laoreet. Aliquam erat volutpat. Nullam vestibulum dictum purus. Aenean varius semper accumsan. Cras vel lobortis justo, id viverra libero. Proin ultrices cursus dictum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras eget placerat neque, id venenatis leo. In eu erat vel tortor molestie tempus ut ac felis. Sed accumsan auctor vehicula. Morbi eros risus, ultricies nec dolor venenatis, rhoncus tristique elit.\n" +
                "\n" +
                "Nullam consequat felis est, at sollicitudin turpis finibus at. Maecenas tellus libero, imperdiet sed pharetra eu, feugiat placerat nibh. Nam at eros nec lectus condimentum facilisis. Aenean tellus neque, fermentum posuere turpis vitae, accumsan congue urna. Pellentesque laoreet mollis arcu, vulputate venenatis odio ornare ac. In arcu justo, euismod in mauris vitae, finibus tempus neque. Vivamus interdum dui leo, et hendrerit ante posuere id. Mauris facilisis cursus lectus, a cursus mi sagittis ac. Vivamus vestibulum ultrices mauris vitae porttitor. Suspendisse in fringilla nisl. Nulla facilisi. Aenean vel turpis a velit condimentum blandit. Etiam mauris ligula, consequat nec ex in, cursus varius libero. Maecenas nec convallis nibh. Proin non consequat massa, at convallis diam. Morbi nisi justo, cursus a velit ut, ullamcorper rutrum tortor.\n" +
                "\n" +
                "Fusce ipsum metus, consequat ac erat ac, dapibus malesuada felis. Sed et magna commodo, vehicula ex quis, condimentum dolor. Aenean velit sem, lacinia at ligula eget, ultricies rhoncus risus. Nullam rhoncus nibh vel congue aliquam. Duis a sapien vitae mauris congue ultricies eget nec enim. Proin convallis, velit vitae mollis interdum, purus arcu eleifend velit, ac consequat massa magna id ante. Mauris tincidunt, justo at auctor aliquet, nulla orci aliquet felis, et hendrerit nibh augue sed nisi. Curabitur a lectus ac tortor convallis maximus ac at libero. Mauris lorem lectus, rutrum in sodales at, pretium ac diam. Curabitur dui dui, volutpat sed ipsum sed, gravida consequat metus. Mauris finibus convallis felis eu ornare.");

        descButton.setOnClickListener(view -> {
            details.setText(item.getDescription());
            descButton.setBackgroundColor(getResources().getColor(R.color.grey));
            specButton.setBackgroundColor(getResources().getColor(R.color.navy_dark));
        });
        specButton.setOnClickListener(view -> {
            details.setText(convertMapToString(item.listSpecs()));
            specButton.setBackgroundColor(getResources().getColor(R.color.grey));
            descButton.setBackgroundColor(getResources().getColor(R.color.navy_dark));
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