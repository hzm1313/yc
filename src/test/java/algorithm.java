/**
 * Created by DT167 on 2018/3/5.
 */
public class algorithm {
    private static final int[] NUMBERS =
            {49, 38, 65, 97, 76, 13, 27, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51};

    public static void main(String[] args){
        int[] numbers = NUMBERS;
        print(numbers);
        numbers = null;
        print(numbers);
    }

    public static void print(int[] numbers){
        for(int i=0;i<NUMBERS.length;i++){
            System.out.println(NUMBERS[i]);
        }
    }

/*    public void insertSort(){

    }

    public void insertSort(){

    }*/
}
