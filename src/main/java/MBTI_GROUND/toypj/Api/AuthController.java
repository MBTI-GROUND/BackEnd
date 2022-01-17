package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Dto.TokenDto;
import MBTI_GROUND.toypj.Dto.TokenRequestDto;
import MBTI_GROUND.toypj.Dto.UserRequestDto;
import MBTI_GROUND.toypj.Dto.UserResponseDto;
import MBTI_GROUND.toypj.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
    return ResponseEntity.ok(authService.signup(userRequestDto));
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto userRequestDto) {
    return ResponseEntity.ok(authService.login(userRequestDto));
  }

  @PostMapping("/reissue")
  public ResponseEntity<TokenDto> reissue(TokenRequestDto tokenRequestDto) throws Exception {
   return ResponseEntity.ok(authService.reissue(tokenRequestDto));
  }
  @GetMapping("/test")
  public String test(){
    return "테스트중";
  }

}
