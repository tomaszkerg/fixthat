package pl.tomek.fixthat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.tomek.fixthat.service.MessageService;

@Controller
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public String getMessages(Model model, Authentication authentication){
        model.addAttribute("messages",messageService.findAllForUserUserName(authentication.getName()));
        return "message/messages";
    }
    @GetMapping("/messages/{id}")
    public String getMessage(@PathVariable Long id, Model model, Authentication authentication){
        return "message/message";
    }
}
