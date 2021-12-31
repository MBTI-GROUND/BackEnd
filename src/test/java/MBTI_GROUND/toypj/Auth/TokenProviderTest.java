package MBTI_GROUND.toypj.Auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenProviderTest {
  @Autowired
  TokenProvider tokenProvider = new TokenProvider();
  @Value("${jwt.secret}")
  private String key;

  @Test
  void test1() {
    tokenProvider.test();
    System.out.println("key = " + key);
  }
}