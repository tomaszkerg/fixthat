package pl.tomek.fixthat.mapper;

import pl.tomek.fixthat.dto.FinishedOrderDto;
import pl.tomek.fixthat.entity.Order;
import pl.tomek.fixthat.entity.Proposition;

public class FinishedOrderMapper {

    public static FinishedOrderDto toDto(Order order,Proposition proposition){
        return FinishedOrderDto.builder()
                .id(order.getId())
                .name(order.getName())
                .description(order.getDescription())
                .device(order.getDevice().getModel())
                .fixDate(proposition.getFinishTime())
                .postDate(order.getPostTime())
                .fixer(proposition.getUser().getUsername())
                .build();
    }
}
