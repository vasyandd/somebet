package ru.spb.somebet.model;

import java.util.Collection;
import java.util.Map;

public class LiveMatch extends FutureMatch {
    private Collection<String> progressMatch;
    private Map<String, Integer> score;
    private boolean isFinish;
}
