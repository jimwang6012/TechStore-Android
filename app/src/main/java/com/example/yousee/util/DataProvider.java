package com.example.yousee.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.yousee.model.CPU;
import com.example.yousee.model.Category;
import com.example.yousee.model.GPU;
import com.example.yousee.model.ICategory;
import com.example.yousee.model.IItem;
import com.example.yousee.model.ItemType;
import com.example.yousee.model.RAM;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Because of Firebase API are implemented in asynchronous nature
 * a callback function is required to be implemented by the developer to
 * handle the action after the request is completed.
 *
 */
public class DataProvider {

    public interface CategoryCallBackManager{
        void callbackFunction(@NonNull ArrayList<ICategory> res);
    }

    public interface ItemCallBackManager{
        void callbackFunction(@NonNull IItem res);
    }

    public interface ArrayItemCallBackManager{
        void callbackFunction(@NonNull ArrayList<IItem> res);
    }


    public static void getCategories(@NonNull CategoryCallBackManager callBackManager){
        ArrayList<ICategory> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Category").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                res.addAll(task.getResult().toObjects(Category.class));
                callBackManager.callbackFunction(res);
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getAllItems(ArrayItemCallBackManager callBackManager){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                    IItem converted = documentToItem(snapshot);
                    if (converted != null) {
                        res.add(converted);
                    }
                }

                callBackManager.callbackFunction(res);
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }


    public static void getItemsByCategory(ArrayItemCallBackManager callBackManager, ItemType itemType){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").whereEqualTo("itemType",itemType.name()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                    IItem converted = documentToItem(snapshot);
                    if (converted != null) {
                        res.add(converted);
                    }
                }
                callBackManager.callbackFunction(res);
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getItemById(ItemCallBackManager callBackManager, long id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").document(String.valueOf(id)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                IItem item = documentToItem(task.getResult());
                if(item!=null){
                    callBackManager.callbackFunction(item);
                }
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getItemsByName(ArrayItemCallBackManager callBackManager, String name){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").whereGreaterThanOrEqualTo("name",name).whereLessThanOrEqualTo("name",name+"\uF7FF").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                    IItem converted = documentToItem(snapshot);
                    if (converted != null) {
                        res.add(converted);
                    }
                }
                callBackManager.callbackFunction(res);
            }else{
                callBackManager.callbackFunction(res);
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getTopViewedItems(ArrayItemCallBackManager callBackManager, int numOfItems){
        getSortedItems(callBackManager, numOfItems, "numViewed");
    }

    public static void getTopSellingItems(ArrayItemCallBackManager callBackManager, int numOfItems){
        getSortedItems(callBackManager, numOfItems, "numSold");
    }

    private static void getSortedItems(ArrayItemCallBackManager callBackManager, int numOfItems, String sortParam) {
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").orderBy(sortParam, Query.Direction.DESCENDING).limit(numOfItems).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                    IItem converted = documentToItem(snapshot);
                    if (converted != null) {
                        res.add(converted);
                    }
                }
                callBackManager.callbackFunction(res);
            } else {
                System.out.println("fail to load the thing");
            }
        });
    }




    /**
     * @param snapshot generated by Firebase after a query
     */
    private static IItem documentToItem( DocumentSnapshot snapshot) {
        if(Objects.equals(snapshot.get("itemType"), "RAM")){
            return snapshot.toObject(RAM.class);
        }else if (Objects.equals(snapshot.get("itemType"), "CPU")){
            return snapshot.toObject(CPU.class);
        }else if(Objects.equals(snapshot.get("itemType"), "GPU")){
            return snapshot.toObject(GPU.class);
        }else {
            return null;
        }
    }


    // Add number documents to Firestore
    public static void addDataToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //has to do them separately so that the field matches exactly
        List<GPU> gpuList = getSampleGPUData();
        List<CPU> cpuList = getSampleCPUData();
        List<RAM> ramList = getSampleRAMData();
        for (GPU item : gpuList) {
            db.collection("Item").document(String.valueOf(item.getId())).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("item Collection Add", "item " + item.getId() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("item Collection Add", "item " + item.getId() + "not added.");
                }
            });
        }
        for (CPU item : cpuList) {
            db.collection("Item").document(String.valueOf(item.getId())).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("item Collection Add", "item " + item.getId() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("item Collection Add", "item " + item.getId() + "not added.");
                }
            });
        }
        for (RAM item : ramList) {
            db.collection("Item").document(String.valueOf(item.getId())).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("item Collection Add", "item " + item.getId() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("item Collection Add", "item " + item.getId() + "not added.");
                }
            });
        }
    }


    private static List<GPU> getSampleGPUData(){
        List<GPU> itemList = new ArrayList<>();
        itemList.add(new GPU(31, "RTX 3060", "good description here",
                "Gigabyte", 98.99, 4, new ArrayList<String>(Arrays.asList("img","img_1")), 50000, 300,
                12, 1775, 1837));
        itemList.add(new GPU(32, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, new ArrayList<String>(Arrays.asList("img","img_1")), 10000, 300,
                12, 1775, 1837));
        itemList.add(new GPU(33, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, new ArrayList<String>(Arrays.asList("img","img_1")), 10000, 300,
                12, 1775, 1837));
        return itemList;
    }


    private static List<RAM> getSampleRAMData() {
        List<RAM> itemList = new ArrayList<>();
        itemList.add(new RAM(21, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, new ArrayList<String>(Arrays.asList("img_1","img_1")), 10000, 300,
                "SSD", 1775, 12,"slow"));
        itemList.add(new RAM(22, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, new ArrayList<String>(Arrays.asList("img","img_1")), 10000, 300,
                "SSD", 1775, 12,"slow"));
        itemList.add(new RAM(23, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, new ArrayList<String>(Arrays.asList("img","img_1")), 10000, 300,
                "SSD", 1775, 12,"slow"));
        return itemList;
    }

    private static List<CPU> getSampleCPUData() {
        List<CPU> itemList = new ArrayList<>();
        itemList.add(new CPU(11, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, new ArrayList<String>(Arrays.asList("img","img_1")), 10000, 300,
                12, "A good SocketType",1775, 1837));
        itemList.add(new CPU(12, "RTX 3060", "good description here",
                "Gigabyte", 948.99, 4, new ArrayList<String>(Arrays.asList("img","img_1")), 10000, 300,
                12, "A good SocketType", 1775,1837));

        return itemList;

    }


}
