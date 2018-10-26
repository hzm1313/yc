package other;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DT167 on 2018/3/5.
 */
public class algorithm {
    private static final int[] NUMBERS =
            {49, 38, 65, 97, 76, 13, 27, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51};

    public static void main(String[] args){
        print();
    }

    public static void print(){
        Hashtable<String,String> map = new Hashtable<>();
        map.put(null,"hzm");
        System.out.println(map.get(null));
        map.put(null,"hzm");
        System.out.println(map.get(null));
    }


/*    public void insertSort(){

    }

    public void insertSort(){

    }*/
}
