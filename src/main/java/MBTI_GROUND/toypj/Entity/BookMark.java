package MBTI_GROUND.toypj.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMark {
  @Id
  @GeneratedValue
  private Long id;
  private Long userId;
  private Long postId;


}
