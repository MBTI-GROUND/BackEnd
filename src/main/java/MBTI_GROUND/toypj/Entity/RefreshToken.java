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
  private String key;
  private String value;

  public void updateValue(String refreshToken){
    this.value = refreshToken;
  }

  @Builder
  public RefreshToken(String key, String value){
    this.key = key;
    this.value = value;
  }

}
