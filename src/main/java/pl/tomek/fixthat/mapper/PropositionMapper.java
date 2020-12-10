package pl.tomek.fixthat.mapper;

import pl.tomek.fixthat.dto.PropositionDto;
import pl.tomek.fixthat.entity.Proposition;

import java.time.LocalDateTime;

public class PropositionMapper {


    public static Proposition toEntity(PropositionDto propositionDto) {
            Proposition proposition = new Proposition();
            proposition.setPrice(propositionDto.getPrice());
            proposition.setPropositionTime(LocalDateTime.now());
            proposition.setFinishTime(LocalDateTime.now().plusDays(propositionDto.getDays()));
            return proposition;
    }
}
