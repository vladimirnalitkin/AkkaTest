package com.van.common;

import sample.van.common.Element;
import sample.van.stacks.IStack;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.van.common.CPUUtils.getUserTime;

public class TestEng {
    private final IStack stack;
    private final Integer threadCount;
    private final String engName;

    public TestEng(Integer threadCount, IStack stack, String engName) {
        this.stack = stack;
        this.threadCount = threadCount;
        this.engName = engName;
    }

    private static class Push implements Callable<String> {
        private final IStack stack;
        private final String name;
        private final int value;

        Push(IStack stack, String name, int value) {
            this.stack = stack;
            this.name = name;
            this.value = value;
        }

        @Override
        public String call() throws Exception {
            long startUserTimeNano = getUserTime();
            int count = 0;
            for (int k = 0; k < 100; k++) {
                count = stack.push(value);
            }
            return "count =" + count + " | " + name + value + " | time : " + (getUserTime() - startUserTimeNano);
        }
    }

    private static class Pop implements Callable<String> {
        private final IStack stack;
        private final String name;

        Pop(IStack stack, String name) {
            this.stack = stack;
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            final ConcurrentHashMap<Integer, Integer> resultMap = new ConcurrentHashMap<>();
            Thread.sleep(200);
            long startUserTimeNano = getUserTime();
            Element<Integer> element;

            do {
                element = stack.pop();
                if (element.getSize() != 0) {
                    resultMap.merge(element.getValue(), 1, (oldVal, newVal) -> oldVal + 1);
                }
            } while (element.getSize() != 0);
            return resultMap.toString();
        }
    }

    public void run() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = new ArrayList<>();
        for (int i = 1; i < this.threadCount; i++) {
            callables.add(new Push(stack, engName + " Push " , i));
        }
        callables.add(new Pop(stack, engName + " Pop "));

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
    }
}


