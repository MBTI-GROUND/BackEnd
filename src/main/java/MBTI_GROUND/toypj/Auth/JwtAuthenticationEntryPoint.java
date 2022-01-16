package MBTI_GROUND.toypj.Auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    // 유효한 자격증명을 제공하지 않고 접근하려 할 때 401
    System.out.println("JwtAuthenticationEntryPoint");
    System.out.println("request.getRequestURL() = " + request.getRequestURL());
    System.out.println("request.getMethod() = " + request.getMethod());
    String exception = (String)request.getAttribute("exception");
    if(exception== null){
      exception = "토큰이 없는 사용자입니다.";
    }
    response.setContentType( "text/html;charset=utf-8" );
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().println(exception);
  }
}
