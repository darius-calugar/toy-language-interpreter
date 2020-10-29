package api.model.collections;

import org.junit.jupiter.api.Test;

public class TestDictionaryJavaMap {
    private final IDictionary<Integer, Integer> dictionary = new DictionaryJavaMap<Integer, Integer>();

    @Test
    void testSetEntry_newEntry_entryIsSet() {
        assert !dictionary.isDefined(1);
        assert !dictionary.isDefined(2);
        dictionary.set(1, 2);
        dictionary.set(2, 5);
        assert dictionary.isDefined(1);
        assert dictionary.isDefined(2);
        assert dictionary.get(1) == 2;
        assert dictionary.get(2) == 5;
    }

    @Test
    void testRemoveEntry_existingEntry_entryIsRemoved() {
        dictionary.set(1, 2);
        dictionary.set(2, 5);
        assert dictionary.isDefined(1);
        assert dictionary.isDefined(2);
        assert dictionary.remove(2) == 5;
        assert dictionary.isDefined(1);
        assert !dictionary.isDefined(2);
    }
}
