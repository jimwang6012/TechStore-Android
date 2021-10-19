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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firestore.v1.WriteResult;

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
        db.collection("Item").whereGreaterThanOrEqualTo("name",name).whereLessThanOrEqualTo("name",name+"\uF8FF").get().addOnCompleteListener(task -> {
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

    public static void incrementItemNumViewed(IItem item){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Item").document(String.valueOf(item.getId()));

        documentReference.update("numViewed", FieldValue.increment(1));
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
        itemList.add(new GPU(30, "NVIDIA GeForce GT 1030", "The new NVIDIA GeForce® GT 1030, powered by the award-winning NVIDIA Pascal™ architecture, accelerates your entire PC experience. Its powerful graphics engine and state-of-the-art technologies provide a performance upgrade to drive today's most demanding PC applications.",
                "Gigabyte", 1178.99, 4, new ArrayList<String>(Arrays.asList("gt1030_0","gt1030_1","gt1030_2")), 1000, 301,
                2, 1151, 1379));
        itemList.add(new GPU(31, "NVIDIA GeForce RTX 3060", "The EVGA GeForce® RTX 3060 12GB provides players with the ability to vanquish 1080p and 1440p gaming, while providing a quality NVIDIA RTX™ experience and a myriad of productivity benefits.",
                "Gigabyte", 999.99, 14, new ArrayList<String>(Arrays.asList("rtx3060_0","rtx3060_1","rtx3060_2","rtx3060_3","rtx3060_4")), 1000, 300,
                12, 1775, 1882));
        itemList.add(new GPU(32, "NVIDIA GeForce RTX 3060 TI", "ASUS Dual GeForce RTX™ 3060 Ti V2 OC Edition 8GB GDDR6 with LHR features two powerful Axial-tech fans for AAA gaming performance and ray tracing.",
                "Gigabyte", 998.99, 4, new ArrayList<String>(Arrays.asList("rtx3060ti_0","rtx3060ti_1","rtx3060ti_2","rtx3060ti_3","rtx3060ti_4")), 1000, 300,
                14, 1720, 1845));
        itemList.add(new GPU(33, "GeForce RTX 3070", "ASUS KO GeForce RTX™ 3070 V2 OC Edition 8GB GDDR6 with LHR adds a touch of flair to the next-gen gaming experience.",
                "Gigabyte", 1174.99, 4, new ArrayList<String>(Arrays.asList("rtx3070oc_0","rtx3070oc_1","rtx3070oc_2","rtx3070oc_3")), 1000, 300,
                12, 1775, 1837));


        itemList.add(new GPU(34, "RTX 3070", "TUF Gaming GeForce RTX™ 3070 Ti OC Edition 8GB GDDR6X buffed-up design with chart-topping thermal performance.",
                "ASUS", 1749.99, 4, new ArrayList<String>(Arrays.asList("rtx3070ti_0","rtx3070ti_1","rtx3070ti_2")), 1000, 300,
                8, 1775, 1837));

        itemList.add(new GPU(35, "GeForce RTX 3080 Ti", "The EVGA GeForce RTX 3080 FTW3 Ultra delivers the exceptional performance that gamers crave for 4K resolution gaming.",
                "Gigabyte", 2648.99, 4, new ArrayList<String>(Arrays.asList("rtx3080tiftw3_0","rtx3080tiftw3_1","rtx3080tiftw3_2","rtx3080tiftw3_3")), 1000, 300,
                12, 1775, 1800));
        itemList.add(new GPU(36, "RTX 3090", "Palit GeForce RTX™ 3090 GamingPro delivers stunning visuals, incredibly fast frame rates, and AI acceleration for gaming and creative applications. ",
                "Nvidia", 1749.99, 4, new ArrayList<String>(Arrays.asList("rtx3090_0","rtx3090_1","rtx3090_2")), 1000, 300,
                12, 1770, 1897));
        itemList.add(new GPU(37, "RX VEGA 64", "These revamped nCUs (1 nCU = 64 stream processors) are designed to operate at incredible clock speeds and deliver extreme gaming experiences with the newest high resolution and high refresh rate monitors.",
                "Gigabyte", 648.99, 4, new ArrayList<String>(Arrays.asList("rx_vega_64_0","rx_vega_64_1","rx_vega_64_2")), 1000, 300,
                8, 1242, 1546));
        itemList.add(new GPU(38, "AMD RX 560D", "The Radeon RX 560D is a graphics card by AMD, launched on July 4th, 2017. Built on the 14 nm process, and based on the Polaris 21 graphics processor, in its Polaris 21 XL variant, the card supports DirectX 12. ",
                "Radeon", 948.99, 4, new ArrayList<String>(Arrays.asList("rx560d_0","rx560d_1","rx560d_2")), 1000, 300,
                12, 1090, 1175));
        itemList.add(new GPU(39, "RX 6800 XT", "With a new outlook, the Red Devil RX 6800 XT is paired with an entirely new advanced cooling solution to keep your graphics card running in low temperatures while providing a remarkably high-performance gaming experience. ",
                "Gigabyte", 948.99, 4, new ArrayList<String>(Arrays.asList("rx6800_0","rx6800_1","rx6800_2","rx6800_3","rx6800_4")), 1000, 300,
                12, 1433, 2337));
        return itemList;
    }


    private static List<RAM> getSampleRAMData() {
        List<RAM> itemList = new ArrayList<>();
        itemList.add(new RAM(21, "Crucial 8GB Desktop", "There's an easy cure for a slow computer: more memory. Designed to help your system run faster and smoother, Crucial Desktop Memory is one of the easiest and most affordable ways to improve your system's performance. Load programs faster. Increase responsiveness. Run data-intensive applications with ease, and increase your desktop's multitasking capabilities.",
                "Crucial", 67.99, 4, new ArrayList<String>(Arrays.asList("crucial_8gb_desktop_1","crucial_8gb_desktop_2","crucial_8gb_desktop_3")), 1001, 301,
                "DDR4", 8, 2666,"CL19"));
        itemList.add(new RAM(22, "Crucial 16GB Desktop", "As a brand of Micron, one of the largest memory manufacturers in the world, Crucial Desktop Memory is the standard for reliable performance. From the original SDRAM technology all the way to DDR4, we've engineered the memory technologies that have powered the world's computers for 40 years and counting. When you choose Crucial memory, you're choosing memory that's backed by a limited lifetime warranty and designed for the world's leading systems.1 Don't settle for anything less",
                "Crucial", 109.25, 4, new ArrayList<String>(Arrays.asList("crucial_16gb_desktop_1","crucial_16gb_desktop_2","crucial_16gb_desktop_3")), 1000, 300,
                "DDR4", 16, 2666,"CL19"));
        itemList.add(new RAM(23, "Corsair Vengeance RGB Pro SL Black 32GB RAM 2 X 16GB", "CORSAIR VENGEANCE RGB PRO Series DDR4 overclocked memory lights up your PC with mesmerizing dynamic multi-zone RGB lighting, while delivering the best in DDR4 performance.",
                "Corsair", 368.99, 4, new ArrayList<String>(Arrays.asList("corsair_vengeance_rgb_pro_sl_black_32gb_ram_2_x_16gb_1","corsair_vengeance_rgb_pro_sl_black_32gb_ram_2_x_16gb_2","corsair_vengeance_rgb_pro_sl_black_32gb_ram_2_x_16gb_3")), 1000, 300,
                "DDR4", 32, 3600,"CL18"));
        itemList.add(new RAM(24, "Corsair Dominator Platinum RGB 16GB RAM 2 X 8GB", "CORSAIR DOMINATOR® PLATINUM RGB DDR4 Memory redefines premium DDR4 memory, with superior aluminum craftsmanship, tightly screened high-frequency memory chips and 12 ultra-bright, individually addressable CAPELLIX RGB LEDs",
                "Crucial", 249.55, 4, new ArrayList<String>(Arrays.asList("corsair_dominator_platinum_rgb_16gb_ram_2_x_8gb_1","corsair_dominator_platinum_rgb_16gb_ram_2_x_8gb_2","corsair_dominator_platinum_rgb_16gb_ram_2_x_8gb_3")), 1000, 300,
                "DDR4", 16, 3200,"CL16"));

        itemList.add(new RAM(25, "G.SKILL Ripjaws V Series Black 32GB", "As the latest addition to the classic Ripjaws family, Ripjaws V series is the newest DDR4 memory designed for maximum compatibility and cutting-edge performance with the latest Intel® Core™ processors. Built with the finest components, tested under the most rigorous conditions, and offered in five color options, Ripjaws V is the perfect choice for building a new performance system or for a simple memory upgrade.",
                "G.SKILL", 278.99, 4, new ArrayList<String>(Arrays.asList("g_skill_ripjaws_v_series_black_32gb_1","g_skill_ripjaws_v_series_black_32gb_2","g_skill_ripjaws_v_series_black_32gb_3")), 1000, 300,
                "DDR4", 32, 3600,"CL16"));
        itemList.add(new RAM(26, "Corsair Vengeance RGB RT 16GB RAM 2X 8GB", "CORSAIR VENGEANCE RGB RS DDR4 memory punches up your PC’s aesthetics while delivering outstanding performance. Each module boasts six individually addressable RGB LEDs for brilliant illumination. Take control with CORSAIR iCUE software and customize RGB lighting to match your setup, create custom lighting profiles, and synchronize with all iCUE-compatible devices for stunning lighting across the entire iCUE ecosystem. A custom PCB delivers high signal quality for superb performance and stability on the latest Intel® and AMD DDR4 motherboards, while tightly screened memory chips unlock superior overclocking potential. For mesmerizing, customizable RGB lighting and remarkable DDR4 performance, equip your PC with VENGEANCE RGB RS.",
                "Corsair", 168.99, 4, new ArrayList<String>(Arrays.asList("corsair_vengeance_rgb_rt_16gb_ram_2x_8gb_1","corsair_vengeance_rgb_rt_16gb_ram_2x_8gb_2","corsair_vengeance_rgb_rt_16gb_ram_2x_8gb_3")), 1000, 300,
                "DDR4", 16, 3200,"CL16"));

        itemList.add(new RAM(27, "G.SKILL Trident Z Royal Elite Silver 16GB", "he Trident Z Royal Elite series is the upper echelon of DDR4 performance and design, featuring a meticulously sculpted crystalline pattern across the polished surface of the aluminum heatspreader, a patented full-length crystalline light bar, and customizable 8-zone RGB lighting. Created for both performance and aesthetics, the Trident Z Royal Elite is the ultimate choice for any high-end PC build.",
                "G.SKILL", 319.00, 4, new ArrayList<String>(Arrays.asList("g_skill_trident_z_royal_elite_silver_16gb_1","g_skill_trident_z_royal_elite_silver_16gb_2","g_skill_trident_z_royal_elite_silver_16gb_3")), 1000, 300,
                "DDR4", 16, 3600,"CL16"));

        itemList.add(new RAM(28, "Kingston FURY Beast 16GB RAM", "Kingston FURY Beast 16GB RAM (2 x 8GB) DDR4-3600MHz CL17 - Black (Intel XMP, AMD Ryzen) KF436C17BBK2/16",
                "Kingston", 168.99, 4, new ArrayList<String>(Arrays.asList("kingston_fury_beast_16gb_ram_1","kingston_fury_beast_16gb_ram_2","kingston_fury_beast_16gb_ram_3")), 1000, 300,
                "DDR4", 16, 3600,"CL17"));

        itemList.add(new RAM(29, "G.SKILL Trident Z Neo RGB 64 GB RAM", "Engineered and optimized for full compatibility on the latest AMD Ryzen 3000 series processors on AMD X570 chipset motherboards, Trident Z Neo brings unparalleled memory performance and vibrant RGB lighting to any gaming PC or workstation with AMD Ryzen 3000 CPUs and AMD X570 motherboards.",
                "G.SKILL", 694.99, 4, new ArrayList<String>(Arrays.asList("g_skill_trident_z_neo_rgb_64_gb_ram_1","g_skill_trident_z_neo_rgb_64_gb_ram_2","g_skill_trident_z_neo_rgb_64_gb_ram_3")), 1000, 300,
                "DDR4", 64, 3600,"CL18"));
        itemList.add(new RAM(20, "Corsair Vengeance RGB RS 64GB RAM 2X 32GB", "Corsair Vengeance RGB RS 64GB RAM 2X 32GB, DDR4, 3200 MHz, Unbuffered, 16-20-20-38, 1.35V, Black PCB, For AMD Ryzen & Intel",
                "Corsair", 668.99, 4, new ArrayList<String>(Arrays.asList("corsair_vengeance_rgb_rs_64gb_ram_2x_32gb_1","corsair_vengeance_rgb_rs_64gb_ram_2x_32gb_2","corsair_vengeance_rgb_rs_64gb_ram_2x_32gb_3")), 10000, 300,
                "DDR4", 64, 3200,"CL18"));
        return itemList;
    }






    private static List<CPU> getSampleCPUData() {
        List<CPU> itemList = new ArrayList<>();
        itemList.add(new CPU(11, "AMD Ryzen 7 3700X CPU", "Everyone deserves a powerful processor. Uncompromising features and smooth performance are finally the standard for every gamer and artist.",
                "AMD", 549.99, 4, new ArrayList<String>(Arrays.asList("ryzen_7_3700x_0","ryzen_7_3700x_1","ryzen_7_3700x_2")), 10000, 300,
                8, "AMD AM4",3600, 4400));
        itemList.add(new CPU(12, "AMD Ryzen 9 5950X", "When you have the world’s most advanced processor architecture for gamers and content creators, the possibilities are endless. Whether you are playing the latest games, designing the next skyscraper, or crunching data, you need a powerful processor that can handle it all—and more. Hands down, the AMD Ryzen™ 5000 Series desktop processors set the bar for gamers and artists alike.",
                "AMD", 1198.99, 4, new ArrayList<String>(Arrays.asList("ryzen_9_5950x_0","ryzen_9_5950x_1","ryzen_9_5950x_2")), 10000, 300,
                16, "AMD AM4", 3400,4900));
        itemList.add(new CPU(13, "Intel Core i7-10700F", "The Core i7-10700F 2.9 GHz Eight-Core LGA 1200 Processor from Intel has a base clock speed of 2.9 GHz and comes with features such as Intel Optane Memory support, Intel Boot Guard, Intel VT-d virtualization technology for directed I/O, and Intel Hyper-Threading technology. With Intel Turbo Boost 3.0 and 2.0, the maximum turbo frequency this processor can achieve is 4.8 GHz. Additionally, this processor features 8 cores with 16 threads in an LGA 1200 socket, has 16MB of cache memory, and 16 PCIe lanes. Having 8 cores allows the processor to run multiple programs simultaneously without slowing down the system, while the 16 threads allow a basic ordered sequence of instructions to be passed through or processed by a single CPU core. This processor also supports 128GB of dual-channel 2933 MHz DDR4 RAM and utilizes 10th-generation technology.",
                "Intel", 448.99, 4, new ArrayList<String>(Arrays.asList("i7_10700f_0","i7_10700f_1","i7_10700f_2")), 10000, 300,
                8, "Intel LGA1200", 2900,4800));

        itemList.add(new CPU(14, "Intel Core i5-10600KF", "Introducing the all new 10th Generation Intel Core i5 10600KF processor, 10th Gen Intel® Core™ \"KF\" and \"F\" SKU desktop processors are built for gamers and creators with the same 10th gen innovations, specifications, and performance, but without processor graphics features. As a result, they can deliver added value when CPU performance is the top priority. With an optimal balance of frequency, cores and threads, optional advanced tuning support7, and blazing connectivity, 10th Gen Intel® Core™ processors supercharge desktops for competitive and premium performance. Processors with a \"KF\" in their processor number also offer overclocking capabilities.",
                "Intel", 352.99, 4, new ArrayList<String>(Arrays.asList("i5_10600kfz_0","i5_10600kfz_1","i5_10600kfz_2")), 10000, 300,
                6, "Intel LGA1200", 4100,4800));
        itemList.add(new CPU(15, "AMD Ryzen 5 5600X ", "When you have the world’s most advanced processor architecture for gamers and content creators, the possibilities are endless. Whether you are playing the latest games, designing the next skyscraper, or crunching data, you need a powerful processor that can handle it all—and more. Hands down, the AMD Ryzen™ 5000 Series desktop processors set the bar for gamers and artists alike.",
                "AMD", 488.99, 4, new ArrayList<String>(Arrays.asList("ryzen_5_5600x_0","ryzen_5_5600x_1","ryzen_5_5600x_2","ryzen_5_5600x_3","ryzen_5_5600x_4")), 10000, 300,
                6, "AMD AM4", 3700,4600));

        itemList.add(new CPU(16, "Intel Core i5-11400", "11th Generation Intel® Core Processors redefine Intel® CPU performance for desktop PCs. New core and graphics architectures, AI-based performance boosts, best-in-class wireless and wired connectivity, and advanced tuning features deliver higher levels of power and flow to support your aspirations.",
                "Intel", 339.00, 4, new ArrayList<String>(Arrays.asList("i5_11400_0","i5_11400_1","i5_11400_2","i5_11400_3","i5_11400_4")), 10000, 300,
                6, "Intel LGA1200", 2600,4400));

        itemList.add(new CPU(17, "Intel Core i9-10900KA Marvel's Avenger SE", "Introducing the all new 10th Generation Intel Core i9 10900K processor, Unlocked 10th Gen Intel® Core™ desktop processors are optimized for enthusiast gamers, overclockers and serious content creators looking to take advantage of amazing overclocking and unleash the performance capabilities of these new processors. With an optimal balance of frequency, cores and threads, advanced tuning support, and blazing connectivity, unlocked 10th Gen Intel® Core™ processors supercharge desktops for a competitive edge.",
                "Intel", 940.00, 4, new ArrayList<String>(Arrays.asList("i9_10900ka_0","i9_10900ka_1","i9_10900ka_2")), 10000, 300,
                10, "FCLGA1200", 3700,5300));


        itemList.add(new CPU(18, "Intel Core i3-10105F", "Introducing the all new 10th Generation Intel Core i3 10105F processor, 10th Gen Intel® Core™ desktop processors are optimized for enthusiast looking to take advantage of amazing performance capabilities of these new processors. With an optimal balance of frequency, cores and threads, and blazing connectivity, supercharge desktops for a competitive edge.",
                "Intel", 159.85, 4, new ArrayList<String>(Arrays.asList("i3_10105f_0","i3_10105f_1","i3_10105f_2")), 10000, 300,
                4, "Intel LGA1200", 3700,4400));

        itemList.add(new CPU(19, "AMD Athlon 3000G", "The most advanced entry-level processor AMD has ever created, for users who value fast responsiveness and built-in Radeon™ Graphics, with the cutting-edge processor architecture you need to take advantage of graphics card upgrades.",
                "AMD", 159.99, 4, new ArrayList<String>(Arrays.asList("athlon_3000g_0","athlon_3000g_1","athlon_3000g_2")), 10000, 300,
                2, "AMD AM4", 3500,3500));
        itemList.add(new CPU(10, "AMD Ryzen Threadripper 3990X", "64 cores provide an astonishing 128 threads of simultaneous multi-processing power, while 288MB of combined cache and vast I/O from the enthusiast-grade AMD TRX40 platform work together to deliver incredible performance.",
                "AMD", 6998.99, 4, new ArrayList<String>(Arrays.asList("ryzen_threadripper_3990x_0","ryzen_threadripper_3990x_1","ryzen_threadripper_3990x_2")), 10000, 300,
                64, "AMD sTRX4", 2900,4300));
        return itemList;

    }


}
