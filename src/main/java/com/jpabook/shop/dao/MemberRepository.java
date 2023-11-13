package com.jpabook.shop.dao;

import com.jpabook.shop.entity.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext // entityManager 설정 어노테이션
    private EntityManager em;


    public Long save(Member member){ // Member 영속성 컨텍스트 저장, ID 반환
        em.persist(member);
        return member.getId();

    }

    public Member find(Long id){ // 멤버 조회
        return em.find(Member.class, id);
    }
}
