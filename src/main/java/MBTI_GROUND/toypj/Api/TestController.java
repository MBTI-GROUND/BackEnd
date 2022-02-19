package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final TokenProvider tokenProvider;


  @GetMapping("/tokenTest")
  public String token() {
    return tokenProvider.createAccessToken("씨발년아");
  }

  @GetMapping("/test")
  public String test1(Authentication authentication) {
    System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
    System.out.println("authentication.getCredentials() = " + authentication.getCredentials());
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
    System.out
        .println("usernamePasswordAuthenticationToken.getPrincipal() = "
            + usernamePasswordAuthenticationToken.getPrincipal());
    return "정답이다. 연금술사";
  }

}
