package com.tsfeng.cn.algorithmic;

import java.util.Arrays;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/28 22:29
 * 堆排序
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] a = {35, 26, 26, 9, 28, 42};
        System.out.println(Arrays.toString(a));
        buildMaxHeapify(a);
        System.out.println(Arrays.toString(a));
        heapSort(a);
        System.out.println(Arrays.toString(a));
    }

    /**
     *排序，最大值放在末尾，data虽然是最大堆，在排序后就成了递增的
     *@param data 要排序数组
     */
    private static void heapSort(int[] data){
        //末尾与头交换，交换后调整最大堆
        for(int i=data.length-1;i>0;i--){
            int temp=data[0];
            data[0]=data[i];
            data[i]=temp;
            maxHeapify(data,i,0);
        }
    }

    private static void buildMaxHeapify(int[] data) {
        //没有子节点的才需要创建最大堆，从最后一个的父节点开始
        int startIndex = getParentIndex(data.length - 1);
        //从尾端开始创建最大堆，每次都是正确的堆
        for (int i = startIndex; i >= 0; i--) {
            maxHeapify(data, data.length, i);
        }
    }

    /**
     * 创建最大堆
     *
     * @param data  数组对象
     * @param heapSize 需要创建最大堆的大小，一般在sort的时候用到，因为最多值放在末尾，末尾就不再归入最大堆了
     * @param index    当前需要创建最大堆的位置
     */
    private static void maxHeapify(int[] data, int heapSize, int index) {
        //当前点与左右子节点比较
        int left = getChildLeftIndex(index);
        int right = getChildRightIndex(index);

        int largest = index;
        if (left < heapSize && data[index] < data[left]) {
            largest = left;
        }
        if (right < heapSize && data[largest] < data[right]) {
            largest = right;
        }
        //得到最大值后可能需要交换，如果交换了，其子节点可能就不是最大堆了，需要重新调整
        if (largest != index) {
            int temp = data[index];
            data[index] = data[largest];
            data[largest] = temp;
            maxHeapify(data, heapSize, largest);
        }
    }


    /**
     * 父节点位置
     * @param current 当前节点位置
     * @return 父节点位置
     */
    private static int getParentIndex(int current) {
        return (current - 1) >> 1;
    }

    /**
     * 左子节点position注意括号，加法优先级更高
     * @param current 当前节点位置
     * @return 左子节点position
     */
    private static int getChildLeftIndex(int current) {
        return (current << 1) + 1;
    }

    /**
     * 右子节点position
     * @param current 当前节点位置
     * @return 右子节点position
     */
    private static int getChildRightIndex(int current) {
        return (current << 1) + 2;
    }

}
