package com.jpabook.shop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected OrderItem(){} 방지 (직접 생성하지않고 다른 스타일로 유도용)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne (fetch = LAZY)
    // order ↔ member는 다대일관계, 연관관계의 주인, 값을 세팅하면 FK값이 변경된다
    @JoinColumn(name = "member_id") // 외래키 선언
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 하나의 주문은 하나의 배송정보만 가지고, 하나의 배송 정보도 하나의 주문 정보만 가져야한다

    private LocalDateTime orderDate; // 주문시간 (시분초 표현), Hibernate 지원 클래스

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


    // 연관관계 편의 메서드 (양방향 연관관계는 편의메서드를 작성하는게 좋다)
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==// 복잡한 생성은 별도의 생성메서드가 있어야 좋음
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER); // 주문상태를 ORDER로 강제해둠
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//

    /* 주문 취소 (주문가능한 재고수량이 원복되어야함) */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) { // 배송완료된 조건
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) { // for문을 돌면서 주문취소된 재고를 원복시킴
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /* 전체 주문가격 조회 */
    public int getTotalPrice() { // 전체 주문가격 조회
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    //검색
//    public List<Order> findOrders(OrderSearch orderSearch) {

//    }
}
