package com.jpabook.shop.service;

import com.jpabook.shop.domain.Member;
import com.jpabook.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service // Component에 의해 스프링 Bean으로 등록
@Transactional(readOnly = true) // JPA가 조회하는곳에서는 성능을 조금 더 최적화함
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 만들어준다
public class MemberService {


    @Autowired // Injection 외부에서 데이터나 의존성을 시스템에 주입
    private final MemberRepository memberRepository;


    // 회원가입
    @Transactional // 우선권자 (기본값 readOnly = false) JPA의 모든 데이터 변경이나 로직들은 트랜잭션안에서 다 실행되야한다 (LAZY LOADING 등)
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원이름 검증 로직
        memberRepository.save(member);
        return member.getId(); // save메소드실행시 em.persist가 동작되면서 key는 PK가 되어 ID가 들어간다 (꺼냈을때 항상 값이 있다는게 보장됨)
    }


    // 중복된 회원 (문제가 있으면 예외발생시키기)
    private void validateDuplicateMember(Member member){

        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    // 전체회원 조회
    public List<Member> findMembers(){ // public메서드는 readOnly를 타지만 우선권자는 예외
        return memberRepository.findAll();
    }


    // 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
