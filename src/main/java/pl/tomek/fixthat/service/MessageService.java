package pl.tomek.fixthat.service;

import pl.tomek.fixthat.dto.MessageListDto;

import java.util.List;

public interface MessageService {

    List<MessageListDto> findAllForUserUserName(String username);
}
