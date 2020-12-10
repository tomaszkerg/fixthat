package pl.tomek.fixthat.mapper;

import pl.tomek.fixthat.dto.OrderShowAllDto;
import pl.tomek.fixthat.entity.Order;

public class OrderShowAllMapper {

    public static OrderShowAllDto toDto(Order order) {
        OrderShowAllDto orderShowAllDto = new OrderShowAllDto();
        orderShowAllDto.setId(order.getId());
        orderShowAllDto.setName(order.getName());
        orderShowAllDto.setDescription(shortDescription(order.getDescription()));
        orderShowAllDto.setPostTime(order.getPostTime());
        orderShowAllDto.setDevice(order.getDevice().getModel());
        return orderShowAllDto;
    }

    public static String shortDescription(String description){
        if(description.length()>50) return description.substring(0,49);
        return description;
    }
}
