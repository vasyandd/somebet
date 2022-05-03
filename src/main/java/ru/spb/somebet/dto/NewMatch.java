package ru.spb.somebet.dto;

import lombok.Data;
import ru.spb.somebet.model.Region;

import java.time.LocalDateTime;

@Data
public class NewMatch {
    private String description;
    private String[] teams = new String[2];
    private String region;
    private LocalDateTime startDate;
}
