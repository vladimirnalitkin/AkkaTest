package sample.van.stacks;

import akka.dispatch.Futures;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sample.van.common.Element;

import java.util.concurrent.atomic.AtomicReference;

public class UnblockStack implements IStack<Integer> {

    private final AtomicReference<Element<Integer>> headRef = new AtomicReference<>(null);

    public Integer push(Integer value) {
        Element<Integer> newHead;
        Element<Integer> oldHead;
        do {
            oldHead = headRef.get();
            newHead = new Element<>(value, oldHead);
        } while (!headRef.compareAndSet(oldHead, newHead));
        return headRef.get().getSize();
    }

    public Element<Integer> pop() {
        Element<Integer> oldHead = null;
        Element<Integer> newHead = null;
        do {
            oldHead = headRef.get();
            if (oldHead == null) {
                return new Element<Integer>(0, null, 0);
            }
            newHead = oldHead.getNext();
        } while (!headRef.compareAndSet(oldHead, newHead));
        return oldHead;
    }

}

