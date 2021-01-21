package com.test.nacos.benchmark;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(10)
@State(Scope.Thread)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
public class NacosBenchmark {

    @State(Scope.Thread)
    public static class ThreadState {
        int x = 20;
        NamingService namingService;

        {
            try {
                namingService = NacosFactory.createNamingService("127.0.0.1" + ":" + 8848);
            } catch (NacosException e) {
                e.printStackTrace();
            }
        }
    }

    @Benchmark
    public void RegisterInstance(ThreadState state) {
        try {
            String serviceName = "nacos-sdk-java-discovery";
            int ran = (int) (Math.random() * state.x);
            var tempName = serviceName + String.valueOf(ran);
            state.namingService.registerInstance(tempName, "127.0.0.1", 8848);


        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(NacosBenchmark.class.getSimpleName())
                .output("d:/Benchmark.log")
                .build();
        new Runner(options).run();
    }

}

//.output("d:/Benchmark.log")