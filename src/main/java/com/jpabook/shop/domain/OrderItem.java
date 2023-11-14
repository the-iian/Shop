package com.jpabook.shop.domain;

import com.jpabook.shop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // ~ToOne 관계는 기본이 즉시로딩이므로, 지연로딩으로 변경하자
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격

    private int count; // 주문수량
}
