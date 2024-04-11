package frc.robot.subsystems;

import java.util.Arrays;

public class MedianFilter 
{
    private float[] arrayForFilter;
    private int filterPowerInit = 10;
    private int counter = 0;
    private float prevResult = 0;
    private float sum = 0;

    public MedianFilter()
    {
        this.arrayForFilter = new float[this.filterPowerInit];
    }

    public MedianFilter(int filterPower)
    {
        this.filterPowerInit = filterPower;
        this.arrayForFilter = new float[this.filterPowerInit];
    }

    public MedianFilter(int filterPower, int digit)
    {
        this.filterPowerInit = filterPower;
        this.arrayForFilter = new float[this.filterPowerInit];
        for (int i = 0; i < this.arrayForFilter.length; ++i)
        { 
            this.arrayForFilter[i] = digit;
        }
    }

    public float Filter(float val)
    {
        for (int i = filterPowerInit - 1; i > 0; i--)
        {
            this.arrayForFilter[i] = this.arrayForFilter[i - 1];
        }
        this.arrayForFilter[0] = val;

        float[] copiedArr = Arrays.copyOf(this.arrayForFilter, this.filterPowerInit);
        quickSort(copiedArr, 0, this.filterPowerInit - 1);
        return copiedArr[this.filterPowerInit / 2];
    }

    public float adapFilter(float val){
        float filVal = 0;
        float k;
        if(Math.abs(val - filVal) > 1.5){
            k = 0.9f;
        }else{
            k = 0.035f;
        }

        filVal += (val - filVal) * k;
        return filVal;
    }

    public float midFilter(float val){
        sum += val;
        counter++;
        if(counter == 10){
            prevResult = sum / 10;
            sum = 0;
            counter = 0;
        }
        return prevResult;
    }

    public float GetValue()
    {
        float[] copiedArr = Arrays.copyOf(this.arrayForFilter, this.filterPowerInit);
        quickSort(copiedArr, 0, this.filterPowerInit - 1);
        return copiedArr[this.filterPowerInit / 2];
    }

    public void Reset()
    {
        this.arrayForFilter = new float[this.filterPowerInit];
    }

    public void Reset(int digit)
    {
        for (int i = 0; i < this.arrayForFilter.length; ++i)
        {
            this.arrayForFilter[i] = digit;
        }
    }
    
    public void quickSort(float arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
     
            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private int partition(float arr[], int begin, int end) {
        float pivot = arr[end];
        int i = (begin-1);
     
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
     
                float swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
     
        float swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;
     
        return i+1;
    }
}