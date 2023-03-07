package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.websession.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository; //병신같은실수 이거떄매 30분 잡아먹음 ㅡ.ㅡ final 안 붙여서 생성자 안생김.ㅋ
    private final SessionManager sessionManager;

    //@GetMapping("/")
    public String home() {
        return "home";
    }

    //@GetMapping("/") //로그인 처리까지 되는 화면
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
    //@GetMapping("/") //로그인 처리까지 되는 화면
    public String homeLoginV2(HttpServletRequest request, Model model){ //로그인 안한 사용자도 들어와야하므로 false
        

        //세션 관리자에 저장된 회원 정보 조회
        Member Member = (Member)sessionManager.getSession(request);
        //로그인

        //실패로직
        if (Member == null) {
            return "home"; //DB에 없을수도 있으니 쿠키가 너무 옛날에 만들어졌을수도 있어서
        }
        //성공로직
        model.addAttribute("member",Member);
        return "loginHome";
    }
    //@GetMapping("/") //로그인 처리까지 되는 화면
    public String homeLoginV3(HttpServletRequest request, Model model){ //로그인 안한 사용자도 들어와야하므로 false

        HttpSession session = request.getSession(false);//여기는 로그인 화면이라 생성할 의도가 없으므로 메모리를 쓰는거기때문에 꼭 필요할때만 생성할것.
        if(session==null){
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //로그인

        ////세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home"; //DB에 없을수도 있으니 쿠키가 너무 옛날에 만들어졌을수도 있어서
        }
        //성공로직
        model.addAttribute("member",loginMember);
        return "loginHome";
    }

    @GetMapping("/") //로그인 처리까지 되는 화면 dd
    public String homeLoginV3Spring(
            @SessionAttribute(name=SessionConst.LOGIN_MEMBER , required = false) Member loginMember, Model model){ //로그인 안한 사용자도 들어와야하므로 false
        ////세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home"; //DB에 없을수도 있으니 쿠키가 너무 옛날에 만들어졌을수도 있어서
        }
        //성공로직
        model.addAttribute("member",loginMember);
        return "loginHome";
    }


}