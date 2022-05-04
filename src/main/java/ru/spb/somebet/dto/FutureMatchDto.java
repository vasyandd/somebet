package ru.spb.somebet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Type;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.Region;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Getter
public class FutureMatchDto {
    private final Long id;
    private final String description;
    private final String[] teams;
    private final Region region;
    private final Collection<BetDto> bets;
    private final LocalDateTime startTime;
}
