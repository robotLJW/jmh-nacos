package com.test.nacos.thread;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;

class ShareData {
    private int j = 0;

    public ShareData(int n) {
        this.j = n;
    }

    public synchronized void increment() {
        j--;
    }

    public int Get() {
        return this.j;
    }
}

public class MyThread extends Thread {
    public static NamingService namingService;
    public int start;
    public int requestNum;
    public long[] result;
    public ShareData Count;

    public MyThread(String serverAddr, String port, int s, int num, ShareData sd, long[] res) {
        try {
            this.start = s;
            this.requestNum = num;
            this.result = res;
            this.Count = sd;
            namingService = NacosFactory.createNamingService(serverAddr + ":" + port);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i = start; i < start + requestNum; i++) {
            try {
                long startTime = System.nanoTime();
                RegisterInstance(i);
                result[i - start] = System.nanoTime() - startTime;
                System.out.println(result[i - start]);
            } catch (NacosException e) {
                e.printStackTrace();
            }
        }
        this.Count.increment();
    }

    public void RegisterInstance(int i) throws NacosException {
        String instanceName = "nacos-sdk-java-discovery" + i;
        String instanceIp = "127.0.0.1";
        int port = 8848;
        namingService.registerInstance(instanceName, instanceIp, port);
    }


}
