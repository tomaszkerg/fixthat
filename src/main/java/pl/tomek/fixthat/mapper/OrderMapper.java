package pl.tomek.fixthat.mapper;

import pl.tomek.fixthat.dto.OrderDto;
import pl.tomek.fixthat.entity.Order;

public class OrderMapper {

    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setName(order.getName());
        orderDto.setDescription(order.getDescription());
        orderDto.setPostTime(order.getPostTime());
        orderDto.setDeviceId(order.getDevice().getId());
        return orderDto;
    }
}
