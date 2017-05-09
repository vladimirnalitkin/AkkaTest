package sample.van.stacks;

import sample.van.common.Element;

public class BlockingStack implements IStack<Integer>{

    private Element<Integer> headRef = null;

    synchronized public Integer push(Integer value) {
        headRef = new Element<>(value, headRef);
        return headRef.getSize();
    }

    synchronized public Element<Integer> pop() {
        if (headRef == null) {
            return new Element<>(0, null, 0);
        }
        Element<Integer> oldHead = headRef;
        headRef = oldHead.getNext();
        oldHead.setNext(null);
        return oldHead;
    }
}
