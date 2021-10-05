package com.example.yousee.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yousee.Models.ICategory;
import com.example.yousee.R;

import java.util.ArrayList;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView nameTextView;

        public ViewHolder(View categoyView) {
            super(categoyView);
            categoyView.setOnClickListener(this);
            imageView = categoyView.findViewById(R.id.image_category);
            nameTextView = categoyView.findViewById(R.id.text_category);
        }
        @Override
        public void onClick(View v) {
            // What to do when the view categoy is clicked
            ICategory clickedCategory = mCategories.get(getAdapterPosition());
            Toast.makeText(mContext, clickedCategory.getType() + " is clicked in position " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<ICategory> mCategories;
    private Context mContext;

    public CategoryAdaptor(ArrayList<ICategory> categories) {
        mCategories = categories;
    }

    @NonNull
    @Override
    public CategoryAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View categoyView = inflater.inflate(R.layout.category_card, parent, false);

        // Return a new holder instance
        return new CategoryAdaptor.ViewHolder(categoyView);
    }


    @Override
    public void onBindViewHolder(CategoryAdaptor.ViewHolder holder, final int position) {

        // Get the data object for the categoy view in this position
        ICategory thisCategory = mCategories.get(position);
        System.out.println("Category: "+thisCategory.getType());


        //Set the attributed of list_view_number_categoy views
        int i = mContext.getResources().getIdentifier(
                thisCategory.getImageUrl(), "drawable",
                mContext.getPackageName());

        //Setting the icon
        holder.imageView.setImageResource(i);
        holder.nameTextView.setText(thisCategory.getType().name());
        switch (thisCategory.getType()) {
            case CPU:
                holder.nameTextView.setBackground(
                        ContextCompat.getDrawable(mContext,R.drawable.cpu_linear_gradient));
                break;
            case GPU:
                holder.nameTextView.setBackground(
                        ContextCompat.getDrawable(mContext,R.drawable.gpu_linear_gradient));
                break;
            case RAM:
                holder.nameTextView.setBackground(
                        ContextCompat.getDrawable(mContext,R.drawable.ram_linear_gradient));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

}
