package api.model.collections;

import org.junit.jupiter.api.Test;

public class TestMyList {
    private final IList<Integer> list = new MyList<>();

    @Test
    void testPush_newEntry_entryPushed() {
        list.push(0);
        list.push(1);
        list.push(2);
        assert list.get(0) == 0;
        assert list.get(1) == 1;
        assert list.get(2) == 2;
    }

    @Test
    void testRemove_existingEntry_entryRemoved() {
        list.push(0);
        list.push(1);
        list.push(2);
        assert list.remove(1) == 1;
        assert list.get(1) == 2;
    }
}
