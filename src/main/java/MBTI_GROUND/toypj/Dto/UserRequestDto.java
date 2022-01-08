package MBTI_GROUND.toypj.Dto;

import MBTI_GROUND.toypj.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

  private String email;
  private String password;

  public UserEntity toUser(PasswordEncoder passwordEncoder) {
    return UserEntity.builder()
        .email(email)
        .password(passwordEncoder.encode(password))
        .build();
  }

  public UsernamePasswordAuthenticationToken toAuthentication() {
    return new UsernamePasswordAuthenticationToken(email, password);
  }
}
