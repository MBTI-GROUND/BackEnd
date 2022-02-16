package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Auth.SecurityUtil;
import MBTI_GROUND.toypj.Dto.UserResponseDto;
import MBTI_GROUND.toypj.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;

  public UserResponseDto getUserInfo(String email){
    return userRepository.findByEmail(email)
        .map(UserResponseDto::of)
        .orElseThrow(()-> new RuntimeException("유저 정보가 없습니다."));
  }

  public UserResponseDto getMyInfo(){
    return userRepository.findByEmail(SecurityUtil.getCurrentUserId())
        .map(UserResponseDto::of)
        .orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
  }
}
