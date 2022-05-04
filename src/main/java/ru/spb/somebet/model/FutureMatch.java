package ru.spb.somebet.model;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.spb.somebet.dto.BetDto;
import ru.spb.somebet.dto.FutureMatchDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "FUTURE_MATCHES")
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
public class FutureMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Type(type = "string-array")
    @Column(
            name = "teams",
            columnDefinition = "text[]"
    )
    private String[] teams = new String[2];

    private Region region;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            orphanRemoval = true, mappedBy = "futureMatch")
    private Collection<Bet> bets = new ArrayList<>();

    private LocalDateTime startTime;

    public static FutureMatchDto modelToDto(FutureMatch futureMatch) {
        List<BetDto> betsDto = new ArrayList<>();
        for (Bet oldBet : futureMatch.bets) {
            betsDto.add(new BetDto(oldBet.getId(), oldBet.getValue(), oldBet.getType()));
        }
        return new FutureMatchDto(futureMatch.id, futureMatch.description,
                futureMatch.teams, futureMatch.region, betsDto, futureMatch.startTime);
    }
}
