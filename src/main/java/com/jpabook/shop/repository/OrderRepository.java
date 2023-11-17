package com.jpabook.shop.repository;

import com.jpabook.shop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);

    }
//
//    // 검색 기능
//    public List<Order> findAll(OrderSearch orderSearch) {
//
//        em.createQuery("select o from Order o join o.member m" + // 주문 조회 후 멤버랑 조인
//                        " where o.status= :status " +
//                        " and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus()) // 파라미터 바인딩
//                .setParameter("name", orderSearch.getMemberName())
//                //.setFirstResult(10) 페이징, 10부터시작~최대 1000개까지
//                .setMaxResults(1000) // 결과 제한, 최대 1000건
//                .getResultList();
//
//    }
}


