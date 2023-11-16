package com.jpabook.shop.domain;

import com.jpabook.shop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected OrderItem(){} 방지 (제약하는 스타일로 코딩을해야 좋은 설계와 유지보수로 끌어간다)
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
    private int orderPrice;
    private int count; // 주문수량


    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem(); // 주문항목 생성

        // 생성된 주문 항목에 아이템, 주문가격, 수량 설정
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 주문 항목에 해당하는 아이템 재고 감소
        item.removeStock(count);

        // 생성된 주문 항목 반환
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count); // 주문취소했을때 재고를 다시 주문수량만큼 원복하기

    }


    //==조회 로직==//
    // 주문상품 총가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();

    }

}
