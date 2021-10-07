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

public class DataProvider {

    public interface CallBackManager {
        void callbackFunction(@NonNull Object res);
    }

    public static void getCategories(@NonNull CallBackManager callBackManager){
        ArrayList<ICategory> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Category").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                res.addAll(Objects.requireNonNull(task.getResult()).toObjects(Category.class));
                callBackManager.callbackFunction(res);
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getAllItems(CallBackManager callBackManager){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()).getDocuments()) {
                    addItemToFinal(res, snapshot);
                };

                callBackManager.callbackFunction(res);
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }


    public static void getItemsByCategory(CallBackManager callBackManager, ItemType itemType){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").whereEqualTo("itemType",itemType.name()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()).getDocuments()) {
                    addItemToFinal(res, snapshot);
                };
                callBackManager.callbackFunction(res);
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getItemById(CallBackManager callBackManager, long id){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").whereEqualTo("id",id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()).getDocuments()) {
                    addItemToFinal(res, snapshot);
                };
                callBackManager.callbackFunction(res.get(0));
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getItemByName(CallBackManager callBackManager, String name){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").whereEqualTo("name",name).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()).getDocuments()) {
                    addItemToFinal(res, snapshot);
                };
                callBackManager.callbackFunction(res.get(0));
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getTopViewedItems(CallBackManager callBackManager, int numOfItems){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").orderBy("numViewed", Query.Direction.DESCENDING).limit(numOfItems).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()).getDocuments()) {
                    addItemToFinal(res, snapshot);
                };
                callBackManager.callbackFunction(res);
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }

    public static void getTopSellingItems(CallBackManager callBackManager, int numOfItems){
        ArrayList<IItem> res = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Item").orderBy("numSold", Query.Direction.DESCENDING).limit(numOfItems).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("successfully finish");
                for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()).getDocuments()) {
                    addItemToFinal(res, snapshot);
                };
                callBackManager.callbackFunction(res);
            }else{
                System.out.println("fail to load the thing");
            }
        });
    }


    private static void addItemToFinal(ArrayList<IItem> res, DocumentSnapshot snapshot) {
        if(Objects.equals(snapshot.get("itemType"), "RAM")){
            res.add(snapshot.toObject(RAM.class));
        }else if (Objects.equals(snapshot.get("itemType"), "GPU")){
            res.add(snapshot.toObject(CPU.class));
        }else if(Objects.equals(snapshot.get("itemType"), "GPU")){
            res.add(snapshot.toObject(GPU.class));
        }else {
            System.out.println("fail to load the thing for unsupported itemtype");
        }
        ;
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
