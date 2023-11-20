package com.jpabook.shop.repository;

import org.springframework.util.StringUtils;
import com.jpabook.shop.domain.Member;
import com.jpabook.shop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);

    }


    //JPA Criteria로 처리 - Querydsl 사용이 가장 좋지만 지금은 이대로 진행
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건

        return query.getResultList();

    }
}

//    검색 기능
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


