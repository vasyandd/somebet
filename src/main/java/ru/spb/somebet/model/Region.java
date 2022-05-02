package ru.spb.somebet.model;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Region {
    RUSSIA("ru"),
    SPAIN("sp"),
    ENGLAND("en"),
    FRANCE("fr"),
    GERMANY("gr"),
    ITALY("it");

    private final String value;

    Region(String value) {
        this.value = value;
    }

    private final static Map<String, Region> regions = Stream.of(values())
            .collect(Collectors.toMap(e -> e.value, e -> e));

    public static Region of(String region) {
        return regions.get(region);
    }
}
