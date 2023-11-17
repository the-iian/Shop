package com.jpabook.shop.controller;

import com.jpabook.shop.domain.Address;
import com.jpabook.shop.domain.Member;
import com.jpabook.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new") // 화면을 열어보고
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm()); // 빈껍데기 들고가서 검증
        return "members/createMemberForm"; // view로 이동
    }


    //회원 등록
    @PostMapping("/members/new") // 데이터를 실제 등록
    public String create(@Valid MemberForm form, BindingResult result) {
        // Valid : MemberForm을 열어보고 검증 어노테이션을 실행, BindingResult : Valid 다음에 있으면 오류가 이곳에 담겨서 아래 코드들이 실행됨

        if (result.hasErrors()) { // result에 오류가있으면 createMemberForm으로 리턴
            return "members/createMemberForm"; // 화면에서 오류 출력
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }


    //회원 목록 조회
    @GetMapping("/members")
    public String list(Model model) { // model객체를 통해 화면에 데이터를 전달
        List<Member> members = memberService.findMembers(); // JPQL에서 꺼내서 모든 멤버 조회
        model.addAttribute("members", members); // 리스트가 꺼내진다
        return "members/memberList"; // loop를 돌면서 화면에 뿌려짐
    }
}
