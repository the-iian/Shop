package com.jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne // order ↔ member는 다대일관계, 연관관계의 주인, 값을 세팅하면 FK값이 변경된다
    @JoinColumn(name = "member_id") // 외래키 선언
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간 (시분초 표현), Hibernate 지원 클래스

    private OrderStatus status; // 주문상태 [ORDER, CANCEL]
}
