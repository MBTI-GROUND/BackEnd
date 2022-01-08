package MBTI_GROUND.toypj.Entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

  @Id
  @GeneratedValue
  private Long id;
  private String email;
  private String password;
  //private String username;
  //private String role;
  private String phoneNumber;
  private MBTI mbti;
  private String nickname;
  @CreatedDate
  private LocalDateTime createdDate;

  @Builder
  public UserEntity(String email,String password, String phoneNumber, String mbti, String nickname){
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.mbti = MBTI.valueOf(mbti);
    this.nickname = nickname;
  }



}
