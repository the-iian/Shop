package com.jpabook.shop.service;

import com.jpabook.shop.domain.Member;
import com.jpabook.shop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // Junit Spring Test 사용
@SpringBootTest
@Transactional // Transactional을 걸고 테스트한다음 테스트끝나면 다시 rollback함. 서비스나 리파지토리에서는 롤백하지않음
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    //@Rollback(false) //등록 쿼리를 모두 볼수있다 (insert포함) - DB확인용
    public void 회원가입() throws Exception {

        //give
        Member member = new Member();
        member.setName("jung");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {

        //given
        Member member1 = new Member();
        member1.setName("jung");

        Member member2 = new Member();
        member2.setName("jung");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야한다");

    }
}