package sbu.cs;

/*
    In this exercise, you must write a multithreaded program that finds all
    integers in the range [1, n] that are divisible by 3, 5, or 7. Return the
    sum of all unique integers as your answer.
    Note that an integer such as 15 (which is a multiple of 3 and 5) is only
    counted once.

    The Positive integer n > 0 is given to you as input. Create as many threads as
    you need to solve the problem. You can use a Thread Pool for bonus points.

    Example:
    Input: n = 10
    Output: sum = 40
    Explanation: Numbers in the range [1, 10] that are divisible by 3, 5, or 7 are:
    3, 5, 6, 7, 9, 10. The sum of these numbers is 40.

    Use the tests provided in the test folder to ensure your code works correctly.
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class FindMultiples
{

    // TODO create the required multithreading class/classes using your preferred method.
    private static final int numThreads = 3;

    public static class SumArray  extends Thread{
        private int lo, hi;
        private int[] arr;

        private long ans = 0;

        public SumArray(int[] arr, int lo, int hi){
            this.lo = lo;
            this.hi = hi;
            this.arr = arr;
        }

        @Override
        public void run() {
            for (int i = lo; i < hi; i++) {
                ans += arr[i];
            }
        }

        public double getAns() {
            return ans;
        }
    }



    /*
    The getSum function should be called at the start of your program.
    New Threads and tasks should be created here.
    */
    public static   long getSum(int n)  {
        //int sum = 0;


        // TODO
        int[] arrayNum=new int[n];
        int[] finalArray=new int[n];
        long ans = 0;

        int count=0;
        for(int i=0;i<n;i++){
            arrayNum[i]=i+1;
        }
        for (int i:arrayNum){
            if(i%3==0){
                if(IntStream.of(finalArray).noneMatch(x->x==i)){
                    finalArray[count]=i;
                    count++;
                }

            }
        }
        for (int i:arrayNum){
            if(i%5==0){
                if(IntStream.of(finalArray).noneMatch(x->x==i)){
                    finalArray[count]=i;
                    count++;
                }

            }
        }
        for (int i:arrayNum){
            if(i%7==0){
                if(IntStream.of(finalArray).noneMatch(x->x==i)){
                    finalArray[count]=i;
                    count++;
                }

            }
        }

        SumArray[] ts = new SumArray[numThreads];
        for (int i = 0; i < numThreads; i++) {
            ts[i] = new SumArray(finalArray, (i * finalArray.length) / numThreads, ((i + 1) * finalArray.length / numThreads));
            ts[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                ts[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ans +=ts[i].getAns();

        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("Enter the number  = ");
        Scanner sc=new Scanner(System.in);
        int num=sc.nextInt();
        System.out.println(getSum(num));
    }
}