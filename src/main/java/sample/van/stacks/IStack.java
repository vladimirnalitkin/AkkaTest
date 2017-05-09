package sample.van.stacks;

import sample.van.common.Element;

public interface IStack<T> {

    Integer push(T value);

    Element<T> pop();

}
