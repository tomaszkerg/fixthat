package pl.tomek.fixthat.service.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.tomek.fixthat.dto.FinishedOrderDto;
import pl.tomek.fixthat.dto.OrderDto;
import pl.tomek.fixthat.dto.OrderShowAllDto;
import pl.tomek.fixthat.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderShowAllDto> findAllOrdersDto();
    Order saveOrder(OrderDto orderDto);
    Optional<Order> getOrderById(Long id);
    boolean deleteOrderById(Long id);
    OrderShowAllDto getOrder(Long id);
    List<OrderShowAllDto> findAllOrdersForUser(String email);
    boolean checkIfOrderIsForUser(Long id,String email);
    Page<OrderShowAllDto> findAllOrdersPageable(Pageable pageable);
    List<OrderShowAllDto> findAllOrdersForUserProposition();
    OrderDto getOrderDto(Long id);
    List<FinishedOrderDto> findAllFinishedForUser();
    boolean checkIfOrderIsActive(Long id);
    List<FinishedOrderDto> inRepairOrders();
}
