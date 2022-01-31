package MBTI_GROUND.toypj.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

  @Id
  private String userId;
  private String refreshToken;

  public void updateValue(String refreshToken){
    this.refreshToken = refreshToken;
  }

  @Builder
  public RefreshToken(String userId, String refreshToken){
    this.userId = userId;
    this.refreshToken = refreshToken;
  }

}
