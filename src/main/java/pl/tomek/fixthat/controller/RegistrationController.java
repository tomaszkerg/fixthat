package pl.tomek.fixthat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.tomek.fixthat.dto.user.UserDto;
import pl.tomek.fixthat.exception.DuplicateEmailException;
import pl.tomek.fixthat.exception.DuplicateUsernameException;
import pl.tomek.fixthat.service.user.UserService;


import javax.validation.Valid;

@Controller
@RequestMapping("/register")

public class RegistrationController {


    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public String getFrom(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto",userDto);
        return "user/register";
    }
    @PostMapping("")
    public String submitForm(@Valid @ModelAttribute(name = "userDto") UserDto userDto,
                             Model model, BindingResult result){
        try{
            userService.checkEmailDuplicate(userDto);

        }catch (DuplicateEmailException e) {
            result.rejectValue("email",null,"Konto z takim adresem email już istnieje");
        }
        try{
            userService.checkUsernameDuplicate(userDto);

        }catch (DuplicateUsernameException e) {
            result.rejectValue("username",null,"Konto z takią nazwą juz istnieje");
        }
        if(result.hasErrors()){
            return "user/register";
        }
        userService.save(userDto);
        return "redirect:/login";
    }
}
