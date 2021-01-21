package com.test.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class NacosDiscovery {

    public static NamingService namingService;

    static {
        try {
            namingService = NacosFactory.createNamingService("127.0.0.1" + ":" + 8848);
            System.out.println("ljw----"+namingService);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    public void RegisterInstance() {
        try {
            String serverIp = "127.0.0.1";
            int serverPort = 8848;
            String serviceName = "nacos-sdk-java-discovery";

            // 注册实例
            //System.out.println("注册实例");
            int ran = (int) (Math.random() * 10000);
            var tempName = serviceName + String.valueOf(ran);
            namingService.registerInstance(tempName, serverIp, serverPort);
            //System.out.println("注册实例成功");

        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
