package pl.tomek.fixthat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.tomek.fixthat.dto.PropositionDto;
import pl.tomek.fixthat.dto.PropositionShowDto;
import pl.tomek.fixthat.service.order.OrderService;
import pl.tomek.fixthat.service.proposition.PropositionService;
import pl.tomek.fixthat.service.user.UserService;

import javax.validation.Valid;

@Controller
@Slf4j
public class PropositionController {

    private final PropositionService propositionService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public PropositionController(PropositionService propositionService, UserService userService,
                                 OrderService orderService) {
        this.propositionService = propositionService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/orders/{id}/add")
    public String addProposition(@PathVariable Long id, Model model){
        model.addAttribute("proposition",new PropositionDto());
        model.addAttribute("order",id);
        return "proposition/addproposition";
    }

    @PostMapping("/orders/{id}/add")
    public String submitProposition(@PathVariable Long id, @Valid PropositionDto propositionDto,
                                    @CurrentSecurityContext(expression="authentication.name")String email,
                                    BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("proposition",propositionDto);
            model.addAttribute("order",id);
            return "order/addorder";
        }
        propositionDto.setUserEmail(email);
        propositionDto.setOrderId(id);
        propositionService.saveProposition(propositionDto);
        return "redirect:/myorders";
    }
    @GetMapping("/orders/{id}/remove")
    public String deleteProposition(@PathVariable Long id, Model model, @RequestParam Long idP){
        propositionService.deletePropositionById(idP);
        return "redirect:/orders/"+id;
    }
    @GetMapping("/mypropositions")
    public String myPropositions(Model model,
                                 @CurrentSecurityContext(expression="authentication.name")String username){
        model.addAttribute("propositions",propositionService.findPropositionsForUser(username));
        return "proposition/mypropositions";
    }

    @GetMapping("/propositions/{id}")
    public String propositionsForOrder(@PathVariable Long id,Model model){
        model.addAttribute("propositions",propositionService.findAllPropositionForOrder(id));
        return "proposition/propositions";
    }
    @PostMapping("/propositions/choose")
    public String chooseProposition(@RequestParam(name = "pId") Long id){
        propositionService.choosePropositionForOrder(id);
        return "order/getsendForm";
    }
//    @GetMapping("/mypropositions/{id}")
//    public String propositionForOrder(){
//
//    }
//    @PostMapping("/orders/{id}/remove")
//    public String submitDelete(@PathVariable Long id, PropositionDto propositionDto,
//                                    @CurrentSecurityContext(expression="authentication.name")String email){
//        propositionDto.setUserEmail(email);
//        propositionDto.setOrderId(id);
//        propositionService.save(propositionDto);
//        return "redirect:/myorders";
//    }

}
