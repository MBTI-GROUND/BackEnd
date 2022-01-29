package MBTI_GROUND.toypj.Auth;
import MBTI_GROUND.toypj.Dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_TYPE = "bearer";


  @Value("${jwt.Access}")
  private String accessKey;
  @Value("${jwt.Refresh}")
  private String refreshKey;

  private final Date accessExpiryDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
  private final Date refreshExpiryDate = Date.from(Instant.now().plus(14, ChronoUnit.DAYS));


  private Key getAccessSignKey() {
    return Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));
  }

  private Key getRefreshSignKey() {
    return Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));
  }

  public String createAccessToken(String userId) {
    return Jwts.builder()
        .signWith(getAccessSignKey())
        .setSubject(userId)
        .setIssuer("MBTI_GROUND")
        .setIssuedAt(new Date())
        .setExpiration(accessExpiryDate)
        .compact();

  }

  public String createRefreshToken(String userId) {
    return Jwts.builder()
        .signWith(getRefreshSignKey())
        .setSubject(userId)
        .setIssuer("MBTI_GROUND")
        .setIssuedAt(new Date())
        .setExpiration(refreshExpiryDate)
        .compact();

  }

  public String validAccessTokenAndGetUserId(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getAccessSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public String validRefreshTokenAndGetUserId(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getRefreshSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public Claims getAccessTokenClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getAccessSignKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  public Claims getRefreshTokenClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getRefreshSignKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  public boolean isValidAccessToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getAccessSignKey()).build().parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      log.info("만료된 ACCESS JWT 토큰입니다.");
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 ACCESS JWT 서명입니다.");
    } catch (NullPointerException e) {
      log.info("ACCESS JWT 토큰이 없습니다.");
    } catch (IllegalArgumentException e) {
      log.info("ACCESS JWT 토큰이 잘못되었습니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 ACCESS JWT 토큰입니다.");
    }
    return false;
  }

  public boolean isValidRefreshToken(String token)throws Exception{
      Jwts.parserBuilder().setSigningKey(getRefreshSignKey()).build().parseClaimsJws(token);
      return true;

  }

  public TokenDto generateTokenDto(String userId) {
    // Access Token 생성
    String accessToken = createAccessToken(userId);
    // Refresh Token 생성
    String refreshToken = createRefreshToken(userId);

    return TokenDto.builder()
        .grantType(BEARER_TYPE)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  /*@Transactional(readOnly = true)
  public Authentication getAuthentication(String accessToken) {
    Claims accessTokenClaims = getAccessTokenClaims(accessToken);
    String userId = accessTokenClaims.getSubject();
    if (userId == null) {
      throw new RuntimeException("유저 ID정보가 없는 토큰입니다.");
    }
    Optional<UserEntity> foundUser = userRepository.findByEmail(userId);
    if (foundUser.isPresent()) {
      String foundUserId = foundUser.get().getEmail();
      String foundUserPassword = foundUser.get().getPassword();
      return new UsernamePasswordAuthenticationToken(foundUserId, foundUserPassword,
          AuthorityUtils.NO_AUTHORITIES);
    } else {
      throw new RuntimeException("DB에 유저 정보가 없습니다.");
    }
  }*/

}
