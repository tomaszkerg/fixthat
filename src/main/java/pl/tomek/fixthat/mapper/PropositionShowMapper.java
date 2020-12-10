package pl.tomek.fixthat.mapper;


import pl.tomek.fixthat.dto.PropositionShowDto;
import pl.tomek.fixthat.entity.Proposition;

public class PropositionShowMapper {



    public static PropositionShowDto toDto(Proposition proposition) {
        PropositionShowDto propositionshowDto = new PropositionShowDto();
        propositionshowDto.setId(proposition.getId());
        propositionshowDto.setPrice(proposition.getPrice());
        propositionshowDto.setFinishTime(proposition.getFinishTime());
        propositionshowDto.setPropositionTime(proposition.getPropositionTime());
        propositionshowDto.setOrderId(proposition.getOrder().getId());
        propositionshowDto.setUsername(proposition.getUser().getUsername());
        return propositionshowDto;
    }
//
//    public static Proposition toEntity(PropositionDto propositionDto) {
//        Proposition proposition = new Proposition();
//        proposition.setId(propositionDto.getId());
//        proposition.setPrice(propositionDto.getPrice());
//        proposition.setPropositionTime(propositionDto.getPropositionTime());
//        proposition.setUser(userService.getUserByEmail(propositionDto.getUserEmail()));
//        return proposition;
//    }
}
