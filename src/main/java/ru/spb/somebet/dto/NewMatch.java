package ru.spb.somebet.dto;

import lombok.Data;
import ru.spb.somebet.model.Region;

import java.time.LocalDateTime;

@Data
public class NewMatch {
    private String description;
    private String team1;
    private String team2;
    private Region region;
    private LocalDateTime startDate;
}
