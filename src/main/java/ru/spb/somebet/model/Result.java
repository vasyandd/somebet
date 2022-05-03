package ru.spb.somebet.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RESULTS")
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        ),
        @TypeDef(
                name = "int-array",
                typeClass = IntArrayType.class
        )
})
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Type(type = "string-array")
    @Column(
            name = "teams",
            columnDefinition = "text[]"
    )
    private String[] teams;
    private Region region;
    @Type(type = "int-array")
    @Column(
            name = "score",
            columnDefinition = "integer[]"
    )
    private int[] score;
    private LocalDateTime endTime;


    public Result(LiveMatch match, long currentMinute) {
        this(null, match.getDescription(), match.getTeams(),
                match.getRegion(),match.getScore(), match.getStartTime().plusMinutes(currentMinute));
    }
}
