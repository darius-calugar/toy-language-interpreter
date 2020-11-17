package api.model.collections;

import org.junit.jupiter.api.Test;

public class TestMyStack {
    IStack<Integer> stack = new MyStack<>();

    @Test
    void testPush_newEntry_entryPushed() {
        stack.push(0);
        stack.push(1);
        stack.push(2);
        assert stack.peek() == 2;
    }

    @Test
    void testPop_stackNonEmpty_entryPopped() {
        stack.push(0);
        stack.push(1);
        stack.push(2);
        assert stack.pop() == 2;
        assert stack.peek() == 1;
    }
}
