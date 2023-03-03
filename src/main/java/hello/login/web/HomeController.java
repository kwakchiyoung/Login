package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository; //병신같은실수 이거떄매 30분 잡아먹음 ㅡ.ㅡ final 안 붙여서 생성자 안생김.ㅋ

    //@GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/") //로그인 처리까지 되는 화면
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId , Model model){ //로그인 안한 사용자도 들어와야하므로 false
        if(memberId == null){
            return "home";
        }

        //로그인
        Member loginMember = memberRepository.findById(memberId);
        //실패로직
        if (loginMember == null) {
            return "home"; //DB에 없을수도 있으니 쿠키가 너무 옛날에 만들어졌을수도 있어서
        }
        //성공로직
        model.addAttribute("member",loginMember);
        return "loginHome";
    }


}