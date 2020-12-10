package pl.tomek.fixthat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.tomek.fixthat.dto.DeviceDto;
import pl.tomek.fixthat.dto.OrderDto;
import pl.tomek.fixthat.dto.OrderShowAllDto;
import pl.tomek.fixthat.service.DeviceService;
import pl.tomek.fixthat.service.order.OrderService;
import pl.tomek.fixthat.service.proposition.PropositionService;
import pl.tomek.fixthat.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final DeviceService deviceService;
    private final PropositionService propositionService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, DeviceService deviceService, PropositionService propositionService) {
        this.orderService = orderService;
        this.userService = userService;
        this.deviceService = deviceService;
        this.propositionService = propositionService;
    }

    @GetMapping("/orders")
    public String getOrders(@PageableDefault(size = 10, sort = "name") Pageable pageable,
                            Model model){
        Page<OrderShowAllDto> page = orderService.findAllOrdersPageable(pageable);
        List<Sort.Order> sortOrders = page.getSort().stream().collect(Collectors.toList());
        if (sortOrders.size() > 0) {
            Sort.Order order = sortOrders.get(0);
            model.addAttribute("sortProperty", order.getProperty());
            model.addAttribute("sortDesc", order.getDirection() == Sort.Direction.DESC);
        }
        model.addAttribute("page", page);
        return "order/orders";
    }

    @GetMapping("/orders/{id}")
    public String getOrder(@PathVariable Long id,Model model){
        model.addAttribute("order",orderService.getOrder(id));
        model.addAttribute("propositions",propositionService.findAllPropositionForOrder(id));
        return "order/showorder";
    }

    @GetMapping("/myorders")
    public String getMyOrders(Model model,
                              @CurrentSecurityContext(expression="authentication.name")String username){
        model.addAttribute("orders", orderService.findAllOrdersForUser(username));
        return "order/myorders";
    }

    @ModelAttribute("deviceList")
    public List<DeviceDto> loadDevices(){
        return deviceService.findAllDevices();
    }

    @GetMapping("/addorder")
    public String addOrder(Model model){
        model.addAttribute("order",new OrderDto());
        return "order/addorder";
    }

    @PostMapping("/addorder")
    public String submitOrder(Model model,@Valid OrderDto orderDto, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("order",orderDto);
            return "order/addorder";
        }
        orderService.saveOrder(orderDto);
        return "redirect:/myorders";
    }

    @GetMapping("/orders/delete/{id}")
    public String delete(Model model, @PathVariable Long id,
                         @CurrentSecurityContext(expression="authentication.name")String username){
        model.addAttribute("order",id);
        return "order/submit";
    }
    @PostMapping("orders/delete")
    public String submitDelete(@RequestParam(name = "decision") Long id, Authentication authentication, BindingResult result){
        if(!orderService.checkIfOrderIsForUser(id,authentication.getName())){
            result.rejectValue("order",null,"nie możesz usunąć czyjegoś zlecenia");
        }
        if(result.hasErrors()) return "redirect:/myorders";
        orderService.deleteOrderById(id);
        return "redirect:/myorders";
    }

    @GetMapping("/editorder/{id}")
    public String editOrder(@PathVariable Long id, Model model,
                            @CurrentSecurityContext(expression="authentication.name")String email){
        model.addAttribute("order",orderService.getOrderDto(id));
        return "order/addorder";
    }

    @PostMapping("/editorder")
    public String submitEdition(Model model, @Valid OrderDto orderDto, BindingResult result, Authentication authentication){
        if(!orderService.checkIfOrderIsForUser(orderDto.getId(),authentication.getName())){
            result.rejectValue("order",null,"nie możesz edytować czyjegos zlecenia");
        }
        if(result.hasErrors()){
            model.addAttribute("order",orderDto);
            return "order/addorder";
        }
        orderService.saveOrder(orderDto);
        return "redirect:/myorders";
    }

    @GetMapping("/finished")
    public String finishedOrders(Model model){
        model.addAttribute("orders",orderService.findAllFinishedForUser());
        return "order/finishedOrders";
    }

    @GetMapping("/inrepair")
    public String inRepair(Model model){
        model.addAttribute("orders",orderService.inRepairOrders());
        return "order/inRepair";
    }


}
