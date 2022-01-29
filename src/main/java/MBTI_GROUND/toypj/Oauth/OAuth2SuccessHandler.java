package MBTI_GROUND.toypj.Oauth;

import MBTI_GROUND.toypj.Auth.TokenProvider;
import MBTI_GROUND.toypj.Dto.TokenDto;
import MBTI_GROUND.toypj.Dto.UserResponseDto;
import MBTI_GROUND.toypj.Entity.MBTI;
import MBTI_GROUND.toypj.Entity.RefreshToken;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.RefreshTokenRepository;
import MBTI_GROUND.toypj.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ObjectMapper objectMapper;


  @Override
  @Transactional
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    String email = oAuth2User.getAttribute("email");
    String password = "소셜로그인";
    TokenDto tokenDto = tokenProvider.generateTokenDto(email);
    if (!userRepository.existsByEmail(email)) {
      UserEntity userEntity = UserEntity.builder()
          .mbti("NONE")
          .password(passwordEncoder.encode(password))
          .email(email)
          .build();
      userRepository.save(userEntity);
      RefreshToken refreshToken = RefreshToken.builder()
          .userId(email)
          .refreshToken(tokenDto.getRefreshToken())
          .build();
      refreshTokenRepository.save(refreshToken);
    }
    writeTokenResponse(response,tokenDto);
    String redirectUrl = "http://localhost:3000/oauth/redirect";
    getRedirectStrategy().sendRedirect(request, response, redirectUrl);


  }

  private void writeTokenResponse(HttpServletResponse response, TokenDto tokenDto)
      throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    var writer = response.getWriter();
    writer.println(objectMapper.writeValueAsString(tokenDto));
    writer.flush();
  }
}
