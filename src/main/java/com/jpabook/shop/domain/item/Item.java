package com.jpabook.shop.domain.item;

import com.jpabook.shop.domain.Category;
import com.jpabook.shop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private int stockQuantity; // 재고 수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //== 비즈니스 로직 ==//
    // 재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity-quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock"); // Custom Exception

        }
        this.stockQuantity = restStock;
    }
}
