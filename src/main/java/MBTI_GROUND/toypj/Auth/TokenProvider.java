package MBTI_GROUND.toypj.Auth;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class TokenProvider {

  @Value("${jwt.secret}")
  private String key;




}
