package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final TokenProvider tokenProvider;



  @GetMapping("/tokenTest")
  public String token(){
    return tokenProvider.create("씨발년아");
  }

}
