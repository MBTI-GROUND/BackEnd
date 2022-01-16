package MBTI_GROUND.toypj.Oauth;

import MBTI_GROUND.toypj.Auth.TokenProvider;
import MBTI_GROUND.toypj.Repository.RefreshTokenRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    System.out.println("OAuth2SuccessHandler");
    System.out.println("request.getRequestURL() = " + request.getRequestURL());
    System.out.println("request.getMethod() = " + request.getMethod());
    OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
    String name = oAuth2User.getAttributes().get("name").toString();
    String email = oAuth2User.getAttribute("email");
    response.setContentType( "text/html;charset=utf-8" );
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().println("정답이다 연금술사");
    response.getWriter().println(name);
    response.getWriter().println(email);


  }
}
