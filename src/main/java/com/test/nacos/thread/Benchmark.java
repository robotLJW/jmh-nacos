package com.test.nacos.thread;

import java.util.ArrayList;

public class Benchmark {
    public static void main(String[] args) throws InterruptedException {
        int n = 2;
        int m = 5;
        long[][] result = new long[n][m];
        ArrayList<MyThread> tList = new ArrayList<>();
        ShareData count = new ShareData(n);
        for (int i = 0; i < n; i++) {
            MyThread thread = new MyThread("127.0.0.1", "8848", i, m, count, result[i]);
            tList.add(thread);
        }
        for (int i = 0; i < tList.size(); i++) {
            MyThread thread = tList.get(i);
            thread.start();
        }
        while (true) {
            int res = count.Get();
            if (res != 0) {
                Thread.sleep(10000);
            } else {
                break;
            }
        }
        System.out.println("-----------cal the result----------------");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.println(result[i][j]);
            }
        }
    }
}
