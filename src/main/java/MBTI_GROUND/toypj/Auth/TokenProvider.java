package MBTI_GROUND.toypj.Auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TokenProvider {

  @Value("${jwt.secret}")
  private String key;

  private Key getSignKey() {
    return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
  }

  public String create(String userId) {
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    return Jwts.builder()
        .signWith(getSignKey())
        .setSubject(userId)
        .setIssuer("MBTI_GROUND")
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .compact();

  }

  public String validateAndGetUserId(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJwt(token)
        .getBody();
    return claims.toString();
  }


}
