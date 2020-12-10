package pl.tomek.fixthat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.tomek.fixthat.dto.user.UserDetailsDto;
import pl.tomek.fixthat.entity.user.User;
import pl.tomek.fixthat.exception.UserNotFoundException;
import pl.tomek.fixthat.util.GenericResponse;
import pl.tomek.fixthat.service.user.UserDetailsService;
import pl.tomek.fixthat.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private Environment env;
    private JavaMailSender mailSender;
    private MessageSource messages;


    @Autowired
    public UserController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/details")
    public String showDetails(@CurrentSecurityContext(expression="authentication.name")String username,
                              Model model){
        model.addAttribute("userDto",userService.getUserInfo(username));
        model.addAttribute("userDetails",userDetailsService.getDetailsForUser(username));
        return "user/userdetails";
    }
    @PostMapping("/editemail")
    public String editEmail(@RequestParam String email){
        userService.updateEmail(email);
        return "redirect:/logout";
    }
    @PostMapping("/editpassword")
    public String editPassword(@RequestParam String password){
        userService.updatePassword(password);
        return "redirect:/details";
    }

    @GetMapping("/editdetails")
    public String editDetails(Model model,
                              @CurrentSecurityContext(expression = "authentication.name")String email){
        model.addAttribute("userDetails",userDetailsService.getDetailsForUser(email));
        return "user/editdetails";
    }

    @PostMapping("/editdetails")
    public String submitDetails(UserDetailsDto userDetailsDto){
        userDetailsService.saveDetails(userDetailsDto);
        return "redirect:/details";
    }


    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request),
                request.getLocale(), token, user));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }

//    @GetMapping("/user/changePassword")
//    public String showChangePasswordPage(Locale locale, Model model,
//                                         @RequestParam("token") String token) {
//        String result = securityService.validatePasswordResetToken(token);
//        if(result != null) {
//            String message = messages.getMessage("auth.message." + result, null, locale);
//            return "redirect:/login.html?lang="
//                    + locale.getLanguage() + "&message=" + message;
//        } else {
//            model.addAttribute("token", token);
//            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
//        }
//    }

//    @PostMapping("/user/savePassword")
//    public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
//
//        String result = securityUserService.validatePasswordResetToken(passwordDto.getToken());
//
//        if(result != null) {
//            return new GenericResponse(messages.getMessage(
//                    "auth.message." + result, null, locale));
//        }
//
//        Optional user = userService.getUserByPasswordResetToken(passwordDto.getToken());
//        if(user.isPresent()) {
//            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
//            return new GenericResponse(messages.getMessage(
//                    "message.resetPasswordSuc", null, locale));
//        } else {
//            return new GenericResponse(messages.getMessage(
//                    "auth.message.invalid", null, locale));
//        }
//    }



    public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
        String url = contextPath + "/user/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
