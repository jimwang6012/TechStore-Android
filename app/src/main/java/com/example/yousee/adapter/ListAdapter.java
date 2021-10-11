package com.example.yousee.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yousee.R;
import com.example.yousee.activity.ListActivity;
import com.example.yousee.model.ICategory;
import com.example.yousee.model.IItem;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView itemCard;
        LinearLayout strip;
        ImageView itemImage;
        TextView name;
        TextView status;
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemCard = itemView.findViewById(R.id.cv_item);
            strip = itemView.findViewById(R.id.ll_strip);
            itemImage = itemView.findViewById(R.id.image_item);
            name = itemView.findViewById(R.id.text_item);
            status = itemView.findViewById(R.id.text_status);
            price = itemView.findViewById(R.id.text_price);
        }

        //TODO
        @Override
        public void onClick(View v) {
            // What to do when the view item is clicked
            IItem clickedItem = mItems.get(getAdapterPosition());
            Toast.makeText(mContext, clickedItem.getName() + " is clicked in position " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(mContext, DetailActivity.class);
//            mContext.startActivity(intent);
        }
    }

    private ArrayList<IItem> mItems;
    private Context mContext;

    public ListAdapter(ArrayList<IItem> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        return new ListAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position) {

        holder.itemCard.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_transition));

        // Get the data object for the item view in this position
        IItem thisItem = mItems.get(position);
        System.out.println("Item: "+thisItem.getName());

        //set strip color
        switch (thisItem.getItemType()) {
            case CPU:
                holder.strip.setBackground(
                        ContextCompat.getDrawable(mContext,R.drawable.cpu_linear_gradient));
                break;
            case GPU:
                holder.strip.setBackground(
                        ContextCompat.getDrawable(mContext,R.drawable.gpu_linear_gradient));
                break;
            case RAM:
                holder.strip.setBackground(
                        ContextCompat.getDrawable(mContext,R.drawable.ram_linear_gradient));
                break;
        }

        //Set the attributed of list_view_number_item views
        int i = mContext.getResources().getIdentifier(
                thisItem.getImageUrls().get(0), "drawable",
                mContext.getPackageName());

        holder.itemImage.setImageResource(i);

        holder.name.setText(thisItem.getName());
        holder.status.setText(getStatus(thisItem.getStock()));
        holder.price.setText(toPrice(thisItem.getPrice()));
    }

    /**
     * convert double to price string
     * @param price a number representing the price
     * @return a string represeting the price
     */
    private String toPrice(double price) {
        Locale nzd = new Locale("en","NZ");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(nzd);
        return dollarFormat.format(price);
    }

    private String getStatus(int stock) {
        if (stock > 0) {
            return "In stock";
        } else {
            return "Out of stock";
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
