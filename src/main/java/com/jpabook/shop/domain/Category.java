package com.jpabook.shop.domain;

import com.jpabook.shop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>(); // Category와 Item은 둘다 List형식의 다대다관계


    //셀프 양방향 연관관계 설정, 이름만 내꺼지 다른 엔티티랑 매핑하는거랑 똑같이 생각하기
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; // 카테고리 구조는 개체구조로 쭉 내려간다. (위로도, 아래로도 볼 수 있어야 함)
    // 나의 부모가 나의 타입이니까 Category 넣어주기

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); // 자식은 카테고리 여러개를 가질수있으므로 List형식


    //연관관계 편의 메서드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
