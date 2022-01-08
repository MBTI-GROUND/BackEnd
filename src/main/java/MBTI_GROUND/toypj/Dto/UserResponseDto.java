package MBTI_GROUND.toypj.Dto;

import MBTI_GROUND.toypj.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

  private String email;

  public static UserResponseDto of(UserEntity userEntity){
    return new UserResponseDto(userEntity.getEmail());
  }

}
