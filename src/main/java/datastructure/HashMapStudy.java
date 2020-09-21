package datastructure;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HashMapStudy {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("234", "2");
        map.put("456", "3");
        map.put("123", "1");
        map.put("789", "4");
        map.forEach((key, value) -> {
            System.out.println("map" + value);
        });

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>(10, 0.75f, true);
        linkedHashMap.put("234", "2");
        linkedHashMap.put("456", "3");
        linkedHashMap.put("123", "1");
        linkedHashMap.put("789", "4");
        linkedHashMap.forEach((key, value) -> {
            System.out.println("linked map" + value);
        });

        linkedHashMap.get("123");

        linkedHashMap.forEach((key, value) -> {
            System.out.println("linked map after access " + value);
        });
        int a = 0;
        if ((a = (6)) == 0) {

        }
    }
}
