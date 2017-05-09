package com.van;

import akka.dispatch.Futures;
import org.junit.Test;
import sample.van.common.Element;
import sample.van.stacks.IStack;
import sample.van.stacks.SimpleStack;
import sample.van.stacks.UnblockStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleTest {
    private static final Integer[] testArr = {2, 34, 45, 6, 5, 65, 6};

    @Test
    public void testSimpleStack() {
        testStack(new SimpleStack());
    }

    @Test
    public void testUnblockStack() {
        testStack(new UnblockStack());
    }

    private void testStack(IStack<Integer> stack) {
        Arrays.asList(testArr).forEach(i -> System.out.print(stack.push(i)));
        System.out.println();
        List list = Arrays.asList(testArr);
        Collections.reverse(list);
        list.forEach(i -> {
            Element<Integer> element = stack.pop();
            System.out.println(element.getSize() + " | " + element.getValue());
            assertEquals(i, element.getValue());
        });
        System.out.println();
    }
}
