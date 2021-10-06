package com.example.yousee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yousee.model.IItem;
import com.example.yousee.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TopPickAdaptor extends RecyclerView.Adapter<TopPickAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.image_view);
            nameTextView = itemView.findViewById(R.id.name);
        }
        @Override
        public void onClick(View v) {
            // What to do when the view item is clicked
            IItem clickedItem = mItems.get(getAdapterPosition());
            Toast.makeText(mContext, clickedItem.getPrice() + " is clicked in position " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<IItem> mItems;
    private Context mContext;

    public TopPickAdaptor(ArrayList<IItem> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.item_top_picks, parent, false);

        // Return a new holder instance
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.imageView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fall_transition));

        // Get the data object for the item view in this position
        IItem thisItem = mItems.get(position);
        System.out.println("Item: "+thisItem.getPrice());


        //Set the attributed of list_view_number_item views
        int i = mContext.getResources().getIdentifier(
                thisItem.getImageUrl(), "drawable",
                mContext.getPackageName());

        //Setting the icon
        holder.imageView.setImageResource(i);
        holder.nameTextView.setText(toPrice(thisItem.getPrice()));
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


    @Override
    public int getItemCount() {
        return mItems.size();
    }


}
