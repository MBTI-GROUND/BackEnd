package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Dto.UserResponseDto;
import MBTI_GROUND.toypj.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserResponseDto> getMyUserInfo() {
    return ResponseEntity.ok(userService.getMyInfo());
  }

  @GetMapping("/{email}")
  public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String email){
    return ResponseEntity.ok(userService.getUserInfo(email));
  }


}
