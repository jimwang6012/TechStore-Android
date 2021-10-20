package com.example.yousee.util;

import androidx.annotation.NonNull;

import com.example.yousee.model.ICategory;
import com.example.yousee.model.IItem;
import com.example.yousee.model.ItemType;

import java.util.ArrayList;

public interface IDataProvider {
    void incrementItemNumViewed(IItem item);

    void getItemsByCategory(ArrayItemCallBackManager callBackManager, ItemType itemType);
    void getCategories(@NonNull CategoryCallBackManager callBackManager);

    void getTopViewedItems(ArrayItemCallBackManager callBackManager, int numOfItems);

    void getTopSellingItems(ArrayItemCallBackManager callBackManager, int numOfItems);

    void getItemsByName(ArrayItemCallBackManager callBackManager, String name);


    interface CategoryCallBackManager{
        void callbackFunction(@NonNull ArrayList<ICategory> res);
    }

    interface ItemCallBackManager{
        void callbackFunction(@NonNull IItem res);
    }

    interface ArrayItemCallBackManager{
        void callbackFunction(@NonNull ArrayList<IItem> res);
    }

}
