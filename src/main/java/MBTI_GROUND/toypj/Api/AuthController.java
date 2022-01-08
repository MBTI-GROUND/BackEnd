package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Dto.TokenDto;
import MBTI_GROUND.toypj.Dto.UserRequestDto;
import MBTI_GROUND.toypj.Dto.UserResponseDto;
import MBTI_GROUND.toypj.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<UserResponseDto> signup(UserRequestDto userRequestDto){
    return null;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(UserRequestDto userRequestDto){
    return null;
  }

  @PostMapping("/reissue")
  public ResponseEntity<TokenDto> reissue(TokenDto tokenDto){
    return null;
  }

}
