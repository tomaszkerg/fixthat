package pl.tomek.fixthat.service.proposition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.fixthat.dto.PropositionDto;
import pl.tomek.fixthat.dto.PropositionShowDto;
import pl.tomek.fixthat.entity.Message;
import pl.tomek.fixthat.entity.Order;
import pl.tomek.fixthat.entity.Proposition;
import pl.tomek.fixthat.entity.user.User;
import pl.tomek.fixthat.mapper.PropositionMapper;
import pl.tomek.fixthat.mapper.PropositionShowMapper;
import pl.tomek.fixthat.repository.MessageRepository;
import pl.tomek.fixthat.repository.OrderRepository;
import pl.tomek.fixthat.repository.PropositionRepository;
import pl.tomek.fixthat.repository.UserRepository;
import pl.tomek.fixthat.service.user.ContextService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PropositionServiceImpl implements PropositionService{

    private final UserRepository userRepository;
    private final PropositionRepository propositionRepository;
    private final OrderRepository orderRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public PropositionServiceImpl(UserRepository userRepository,
                                  PropositionRepository propositionRepository,
                                  OrderRepository orderRepository,
                                  MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.propositionRepository = propositionRepository;
        this.orderRepository = orderRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Proposition> findAllPropositions() {
        return propositionRepository.findAll();
    }

    @Override
    public Proposition saveProposition(PropositionDto propositionDto) {
        Proposition proposition = PropositionMapper.toEntity(propositionDto);
        proposition.setUser(userRepository.getByUsername(ContextService.getUsername()));
        Order order = orderRepository.getOne(propositionDto.getOrderId());
        proposition.setOrder(order);
        proposition.setActive(true);
        if(propositionRepository.findAllByOrder_Id(propositionDto.getOrderId()).size()>3){
            sendMessageAboutFiveP(order.getUser());
            order.setActive(false);
        }
        log.info("saving proposition for order with id {} by user with id {}",
                order.getId(),proposition.getUser().getId());
        return propositionRepository.save(proposition);
    }

    @Override
    public Optional<Proposition> getPropositionById(Long id) {
        log.info("getting proposition with id {}",id);
        return propositionRepository.findById(id);
    }

    @Override
    public boolean deletePropositionById(Long id) {
        String currentUser = ContextService.getUsername();
        if(propositionRepository.existsById(id)){
            if(propositionRepository.getOne(id).getUser().getUsername().equals(currentUser)) propositionRepository.deleteById(id);
        }
        return !propositionRepository.existsById(id);
    }

    @Override
    public List<PropositionShowDto> findAllPropositionForOrder(Long id) {
        log.info("getting propositions for order with id {}",id);
        return propositionRepository.findAllByOrder_Id(id)
                .stream()
                .map(PropositionShowMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropositionShowDto> findPropositionsForUser(String username) {
        log.info("getting propositions for user {}",username);
        return propositionRepository
                .findAllByUserUsernameAndActiveTrue(username)
                .stream()
                .map(PropositionShowMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void sendMessageAboutFiveP(User user) {
        Message message = new Message();
        message.setTitle("Max limit propozycji");
        message.setContext("Osiągnąłeś już maksymalną liczbę propozycji pod swoim zgłoszeniem, wybierz jedną osobę");
        message.setReceiver(user);
        log.info("wysyłanie wiadomosc uzytkownikowi o id {} ponieważ dostał 5 propozycji do swojego zlecenia",user.getId());
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public void choosePropositionForOrder(Long id) {
        Proposition proposition = propositionRepository.getOne(id);
        proposition.setActive(false);
        propositionRepository.save(proposition);
        Order order = proposition.getOrder();
        Long orderId = order.getId();
        String currentUser = ContextService.getUsername();
        if(order.getUser().getUsername().equals(currentUser)) {
            List<Proposition> propositions = propositionRepository.findAllByOrder_Id(orderId);
            if(propositions.size()<5) {
                order.setActive(false);
                orderRepository.save(order);
            }
            for (Proposition p : propositions) {
                if (!proposition.getId().equals(p.getId())) propositionRepository.deleteById(p.getId());
            }
            sendMessageAboutBeingChosen(userRepository.getByUsername(proposition.getUser().getUsername()));
        }else{
            sendMessageTryingToManageDifferentUserOrder(userRepository.getByUsername(currentUser),orderId);
        }
    }
    @Override
    @Transactional
    public void sendMessageAboutBeingChosen(User user) {
        Message message = new Message();
        message.setTitle("Twoja propozycja została wybrana");
        message.setContext("Według zleceniodawcy złozyles najlepsza propozycje, gratulacje, za kilka dni powinenes otrzymać przesyłke, dostaniesz link do sledzenia wkrótce");
        message.setReceiver(user);
        log.info("wysyłanie wiadomosc uzytkownikowi o id {} ponieważ jego propozycja zostala wybrana",user.getId());
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public void sendMessageTryingToManageDifferentUserOrder(User user,Long orderId) {
        Message message = new Message();
        message.setTitle("Próbujesz zarządzać nie swoim orderem");
        message.setReceiver(user);
        log.info("uzytkownik o id {} ppróbował zarzadzać orderem o id {}",user.getId(),orderId);
        messageRepository.save(message);
    }

}
