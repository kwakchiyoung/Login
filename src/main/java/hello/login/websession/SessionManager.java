package hello.login.websession;

import org.springframework.stereotype.Component;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String,Object> sessionStore = new ConcurrentHashMap<>(); //동시성 이슈로 인해
    /**
     * 세션 생성
     */
    //세션 id를 생성하고, 값을 세션에 저장
    public void createSession(Object value, HttpServletResponse response){
        String sessionId = UUID.randomUUID().toString(); //랜덤값 추출
        sessionStore.put(sessionId,value);

        //쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME,sessionId);
    }
    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request){
        Cookie seesionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if ( seesionCookie == null){
            return null;
        }
        return sessionStore.get(seesionCookie.getValue());
    }
    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if ( sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request , String cookieName){
        if (request.getCookies() ==null){
            return null;
        }
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(cookieName))
                .findAny().orElse(null);
    }

}
