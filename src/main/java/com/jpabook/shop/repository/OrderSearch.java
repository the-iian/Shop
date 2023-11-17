package com.jpabook.shop.repository;

import com.jpabook.shop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {  // 파라미터 조건이 있으면 WHERE문으로 검색이 되야한다

    private String memberName; // 회원명
    private OrderStatus orderStatus; // 주문상태 [ORDER, CANCLE]

}
