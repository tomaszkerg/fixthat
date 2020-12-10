package pl.tomek.fixthat.mapper;

import pl.tomek.fixthat.dto.MessageListDto;
import pl.tomek.fixthat.entity.Message;

public class MessageListMapper {

    public static MessageListDto toDto(Message message){
        return MessageListDto.builder()
                .id(message.getId())
                .sendDate(message.getSendDate())
                .title(message.getTitle())
                .build();
    }
}
