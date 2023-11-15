package com.jpabook.shop.repository;

import com.jpabook.shop.domain.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 스프링 Bean으로 관리
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // 스프링이 EntityManager를 자동으로 만들어서 인젝션 (외부에서 얻어온다)


    public void save(Member member) {
        em.persist(member); // 영속성 컨텍스트에 member객체 넣기
    }


    public Member findOne(Long id){ // 단건 조회
        return em.find(Member.class, id); // 회원을 찾아서 반환 ( 1.Type 2.PK )
    }


    public List<Member> findAll(){ // 리스트 조회
        return em.createQuery("select m from Member m", Member.class) // createQuery : JPQL 쿼리 실행 메서드 ( 1.JPQL 2.반환타입 )
                 .getResultList(); // 멤버를 List로 만들어서 가져오기
    }


    public List<Member> findByName(String name){ // 회원 이름으로 검색
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                 .setParameter("name", name) // 파라미터가 바인딩됨
                 .getResultList();

    }
}
