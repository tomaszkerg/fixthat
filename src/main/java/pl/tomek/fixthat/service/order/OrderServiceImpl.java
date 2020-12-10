package pl.tomek.fixthat.service.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.tomek.fixthat.dto.FinishedOrderDto;
import pl.tomek.fixthat.dto.OrderDto;
import pl.tomek.fixthat.dto.OrderShowAllDto;
import pl.tomek.fixthat.entity.Order;
import pl.tomek.fixthat.entity.Proposition;
import pl.tomek.fixthat.exception.OrderNotFoundException;
import pl.tomek.fixthat.mapper.FinishedOrderMapper;
import pl.tomek.fixthat.mapper.OrderMapper;
import pl.tomek.fixthat.mapper.OrderShowAllMapper;
import pl.tomek.fixthat.repository.DeviceRepository;
import pl.tomek.fixthat.repository.OrderRepository;
import pl.tomek.fixthat.repository.PropositionRepository;
import pl.tomek.fixthat.repository.UserRepository;
import pl.tomek.fixthat.service.user.ContextService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final PropositionRepository propositionRepository;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, PropositionRepository propositionRepository,
                            UserRepository userRepository, DeviceRepository deviceRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.propositionRepository = propositionRepository;
    }

    @Override
    public List<OrderShowAllDto> findAllOrdersDto() {
        return orderRepository.findAll()
                .stream()
                .map(OrderShowAllMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Order saveOrder(OrderDto orderDto) {
        Order order = new Order();
        String username = ContextService.getUsername();
        if(orderDto.getId()==null){
            order.setPostTime(LocalDateTime.now());
            order.setUser(userRepository.getByUsername(username));
            order.setActive(true);
            log.info("dodawanie nowego zlecenia o nazwie = {} przez usera {}",order.getName(),username);
        }else{
            order = getOrderById(orderDto.getId()).orElseThrow(()->{
                throw new OrderNotFoundException();
            });
            log.info("edytowanie zlecenia o id = {} przez usera {}",order.getId(),username);
        }
        order.setDescription(orderDto.getDescription());
        order.setName(orderDto.getName());
        order.setDevice(deviceRepository.getOne(orderDto.getDeviceId()));
        return orderRepository.save(order);
    }


    @Override
    public Optional<Order> getOrderById(Long id) {
        String email = ContextService.getUsername();
        log.info("pobieranie zlecenia o id = {} przez usera {}",id,email);
        return orderRepository.findById(id);
    }

    @Override
    public boolean deleteOrderById(Long id) {
        String email = ContextService.getUsername();
        log.info("usuwanie zlecenia o id = {} przez usera {}",id,email);
        orderRepository.deleteById(id);
        return !orderRepository.existsById(id);
    }

    @Override
    public OrderShowAllDto getOrder(Long id) {
        return getOrderById(id)
                .map(OrderShowAllMapper::toDto)
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public OrderDto getOrderDto(Long id){
        return getOrderById(id)
                .map(OrderMapper::toDto)
                .orElseThrow();
    }

    @Override
    public List<FinishedOrderDto> findAllFinishedForUser() {
        return orderRepository.findAllByUserUsernameAndActiveFalse(ContextService.getUsername())
                .stream()
                .map(o -> {
                    Proposition proposition = propositionRepository.getFirstByOrder(o);
                    return FinishedOrderMapper.toDto(o,proposition);
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkIfOrderIsActive(Long id) {
        return orderRepository.findById(id).orElseThrow(()->{
            throw new OrderNotFoundException();
        }).isActive();
    }


    @Override
    public List<OrderShowAllDto> findAllOrdersForUser(String username) {
        return orderRepository
                .findAllByUserUsernameAndActiveTrue(username)
                .stream()
                .map(OrderShowAllMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkIfOrderIsForUser(Long id,String username) {
        return orderRepository.findById(id).orElseThrow( () -> {
            throw new OrderNotFoundException();
        }).getUser().getUsername().equals(username);
    }


    @Override
    public Page<OrderShowAllDto> findAllOrdersPageable(Pageable pageable) {
        return orderRepository.findAllByActiveTrue(pageable)
                .map(OrderShowAllMapper::toDto);
    }

    @Override
    public List<OrderShowAllDto> findAllOrdersForUserProposition() {
        List<Proposition> propositions = propositionRepository.findAllByUserUsername(ContextService.getUsername());
        List<Order> orders = new ArrayList<>();
        for(Proposition p:propositions){
            orders.add(orderRepository.getOne(p.getOrder().getId()));
        }
        return orders.stream()
                .map(OrderShowAllMapper::toDto)
                .collect(Collectors.toList());
    }

}
