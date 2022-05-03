package ru.spb.somebet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.spb.somebet.model.Region;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class NewMatch {
    private final String description;
    private final String[] teams;
    private final String region;
    private final LocalDateTime startDate;
}
