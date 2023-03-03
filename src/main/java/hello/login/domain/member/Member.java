package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data //롬복 - 게터,세터,toString등을 필드에 적용시킴.
public class Member {
    private Long id;
    @NotEmpty //null 과 "" 둘 다 허용하지 않게 합니다.
    private String loginId; //로그인 ID
    @NotEmpty
    private String name; //사용자이름
    @NotEmpty
    private String password; //비밀번호
}
