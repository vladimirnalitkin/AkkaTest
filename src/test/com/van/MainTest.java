package com.van;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import org.junit.Test;
import sample.van.stacks.BlockingStack;
import sample.van.stacks.IStack;
import sample.van.stacks.SimpleStack;
import com.van.common.TestEng;
import sample.van.stacks.UnblockStack;

import static com.van.common.CPUUtils.getSystemTime;
import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void akkaTypeTest() throws InterruptedException {
        ActorSystem _system = ActorSystem.create("TypedActorsExample");
        IStack<Integer> stack = TypedActor.get(_system).typedActorOf(
                new TypedProps<SimpleStack>(IStack.class, SimpleStack.class));

        long startSystemTimeNano = getSystemTime();

        TestEng testEng = new TestEng(20, stack, "akkaTypeTest");
        testEng.run();

        System.out.println("That took " + (getSystemTime() - startSystemTimeNano) + " milliseconds");

        _system.shutdown();
    }

    @Test
    public void unblockingTest() throws InterruptedException {

        long startSystemTimeNano = getSystemTime();

        TestEng testEng = new TestEng(20, new UnblockStack(), "unblockingTest");
        testEng.run();

        System.out.println("That took " + (getSystemTime() - startSystemTimeNano) + " milliseconds");
    }

    @Test
    public void blockingTest() throws InterruptedException {

        long startSystemTimeNano = getSystemTime();

        TestEng testEng = new TestEng(20, new BlockingStack(), "blockingTest");
        testEng.run();

        System.out.println("That took " + (getSystemTime() - startSystemTimeNano) + " milliseconds");
    }

  /*  @Test(expected = java.lang.NullPointerException.class)
    public void simpleTest() throws InterruptedException {

        long startSystemTimeNano = getSystemTime( );

        TestEng testEng = new TestEng(20, new SimpleStack());
        testEng.run();

        System.out.println("That took " + (getSystemTime( ) - startSystemTimeNano) + " milliseconds");
    }*/
}
