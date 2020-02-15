package datastructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListStudy {


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()){
            iterator.next();
        }
    }
}
