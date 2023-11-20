package com.jpabook.shop.controller;

import com.jpabook.shop.domain.Member;
import com.jpabook.shop.domain.Order;
import com.jpabook.shop.domain.item.Item;
import com.jpabook.shop.repository.OrderSearch;
import com.jpabook.shop.service.ItemService;
import com.jpabook.shop.service.MemberService;
import com.jpabook.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    //상품 주문서 조회
    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    //상품 주문
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count); // 하나의 상품만 주문할 수 있게.


        // 컨트롤러에서 member, item을 찾게해도되지만, 지금과 같은 로직이 좋은것은 컨트롤러 코드도 깔끔해지고,
        // 테스트할때에도 동작이 깔끔해지고, 엔티티들도 영속 상태로 흘러가기때문에 할 수 있는게 더욱 많아진다

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        // ModelAttribute에 세팅하면 model에 담긴다

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";

    }


    //주문 취소
    @PostMapping("/orders/{orderId}/cancel")
    public String cancleOrder(@PathVariable("orderId") Long orderId){
        orderService.cancleOrder(orderId);
        return "redirect:/orders";

    }
}

