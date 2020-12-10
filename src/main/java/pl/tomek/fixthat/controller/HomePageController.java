package pl.tomek.fixthat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.tomek.fixthat.dto.user.UserDto;

@Controller
@RequestMapping("")
public class HomePageController {

    @GetMapping("")
    public String homePage(Model model){
        model.addAttribute("userDto",new UserDto());
        return "index";
    }

}
