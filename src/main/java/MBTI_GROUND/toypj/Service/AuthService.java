package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Auth.TokenProvider;
import MBTI_GROUND.toypj.Dto.TokenDto;
import MBTI_GROUND.toypj.Dto.TokenRequestDto;
import MBTI_GROUND.toypj.Dto.UserRequestDto;
import MBTI_GROUND.toypj.Dto.UserResponseDto;
import MBTI_GROUND.toypj.Entity.RefreshToken;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.RefreshTokenRepository;
import MBTI_GROUND.toypj.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public UserResponseDto signup(UserRequestDto userRequestDto) {
    if (userRepository.existsByEmail(userRequestDto.getEmail())) {
      throw new RuntimeException("이미 가입되어 있는 유저입니다.");
    }
    UserEntity userEntity = userRequestDto.toUser(passwordEncoder);
    userRepository.save(userEntity);
    return UserResponseDto.of(userEntity);

  }

  public TokenDto login(UserRequestDto userRequestDto) {

    UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();

    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);
    TokenDto tokenDto = tokenProvider.generateTokenDto(userRequestDto.getEmail());

    RefreshToken refreshToken = RefreshToken.builder()
        .key(authentication.getName())
        .value(tokenDto.getRefreshToken())
        .build();

    refreshTokenRepository.save(refreshToken);

    return tokenDto;
  }

  public TokenDto reissue(TokenRequestDto tokenRequestDto) throws Exception {
    if (!tokenProvider.isValidRefreshToken(tokenRequestDto.getRefreshToken())) {
      throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
    }
    String userId = tokenProvider.getRefreshTokenClaims(tokenRequestDto.getRefreshToken())
        .getSubject();
    RefreshToken refreshToken = refreshTokenRepository.findByKey(userId)
        .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

    if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
      throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
    }
    TokenDto tokenDto = tokenProvider.generateTokenDto(userId);
    refreshToken.updateValue(tokenDto.getRefreshToken());
    return tokenDto;

  }

}
