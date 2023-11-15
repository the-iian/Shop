package com.jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
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
}
