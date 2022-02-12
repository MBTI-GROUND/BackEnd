package MBTI_GROUND.toypj.Auth;

import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  public static final String REFRESH_HEADER = "Refresh";
  public static final String BEARER_PREFIX = "Bearer ";
  public static final String REFRESH_PREFIX = "Refresh ";
  private final TokenProvider tokenProvider;

  private final UserRepository userRepository;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String accessToken = parseBearerToken(request);
      log.info("JwtAuthenticationFilter is running");
      if (StringUtils.hasText(accessToken) && tokenProvider.isValidAccessToken(accessToken)) {
        String userId = tokenProvider.validAccessTokenAndGetUserId(accessToken);
        log.info("Authenticated user ID: " + userId);
        Optional<UserEntity> userEntity = userRepository.findByEmail(userId);
        String password = userEntity.orElseThrow(() -> new RuntimeException("사용자 정보를 가져올 수 없습니다."))
            .getPassword();
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userId, password, AuthorityUtils.NO_AUTHORITIES);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
      }
      // ACCESS TOKEN 만료되었을 경우
      else if(StringUtils.hasText(accessToken)){
        String refreshToken = parseRefreshToken(request);
        if(StringUtils.hasText(refreshToken) && tokenProvider.isValidRefreshToken(refreshToken)){
          String userId = tokenProvider.validRefreshTokenAndGetUserId(refreshToken);
          log.info("Authenticated user ID: " + userId);
          Optional<UserEntity> userEntity = userRepository.findByEmail(userId);
          String password = userEntity.orElseThrow(() -> new RuntimeException("사용자 정보를 가져올 수 없습니다."))
              .getPassword();
          AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userId, password, AuthorityUtils.NO_AUTHORITIES);
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
          securityContext.setAuthentication(authentication);
          SecurityContextHolder.setContext(securityContext);
          String reIssuedAccessToken = tokenProvider.createAccessToken(userId);
          response.addHeader("REISSUEDACCESSTOKEN",reIssuedAccessToken);

        }
      }

    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      request.setAttribute("exception","잘못된 REFRESH JWT 서명입니다.");
    } catch (NullPointerException exception) {
      log.info("REFRESH JWT 토큰이 없습니다.");
      request.setAttribute("exception","REFRESH JWT 토큰이 없습니다." );
    } catch (IllegalArgumentException e) {
      log.info("REFRESH JWT 토큰이 잘못되었습니다.");
      request.setAttribute("exception","REFRESH JWT 토큰이 잘못되었습니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 REFRESH JWT 토큰입니다.");
      request.setAttribute("exception","지원되지 않는 REFRESH JWT 토큰입니다.");
    } catch (Exception e) {
      request.setAttribute("exception",e.getMessage());
    }
    filterChain.doFilter(request, response);



  }

  private String parseBearerToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private String parseRefreshToken(HttpServletRequest request){
    String refreshToken = request.getHeader(REFRESH_HEADER);
    if (StringUtils.hasText(refreshToken) && refreshToken.startsWith(REFRESH_PREFIX)){
      return refreshToken.substring(8);
    }
    return null;
  }
}
