package com.jpabook.shop.domain.item;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Single Table : 상속관계 클래스들을 하나의 테이블에 다 때려박는 방식
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 구현체를 가지고할거라 추상클래스로 진행

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

}
