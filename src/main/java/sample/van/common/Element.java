package sample.van.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Element<T> {
    @Getter
    private final T value;
    @Getter
    @Setter
    private Element<T> next;
    @Getter
    private final int size;

    public Element(T value, Element<T> next) {
        this.value = value;
        this.next = next;
        this.size = next == null ? 1 : next.getSize() + 1;
    }

    public Element(T value, Element<T> next, int size) {
        this.value = value;
        this.next = next;
        this.size = size;
    }
}