package pl.tomek.fixthat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.fixthat.dto.MessageListDto;
import pl.tomek.fixthat.mapper.MessageListMapper;
import pl.tomek.fixthat.repository.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<MessageListDto> findAllForUserUserName(String username) {
        return messageRepository.findAllByReceiverUsername(username)
                .stream()
                .map(MessageListMapper::toDto)
                .collect(Collectors.toList());
    }
}
