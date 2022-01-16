package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final TokenProvider tokenProvider;



  @GetMapping("/tokenTest")
  public String token(){
    return tokenProvider.createAccessToken("씨발년아");
  }

  @GetMapping("/test")
  public String test1(){
    return "정답이다. 연금술사";
  }

}
