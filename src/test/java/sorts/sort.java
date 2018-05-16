package sorts;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.test.context.TestPropertySource;

import java.util.*;
import java.util.ArrayList;

/**
 * Created by DT167 on 2018/5/10.
 */
public class sort {
    static List<Integer> strList;

    @Before
    public void initData() {
        Random rand = new Random();
        strList = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            strList.add(new Integer(rand.nextInt(1000)));
        }
    }

    @Test
    public void sortMain() {
        strList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1.compareTo(o2) > 0) {
                    return 1;
                } else if (o1.compareTo(o2) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        printList(strList);
    }

    @Test
    public void insertSort() {
        //insertsort
        Integer tmp;
        for (int i = 1; i < strList.size(); i++) {
            int j = i;
            tmp = null;
            while (j > 0 && strList.get(j).compareTo(strList.get(j - 1)) > 0) {
                tmp = strList.get(j);
                strList.set(j, strList.get(j - 1));
                strList.set(j - 1, tmp);
                j--;
            }
        }
        printList(strList);
    }

    @Test
    public void shellSort() {
        //shellSort
        Integer tmp;
        int gap = (int) Math.floor(strList.size() / 2);
        while (true) {
            for (int i = gap; i < strList.size(); i++) {
                int j = i;
                tmp = null;
                while (j >= gap && strList.get(j).compareTo(strList.get(j - gap)) > 0) {
                    tmp = strList.get(j);
                    strList.set(j, strList.get(j - 1));
                    strList.set(j - 1, tmp);
                    j = j - gap;
                }
            }
            gap = (int) Math.floor(gap / 2);
            if (gap < 1) {
                break;
            }
        }
        printList(strList);
    }

    @Test
    public void bubbleSort() {
        //bubbleSort
        Integer tmp;
        for (int i = 0; i < strList.size(); i++) {
            for (int j = 0; j < strList.size() - i - 1; j++) {
                if (strList.get(j).compareTo(strList.get(j + 1)) < 0) {
                    tmp = strList.get(j);
                    strList.set(j, strList.get(j + 1));
                    strList.set(j + 1, tmp);
                }
            }
        }
        printList(strList);
    }

    @Test
    public void quickSortMain() {
        //quickSort
        quickSort(strList, 0, strList.size() - 1);
        printList(strList);
    }

    void quickSort(List<Integer> listNum, int left, int right) {
        int i = left;
        int j = right;
        if (i >= j) {
            return;
        }
        Integer temp = listNum.get(i);
        while (i != j) {
            while (i < j && listNum.get(j).compareTo(temp) >= 0) {
                j--;
            }
            if (i < j) {
                listNum.set(i, listNum.get(j));
            }

            while (i < j && listNum.get(i).compareTo(temp) <= 0) {
                i++;
            }
            if (i < j) {
                listNum.set(j, listNum.get(i));
            }
        }
        listNum.set(i, temp);
        quickSort(listNum, left, i - 1);
        quickSort(listNum, i + 1, right);
    }

    @Test
    public void simpleSort() {
        //simpleSOrt
        Integer temp = null;
        int tempNum = 1;
        for (int i = 0; i < strList.size(); i++) {
            temp = strList.get(i);
            for (int j = i + 1; j < strList.size(); j++) {
                if (strList.get(j).compareTo(temp) > 0) {
                    tempNum = j;
                    temp = strList.get(tempNum);
                }
            }
            if (tempNum != i) {
                strList.set(tempNum, strList.get(i));
                strList.set(i, temp);
            }
        }
        printList(strList);
    }

    @Test
    public void heapSort() {
        //heapSort
        //大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]，升序
        //小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]，降序
        Integer temp = null;
        adjustHeap(strList, 0, strList.size());
        for (int i = 1; i < strList.size(); i++) {
            temp = strList.get(0);
            strList.set(0, strList.get(strList.size() - i));
            strList.set(strList.size() - i, temp);
            adjustHeap(strList, 0, strList.size() - i);
        }
        printList(strList);
    }

    void adjustHeap(List<Integer> listNum, int i, int length) {
        int k = -1;
        for (int listSub = length / 2 - 1; listSub >= 0; listSub--) {
            int temp = listNum.get(listSub);
            if (2 * listSub + 2 < length && listNum.get(2 * listSub + 2).compareTo(listNum.get(2 * listSub + 1)) > 0) {
                k = 2 * listSub + 2;
            } else {
                k = 2 * listSub + 1;
            }
            if (listNum.get(k).compareTo(temp) > 0) {
                listNum.set(listSub, listNum.get(k));
                listNum.set(k, temp);
            }
        }
    }

    @Test
    public void mergeSortMain() {
        //mergeSort
        mergeSort(strList, 0, strList.size() - 1);
        printList(strList);
    }

    void mergeSort(List<Integer> listNum, int start, int end) {
        if (end <= start) {
            return;
        }
        int middle = (start + end) / 2;
        int start1 = start;
        int end1 = middle - 1;
        int start2 = middle;
        int end2 = end;
        mergeSort(listNum, start1, end1);
        mergeSort(listNum, start2, end2);
        merge(listNum, start1, end1, start2, end2);
    }

    void merge(List<Integer> listNum, int start1, int end1, int start2, int end2) {
        int j = start2;
        List<Integer> tmpList = new ArrayList<>();
        for (int i = start1; i <= end1; i++) {
            for (; j <= end2; j++) {
                if (listNum.get(j) >= listNum.get(i)) {
                    tmpList.add(listNum.get(j));
                } else {
                    break;
                }
            }
            tmpList.add(listNum.get(i));
        }
        for (int i = start1; i < end2; i++) {
            listNum.set(i, tmpList.get(i));
        }
    }

    @Test
    public void radixSortMain() {
        //radixSort
        //Least significant digit（LSD)
        //Most significance digit（MSD)
        //radixSortLSD(strList, 2);
        List<Integer> listBucket = new ArrayList<Integer>();
        radixSortMSD(true,strList,listBucket,0,strList.size(),2);
        printList(strList);
    }

    //LSD实现
    void radixSortLSD(List<Integer> listNum, int maxDig) {
        List<List<Integer>> bubble = new ArrayList<List<Integer>>();
        for (int i = 0; i < 10; i++) {
            bubble.add(new ArrayList<>());
        }
        for (int i = 0; i <= maxDig; i++) {
            for (int j = 0; j < 10; j++) {
                bubble.get(j).clear();
            }
            for (int listNumSub = 0; listNumSub < listNum.size(); listNumSub++) {
                int dig = getDigat(listNum.get(listNumSub), i);
                bubble.get(dig).add(listNum.get(listNumSub));
            }
            listNum.clear();
            for (int listNumSub = 0; listNumSub < 10; listNumSub++) {
                if (bubble.get(listNumSub).size() > 0) {
                    for (Integer temp : bubble.get(listNumSub)) {
                        listNum.add(temp);
                    }
                }
            }
        }
    }

    //MSD实现,用数组实现
    //取高位，然后分组，递归，递归
    void radixSortMSD(boolean isInit, List<Integer> listNum,List<Integer> listBucket,int start,int end, int currentDig) {
        List<List<Integer>> listBucketTmp = new ArrayList<>();
        for(int i=0;i<10;i++){
            listBucketTmp.add(new ArrayList<>());
        }
        if(isInit) {
            for(int listNumSub = start; listNumSub < end; listNumSub++ ) {
                int dig = getDigat(listNum.get(listNumSub), currentDig);
                listBucketTmp.get(dig).add(listNum.get(listNumSub));
            }
            listNum.clear();
        } else {
            for(int listNumSub = 0; listNumSub < listBucket.size(); listNumSub++ ) {
                int dig = getDigat(listBucket.get(listNumSub), currentDig);
                listBucketTmp.get(dig).add(listBucket.get(listNumSub));
            }
        }
        for(int bucketSub = 0 ;bucketSub < 10; bucketSub++) {
            if(listBucketTmp.get(bucketSub).size()>1) {
                radixSortMSD(false,listNum,listBucketTmp.get(bucketSub),0,strList.size(),currentDig - 1);
            } else if(listBucketTmp.get(bucketSub).size() > 0){
                listNum.add(listBucketTmp.get(bucketSub).get(0));
            }
        }
    }

    //百分位
    //0：个位 1：十位 2：百位 3：千位
    int getDigat(int num, int percent) {
        int result = (num / (int) Math.pow(10,percent)) % 10;
        //System.out.println(num + "---" + percent + "-------result:---" + result);
        return result;
    }


    @Test
    public void testNum() {
        System.out.println(10^2);
        System.out.println(453%10);
    }

    void printList(List<Integer> numList) {
        numList.forEach(obj -> {
            System.out.print(obj + " ");
        });
    }
}