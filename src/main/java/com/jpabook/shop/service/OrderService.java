package com.jpabook.shop.service;

import com.jpabook.shop.domain.Delivery;
import com.jpabook.shop.domain.Member;
import com.jpabook.shop.domain.Order;
import com.jpabook.shop.domain.OrderItem;
import com.jpabook.shop.domain.item.Item;
import com.jpabook.shop.repository.ItemRepository;
import com.jpabook.shop.repository.MemberRepository;
import com.jpabook.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    //주문
    @Transactional // 데이터를 변경하려면 사용
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성 - 하나의 아이템만 넘기도록 설계했지만 주문 생성에서 인자값을 추가하면 여러개 주문도 가능하다
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        /* OrderItem orderItem1 = new OrderItem();
           orderItem1.setCount();
           이런식의 코드로 짤수도있지만 로직을 생성하거나 변경할때 각각 다른 코드로 만들면 분산된다
           이거외에 다른 스타일의 코드는 제약을 시켜야 함 */

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();

    }

    //취소
    @Transactional
    public void cancleOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId); // 주문 엔티티 조회
        order.cancel();

    // orderId를 통해 Order객체를 DB에서 조회하고, 해당 주문을 가져와서 Order객체로 반환한다

    }

    //검색
}
