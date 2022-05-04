package ru.spb.somebet.service.live;

import ru.spb.somebet.dto.LiveMatchDto;
import ru.spb.somebet.model.LiveMatch;

import java.util.Collection;

public interface LiveMatchService {

    Collection<LiveMatchDto> getLiveMatches();

}
