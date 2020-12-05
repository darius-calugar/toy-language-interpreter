package api.controller;

import api.model.values.IValue;
import api.model.values.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {
    public static Map<Integer, IValue> conservativeGarbageCollector(Collection<Collection<IValue>> symbolTableValueSets, Map<Integer, IValue> heap) {
        var usedAddresses = symbolTableValueSets.stream()
                .map(GarbageCollector::getAddresses)
                .reduce(new LinkedList<>(), (integers, c) -> {
                    integers.addAll(c);
                    return integers;
                });
        return heap.entrySet().stream()
                .filter(e -> usedAddresses.contains(e.getKey()) ||
                             heap.values().stream()
                                     .filter(v -> v instanceof RefValue)
                                     .anyMatch(v -> ((RefValue) v).getAddress() == e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Integer, IValue> safeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()) ||
                             heap.values().stream()
                                     .filter(v -> v instanceof RefValue)
                                     .anyMatch(v -> ((RefValue) v).getAddress() == e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Integer, IValue> unsafeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static List<Integer> getAddresses(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }
}
