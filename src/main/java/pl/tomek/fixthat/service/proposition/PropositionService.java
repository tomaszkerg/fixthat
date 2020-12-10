package pl.tomek.fixthat.service.proposition;

import pl.tomek.fixthat.dto.PropositionDto;
import pl.tomek.fixthat.dto.PropositionShowDto;
import pl.tomek.fixthat.entity.Proposition;
import pl.tomek.fixthat.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface PropositionService {

    List<Proposition> findAllPropositions();
    Proposition saveProposition(PropositionDto propositionDto);
    Optional<Proposition> getPropositionById(Long id);
    boolean deletePropositionById(Long id);
    List<PropositionShowDto> findAllPropositionForOrder(Long id);
    List<PropositionShowDto> findPropositionsForUser(String username);
    void sendMessageAboutFiveP(User user);
    void sendMessageAboutBeingChosen(User user);
    void sendMessageTryingToManageDifferentUserOrder(User user,Long orderId);
    void choosePropositionForOrder(Long id);
}
