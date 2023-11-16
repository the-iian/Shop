package com.jpabook.shop.repository;

import com.jpabook.shop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 상품 저장
    public void save(Item item) {
        if (item.getId() == null){ // 상품은 JPA 저장전까지 id값이 없다 → 새로 생성한 객체
            em.persist(item); // 영속성컨텍스트에 신규로 등록
        } else {
            em.merge(item); // DB에 있다면 UPDATE와 비슷한 개념
        }
    }

    // 상품 단건조회
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }


    // 상품 목록조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                 .getResultList();
    }

}
