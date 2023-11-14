package com.jpabook.shop.domain.item;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // 저장할때 구분할 값
@Getter @Setter
public class Book extends Item{ // Item 상속

    private String author;
    private String isbn;

}
