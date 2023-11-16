package com.jpabook.shop.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장타입 포함 (Embedded, Embeddable 둘중하나만 사용해도 무방)
    private Address address;

    @OneToMany(mappedBy = "member")
    // member ↔ order는 일대다관계, order테이블에 있는 member필드에 의해 맵핑됨, 읽기전용
    private List<Order> orders = new ArrayList<>();


}
